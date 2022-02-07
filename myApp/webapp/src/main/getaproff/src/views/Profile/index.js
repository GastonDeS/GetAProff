import React, { useState, useEffect } from "react";
import i18next from "i18next";
import axios from "axios";
import PropTypes from 'prop-types'
import AuthService from "../../services/authService";
import Navbar from "../../components/Navbar";
import {
  ProfileInfoButtons,
  StarsReviews,
  ProfileInfo,
  ProfileContainer,
  InfoContainer,
  ProfileName,
  TabInfoContainer,
  SectionInfo,
  ProfileDataContainer,
  ProfileSubjects,
  ReviewTitle,
  TabContainer,
} from './Profile.styles';
import RatingStar from "react-stars";
import Button from "../../components/Button";
import Tab from "../../components/Tab";
import TabItem from "../../components/TabItem";
import Rows from "../../components/Rows";
import { Wrapper, MainContainer, Row, Headers, Table } from "../../GlobalStyle";
import {useLocation} from 'react-router-dom';

const Profile = () => {
  const [index, setIndex] = useState(0);
  const [rows, setRows] = useState({data: []});
  const [user, setUser] = useState({data: []});
  const [teacher, setTeacher] = useState({data: []});
  const [loading, setLoading] = useState(true);
  const [reviews, setReviews] = useState({data: []});
  const [image, setImage] = useState();


  const location = useLocation();

  const tabs = ['profile.personal', 'profile.subjects', 'profile.reviews'];

  useEffect(async () => {
    if ( user.id ) {
      const res = await axios.get("/teachers/subjects/"+user.id);
      setRows({
        data: res.data.map((item, index) => {
          return { 
            first: item.subject,
            second: '$' + item.price + '/' + i18next.t('subjects.hour'),
            third: i18next.t('subjects.levels.' + item.level)
          };
        }),
      });
      const teacherRes = await axios.get("/teachers/52");
      setTeacher({data: teacherRes.data});
      const reviewsRes = await axios.get("/ratings/"+user.id);
      setReviews({data: reviewsRes.data});
      const imageRes = await axios.get('/images/'+user.id)
      setImage('data:image/png;base64,' + imageRes.data.image);
    }
  }, [user]);

  useEffect(async () => {
    if (!(location.state != null && location.state.id != null)) {
      setUser(await AuthService.getCurrentUser());
    } else {
      // 
    }
  }, []);

  useEffect(async () => {
    setLoading(false);
  },[teacher]);

  return (
    <Wrapper>
      <Navbar/>
      <MainContainer>
        {(loading)?
          <>
          </>
        :
        <ProfileContainer>
          <InfoContainer>
            <img
              src={image}
              alt="profileImg"
            />
            <ProfileInfo>
              <ProfileName>
                <h1 style={{ fontSize: '2rem' }}>{teacher.data.name}</h1>
                <StarsReviews>
                  <RatingStar count={5} value={teacher.data.rate} size={18} edit={false} />
                  {/* TODO getReviewsCount */}
                  <p>({teacher.data.rate} Reviews)</p>
                </StarsReviews>
              </ProfileName>
              <ProfileInfoButtons>
                {teacher.data.id === user.id ? (
                  <>
                    <Button text="Edit certifications" fontSize="1rem"/>
                    <Button text="Edit profile" fontSize="1rem"/>
                    <Button text="Edit subjects" fontSize="1rem"/>
                    <Button text="Share profile" fontSize="1rem"/>
                  </>
                ) : (
                  <>
                    <Button text="Request class" fontSize="1rem"/>
                    <Button text="Add to favorites" fontSize="1rem"/>
                    <Button text="Share profile" fontSize="1rem"/>
                  </>
                )}
              </ProfileInfoButtons>
            </ProfileInfo>
          </InfoContainer>
          <TabContainer>
            <Tab setIndex={setIndex} style={{ margin: "2rem" }}>
              {tabs.map((tab, index) => {
                  return index === 0 ? <TabItem fontSize='1rem' style={{ borderTopLeftRadius: '0.625rem'}}>{i18next.t(tab)}</TabItem> : 
                    index === tabs.length - 1 ? <TabItem fontSize='1rem' style={{ borderTopRightRadius: '0.625rem'}}>{i18next.t(tab)}</TabItem> :
                   <TabItem fontSize='1rem'>{i18next.t(tab)}</TabItem>
              })}
            </Tab>
            <TabInfoContainer>
              {index === 0 ? (
                // TODO Personal Info
                <SectionInfo>
                  <ProfileDataContainer>
                    <h3>Description</h3>
                    <p>{teacher.data.description}</p>
                  </ProfileDataContainer>
                  <ProfileDataContainer>
                    <h3>Schedule</h3>
                    <p>{teacher.data.schedule}</p>
                  </ProfileDataContainer>
                  {/* TODO getCertificaciones */}
                  <ProfileDataContainer> 
                    <h3>Certifications</h3>
                    <ul>
                      <li>
                        <a href={"www.google.com"} target={"_blank"}>
                          Certificacion Cambridge
                        </a>
                      </li>
                    </ul>
                  </ProfileDataContainer>
                </SectionInfo>
              ) : index === 1 ? (
                <ProfileSubjects>
                  <Table>
                      <thead>
                        <Row>
                          <Headers style={{ width: "50%" }}>{i18next.t('subjects.subject')}</Headers>
                          <Headers style={{ width: "20%" }}>{i18next.t('subjects.price')}</Headers>
                          <Headers style={{ width: "30%" }}>{i18next.t('subjects.level')}</Headers>
                        </Row>
                      </thead>
                      <tbody>
                        {rows.data.map((item, index) => {
                            return <Rows key={index} edit={false} data={item}/>
                        })}
                      </tbody>
                    </Table>
                </ProfileSubjects>
              ) : (
                <SectionInfo>
                  {
                    reviews.data.map((option,index) => {
                      return <ProfileDataContainer>
                        <ReviewTitle>
                          <h3>{option.student}</h3>
                          <RatingStar count={5} value={option.rate} size={18} edit={false} />
                        </ReviewTitle>
                        <p>{option.review}</p>
                      </ProfileDataContainer>
                    })
                  }
                  
                </SectionInfo>
              )}
            </TabInfoContainer>
          </TabContainer>
        </ProfileContainer>
        }
      </MainContainer>
    </Wrapper>
  );
};

Profile.propTypes = {
  isTeacher: PropTypes.bool,
  id: PropTypes.number
};

Profile.defaultProps = {
  isTeacher: true
}

export default Profile;
