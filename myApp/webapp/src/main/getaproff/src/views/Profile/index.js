import React, { useState } from 'react';
import Navbar from '../../components/Navbar';
import RatingStar from 'react-stars';
import { Wrapper, PageContainer, ProfileInfoButtons, StarsReviews ,ProfileImage, ProfileInfo, ProfileContainer, InfoContainer, ProfileName, StarsReviewsText } from './Profile.styles';
import Button from '../../components/Button';
import Tab from '../../components/Tab';
import TabItem from '../../components/TabItem';
import Textarea from '../../components/Textarea';

const Profile = () => {
    const [index, setIndex] = useState(0);

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
                                    <RatingStar count={5} value={5} size={18} edit={false}/>
                                    <StarsReviewsText>
                                        <p>( 0 Reviews )</p>
                                    </StarsReviewsText>
                                </StarsReviews>
                            </ProfileName>
                            <ProfileInfoButtons>
                                <Button text={'edit certifications'} />
                                <Button text={'edit profile'} />
                                <Button text={'edit subjects'} />
                                <Button text={'share profile'} />
                            </ProfileInfoButtons>
                        </ProfileInfo>
                    </InfoContainer>
                    <Tab setIndex={setIndex} style={{margin: '2rem'}}>
                        <TabItem style={{ borderBottomLeftRadius: '2rem' }}>Teacher</TabItem> 
                        <TabItem style={{ borderBottomLeftRadius: '2rem' }}>Teacher</TabItem> 
                        <TabItem style={{ borderBottomLeftRadius: '2rem' }}>Teacher</TabItem> 
                    </Tab>

                    {(index === 0 )? 
                        <Textarea placeholder="description"/>
                    : ( index === 1) ?
                        <Textarea placeholder="hola"/>
                    : 
                        <Textarea placeholder="tercera fila"/>

                    }

                </ProfileContainer>
            </PageContainer>
        </Wrapper>
    )
};

Profile.propTypes = {};


export default Profile;