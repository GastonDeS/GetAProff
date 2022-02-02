import React, { useState, useEffect } from "react";
import i18next from "i18next";
import axios from "axios";

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

const Profile = () => {
  const [index, setIndex] = useState(0);
  const profile = Math.round(Math.random());
  const [rows, setRows] = useState({data: []});

  const tabs = ['profile.personal', 'profile.subjects', 'profile.reviews'];

  useEffect(async () => {
    const res = await axios.get("/api/subjects/145");
    setRows({
      data: res.data.map((item, index) => {
        return { 
          first: item.name,
          second: '$' + item.price + '/' + i18next.t('subjects.hour'),
          third: i18next.t('subjects.levels.' + item.level)
        };
      }),
    });
  }, []);

  return (
    <Wrapper>
      <Navbar/>
      <MainContainer>
        <ProfileContainer>
          <InfoContainer>
            <img
              src={"http://pawserver.it.itba.edu.ar/paw-2021b-6/image/38"}
              alt="profileImg"
            />
            <ProfileInfo>
              <ProfileName>
                <h1 style={{ fontSize: '2rem' }}>Gaston De Schant</h1>
                <StarsReviews>
                  <RatingStar count={5} value={5} size={18} edit={false} />
                  <p>(0 Reviews)</p>
                </StarsReviews>
              </ProfileName>
              <ProfileInfoButtons>
                {profile === 0 ? (
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
                    <p>esta es la descripcion</p>
                  </ProfileDataContainer>
                  <ProfileDataContainer>
                    <h3>Schedule</h3>
                    <p>monday from 1 am to 2 pm</p>
                  </ProfileDataContainer>
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
                  <ProfileDataContainer>
                    <ReviewTitle>
                      <h3>Brlin</h3>
                      <RatingStar count={5} value={5} size={18} edit={false} />
                    </ReviewTitle>
                    <p>Muy buena clase Gaston</p>
                  </ProfileDataContainer>
                </SectionInfo>
              )}
            </TabInfoContainer>
          </TabContainer>
        </ProfileContainer>
      </MainContainer>
    </Wrapper>
  );
};

Profile.propTypes = {};

export default Profile;
