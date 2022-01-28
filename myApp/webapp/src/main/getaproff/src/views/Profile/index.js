import React, { useState } from 'react';
import Navbar from '../../components/Navbar';
import ReactStarts from 'react-stars';
import { Wrapper,PageContainer, ProfileInfoButtons,StarsReviews ,ProfileImage, ProfileInfo, ProfileContainer, InfoContainer, ProfileName, StarsReviewsText, TabInfoContainer, SectionInfo, ProfileDataContainer, ProfileInfoHeader,ProfileInfoText, ProfileSubjects, SubjectsRowTitle, SubjectRowInfo, ReviewTitle } from './profile.styles';
import Button from '../../components/Button';
import Tab from '../../components/Tab';
import TabItem from '../../components/TabItem';
import { Row, Container, Col } from 'react-bootstrap';


const Profile = () => {
    const [index, setIndex] = useState(0);
    const profile = Math.round(Math.random());

    return (
        <Wrapper>
            <Navbar/>
            <PageContainer>
                <ProfileContainer>
                    <InfoContainer>
                        <ProfileImage src={'http://pawserver.it.itba.edu.ar/paw-2021b-6/image/38'} alt='profileImg'/>
                        
                        <ProfileInfo>
                            <ProfileName>
                                <h1>Gaston De Schant</h1>
                                <StarsReviews>
                                    <ReactStarts count={5} value={5} size={18} edit={false}/>
                                    <StarsReviewsText>
                                        <p>( 0 Reviews )</p>
                                    </StarsReviewsText>
                                </StarsReviews>
                            </ProfileName>
                            <ProfileInfoButtons>
                                {
                                    (profile === 0) ? 
                                        <div>
                                            <Button text={'edit certifications'} />
                                            <Button text={'edit profile'} />
                                            <Button text={'edit subjects'} />
                                            <Button text={'share profile'} />
                                        </div>
                                    :
                                        <div>
                                            <Button text={'Request class'} />
                                            <Button text={'Add to favorites'} />
                                            <Button text={'Share profile'} />
                                        </div>
                                }
                            </ProfileInfoButtons>
                        </ProfileInfo>
                    </InfoContainer>
                    <Tab setIndex={setIndex} style={{margin: '2rem'}}>
                        <TabItem style={{ borderBottomLeftRadius: '2rem' }}>Teacher</TabItem> 
                        <TabItem style={{ borderBottomLeftRadius: '2rem' }}>Teacher</TabItem> 
                        <TabItem style={{ borderBottomLeftRadius: '2rem' }}>Teacher</TabItem> 
                    </Tab>
                    <TabInfoContainer>
                        {(index === 0 )? 
                            // TODO Personal Info
                            <SectionInfo>
                                <ProfileDataContainer>
                                    <ProfileInfoHeader>description</ProfileInfoHeader>
                                    <ProfileInfoText>esta es la descripcion</ProfileInfoText>
                                </ProfileDataContainer>
                                <ProfileDataContainer>
                                    <ProfileInfoHeader>schedule</ProfileInfoHeader>
                                    <ProfileInfoText>monday from 1 am to 2 pm</ProfileInfoText>
                                </ProfileDataContainer>
                                <ProfileDataContainer>
                                    <ProfileInfoHeader>certifications</ProfileInfoHeader>
                                    <ul>
                                        <li>
                                            <a href={'www.google.com'} target={'_blank'}>Certificacion Cambridge</a>
                                        </li>
                                    </ul>
                                </ProfileDataContainer>
                            </SectionInfo>
                        : ( index === 1) ?
                            <ProfileSubjects>
                                <Container>
                                    <Row style={{background: '#026670'}}>
                                        <Col xs={7}><SubjectsRowTitle>Subject</SubjectsRowTitle></Col>
                                        <Col xs={2}><SubjectsRowTitle>Price</SubjectsRowTitle></Col>
                                        <Col xs={3}><SubjectsRowTitle>Level</SubjectsRowTitle></Col>
                                    </Row>
                                    <Row>
                                        <Col xs={7}><SubjectRowInfo>Math</SubjectRowInfo></Col>
                                        <Col xs={2}><SubjectRowInfo>800 $/h</SubjectRowInfo></Col>
                                        <Col xs={3}><SubjectRowInfo>Elementary</SubjectRowInfo></Col>
                                    </Row>
                                </Container>
                            </ProfileSubjects>
                        : 
                            <SectionInfo>
                                <ProfileDataContainer>
                                    <ReviewTitle>
                                        <h4>Brlin</h4>
                                        <ReactStarts count={5} value={5} size={18} edit={false}/>
                                    </ReviewTitle>
                                    <ProfileInfoText>Muy buena clase Gaston</ProfileInfoText>
                                </ProfileDataContainer>
                            </SectionInfo>
                        }
                    </TabInfoContainer>
                </ProfileContainer>
            </PageContainer>
        </Wrapper>
    )
};

Profile.propTypes = {};


export default Profile;