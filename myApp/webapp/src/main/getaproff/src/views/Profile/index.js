import React from 'react';
import Navbar from '../../components/Navbar';
import { Wrapper,PageContainer ,ProfileImage, ProfileInfo, ProfileContainer, InfoContainer, ProfileName } from './profile.styles';


const Profile = () => {
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
                            </ProfileName>
                        </ProfileInfo>
                    </InfoContainer>
                </ProfileContainer>
            </PageContainer>
        </Wrapper>
    )
};

Profile.propTypes = {};


export default Profile;