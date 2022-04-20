import React, { useState, useEffect } from "react";
import PropTypes from 'prop-types';
import i18next from "i18next";
import axios from "axios";
import AuthService from "../../services/authService";
import {useLocation, useNavigate, useParams} from 'react-router-dom';

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
import { Wrapper, MainContainer, Row, Headers, Table, Request } from "../../GlobalStyle";
import ProfileImg from '../../assets/img/no_profile_pic.jpeg';
import ImagesService from "../../services/imagesService";
import FilesService from "../../services/filesService";
import Dropdown from "../../components/DropDown";
import userService from "../../services/index";

const Profile = () => {
  const [index, setIndex] = useState(0);
  const [subjects, setSubjects] = useState([]);
  const [user, setUser] = useState();
  const [loading, setLoading] = useState(true);
  const [reviews, setReviews] = useState([]);
  const [image, setImage] = useState(ProfileImg);
  const [certifications, setCertifications] = useState([]);
  const [currentUser, setCurrentUser] = useState();
  const [isTeacher, setIsTeacher] = useState(true);
  const [isFaved, setIsFaved] = useState(false);
  const location = useLocation();
  const editOptions = [{name: 'profile.edit.profile', path: '/edit-profile'}, 
    {name: 'profile.edit.certifications', path: '/edit-certifications'},
    {name: 'profile.edit.subjects', path: '/edit-subjects'}];

  const navigate = useNavigate();
  const { id } = useParams();
  const tabs = ['profile.personal', 'profile.subjects', 'profile.reviews'];

  useEffect(() => {
    setCurrentUser(AuthService.getCurrentUser());
    let url = 'users';
    if (currentUser){
      if (Number(currentUser.id) === Number(id) && !currentUser.teacher) {
        url = 'placeholder';
        setIsTeacher(false);
      }
    }
   userService.getUserInfo(id)
       .then(data => {{
         setUser(data);
       }})
       .catch(error => {console.log(error)});
  }, []);
  
  useEffect(() => {
    if (user) {
      setLoading(false);
      ImagesService.getImage(user.id, setImage);
      if (isTeacher) {
        userService.getUserSubjects(user.id)
            .then(data => setSubjects(data))
            .catch(err => navigate("/error"))

        userService.getUserReviews(user.id)
            .then(data => setReviews(data))
            .catch(err => navigate("/error"));


        userService.getUserCertifications(user.id)
            .then(data => setCertifications(data))
            .catch(err => navigate("/error"));
      }
    }
  },[user]);

  useEffect(() => {
    let current = AuthService.getCurrentUser();
    let teacherId = window.location.pathname.split('/').pop();
    userService.checkIfTeacherIsFaved(current.id, teacherId)
        .then(value => {
          setIsFaved(value);
        })
  },[])

  const handleFavoriteState = () => {
    let teacherId = window.location.pathname.split('/').pop();
    let setFavoriteStatus = userService.addTeacherToFavorites;
    if (isFaved){
      setFavoriteStatus = userService.removeTeacherFromFavorites;
    }
    setFavoriteStatus(teacherId, currentUser.id)
        .catch(err => console.log(err));
    setIsFaved(!isFaved);
  }

  return (
    <Wrapper>
      <Navbar/>
      <MainContainer>
        {(loading)?
          <></>
        :
        <ProfileContainer>
          <InfoContainer>
            <img
              src={image}
              alt="profileImg"
            />
            <ProfileInfo>
              <ProfileName>
                <h1 style={{ fontSize: '2rem' }}>{user.name}</h1>
                {
                  isTeacher && 
                  <StarsReviews>
                    <RatingStar count={5} value={user.rate} size={18} edit={false} />
                    <p>({user.reviewsQty} Reviews)</p>
                  </StarsReviews>
                }
              </ProfileName>
              <ProfileInfoButtons>
                {currentUser && currentUser.id === user.id ? (
                  isTeacher ? 
                  <>
                    {/* <Button text="Edit profile" fontSize="1rem"/>
                    <Button text="Edit subjects" fontSize="1rem"/>
                    <Button text="Edit certifications" fontSize="1rem"/> */}
                    <Dropdown brand="Edit" options={editOptions} size="1rem" color="white" background="var(--secondary)" radius="2rem" padding="0.45em 1.3em"/>
                    <Button text="Share profile" fontSize="1rem"/> 
                  </> : 
                  <Button text="Edit profile" fontSize="1rem" callback={() => navigate('/edit-profile')}/>
                ) : (
                  <>
                    <Button text="Request class" fontSize="1rem"/>
                    <Button text={!isFaved? "Add to favorites": "Remove from favorites"} callback={handleFavoriteState} fontSize="1rem"/>
                    <Button text="Share profile" fontSize="1rem"/>
                  </>
                )}
              </ProfileInfoButtons>
            </ProfileInfo>
          </InfoContainer>
          {isTeacher && <TabContainer>
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
                    <p>{user.description}</p>
                  </ProfileDataContainer>
                  <ProfileDataContainer>
                    <h3>Schedule</h3>
                    <p>{user.schedule}</p>
                  </ProfileDataContainer>
                  <ProfileDataContainer> 
                    <h3>Certifications</h3>
                    <ul>
                      {certifications.map((certification) => {
                        return (
                        <li key={certification.id}>
                          <Request>
                            <button onClick={() => window.open(URL.createObjectURL(new Blob([FilesService.base64ToArrayBuffer(certification.file)], { type: "application/pdf" })))}>{certification.name}</button>
                          </Request>
                        </li>
                      )})}
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
                        {subjects.map((item, index) => {
                            return <Rows key={index} edit={false} data={item}/>
                        })}
                      </tbody>
                    </Table>
                </ProfileSubjects>
              ) : (
                <SectionInfo>
                  {
                    reviews.map((option) => {
                      console.log(option)
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
          </TabContainer>}
        </ProfileContainer>
        }
      </MainContainer>
    </Wrapper>
  );
};

Profile.propTypes = {
  id: PropTypes.number
};

export default Profile;
