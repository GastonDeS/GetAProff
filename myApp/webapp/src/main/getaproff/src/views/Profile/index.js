import React, { useState, useEffect } from "react";
import i18next from "i18next";
import axios from "axios";

import Navbar from "../../components/Navbar";
import {
  Wrapper,
  PageContainer,
  ProfileInfoButtons,
  StarsReviews,
  ProfileImage,
  ProfileInfo,
  ProfileContainer,
  InfoContainer,
  ProfileName,
  StarsReviewsText,
  TabInfoContainer,
  SectionInfo,
  ProfileDataContainer,
  ProfileInfoHeader,
  ProfileInfoText,
  ProfileSubjects,
  SubjectsRowTitle,
  SubjectRowInfo,
  ReviewTitle,
} from './Profile.styles';
import RatingStar from "react-stars";
import Button from "../../components/Button";
import Tab from "../../components/Tab";
import TabItem from "../../components/TabItem";
import { Container, Col } from "react-bootstrap";
import { Row, Table, Headers, Subjects } from "../EditSubjects/EditSubjects.styles"
import Rows from "../../components/Rows";


const Profile = () => {
  const [index, setIndex] = useState(0);
  const profile = Math.round(Math.random());
  const [rows, setRows] = useState({data: []});

  const tabs = ['profile.personal', 'profile.subjects', 'profile.reviews'];

  useEffect(async () => {
    const res = await axios.get("/api/subjects/145");
    setRows({
      data: res.data.map((item, index) => {
        return { ...item, rowId: index };
      }),
    });
  }, []);

  return (
    <Wrapper>
      <Navbar/>
      <PageContainer>
        <ProfileContainer>
          <InfoContainer>
            <ProfileImage
              src={"http://pawserver.it.itba.edu.ar/paw-2021b-6/image/38"}
              alt="profileImg"
            />
            <ProfileInfo>
              <ProfileName>
                <h1 style={{ fontSize: '2rem' }}>Gaston De Schant</h1>
                <StarsReviews>
                  <RatingStar count={5} value={5} size={18} edit={false} />
                  <StarsReviewsText>
                    <p>( 0 Reviews )</p>
                  </StarsReviewsText>
                </StarsReviews>
              </ProfileName>
              <ProfileInfoButtons>
                {profile === 0 ? (
                  <>
                    <Button text="edit certifications" fontSize="1rem"/>
                    <Button text="edit profile" fontSize="1rem"/>
                    <Button text="edit subjects" fontSize="1rem"/>
                    <Button text="share profile" fontSize="1rem"/>
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
          <Tab setIndex={setIndex} style={{ margin: "2rem" }}>
            {tabs.map(tab => {
                return <TabItem fontSize='1rem'>{i18next.t(tab)}</TabItem>
            })}
          </Tab>
          <TabInfoContainer>
            {index === 0 ? (
              // TODO Personal Info
              <SectionInfo>
                <ProfileDataContainer>
                  <ProfileInfoHeader>Description</ProfileInfoHeader>
                  <ProfileInfoText>esta es la descripcion</ProfileInfoText>
                </ProfileDataContainer>
                <ProfileDataContainer>
                  <ProfileInfoHeader>Schedule</ProfileInfoHeader>
                  <ProfileInfoText>monday from 1 am to 2 pm</ProfileInfoText>
                </ProfileDataContainer>
                <ProfileDataContainer>
                  <ProfileInfoHeader>Certifications</ProfileInfoHeader>
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
                          return <Rows key={index} edit={false} rowId={index} subject={item}/>
                      })}
                    </tbody>
                  </Table>
              </ProfileSubjects>
            ) : (
              <SectionInfo>
                <ProfileDataContainer>
                  <ReviewTitle>
                    <h4>Brlin</h4>
                    <RatingStar count={5} value={5} size={18} edit={false} />
                  </ReviewTitle>
                  <ProfileInfoText>Muy buena clase Gaston</ProfileInfoText>
                </ProfileDataContainer>
              </SectionInfo>
            )}
          </TabInfoContainer>
        </ProfileContainer>
      </PageContainer>
    </Wrapper>
  );
};

Profile.propTypes = {};

export default Profile;
