import styled from 'styled-components';

export const Wrapper = styled.div ``;

export const MainContainer = styled.div`
  width: 100%;
  height: 100%;
  padding: 3em;
  display: flex;
  align-items: flex-start;
  justify-content: center;
`;

export const ProfileInfo = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: flex-start;
    width: 90%;
    height: 85%;
    margin: 10px 10px 10px 0;
`;

export const ProfileImage = styled.img`
    width: 175px;
    height: 175px;
    object-fit: cover;
    margin: 15px;
    border-radius: 5px;
`;

export const ProfileContainer = styled.div`
    width: 70.1vw;;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
`;

export const InfoContainer = styled.div`
    width: 100%;
    height: 220px;
    display: flex;
    justify-content: center;
    align-items: center;
    background: hsla(185, 96%, 22%, 0.35);
    border-radius: 10px;
    margin: 8px;
    min-height: 200px;
`;

export const ProfileName = styled.div`
    display: flex;
    justify-content: flex-start;
    flex-direction: column;
    align-items: flex-start;
    width: fit-content;
    height: fit-content;
    margin: 8px;
`;

export const PageContainer = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    width: 100vw;
    min-height: 100vh;
    padding: 2em;
    min-width: 600px;
`;
