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

export const StarsReviews = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    width: fit-content;
    height: fit-content;
`;

export const StarsReviewsText = styled.div`
    font-size: 16px;
    font-family: "Roboto Light", sans-serif;
    margin: 0 0 0 5px;
`;

export const ProfileInfoButtons = styled.div`
    display: flex;
    justify-content: flex-end;
    align-items: center;
    width: 100%;
    height: fit-content;
`;

export const TabInfoContainer = styled.div`
    width: 70vw;
    background: white;
    border-bottom: 10px;
    padding: 4px;
    border-bottom-right-radius: 10px;
    border-bottom-left-radius: 10px;
    overflow-y: scroll;
`;

export const SectionInfo = styled.div`
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
`;

export const ProfileDataContainer = styled.div`
    width: 96%;
    border-radius: 10px;
    height: fit-content;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
    margin: 10px;
    background: hsla(185, 96%, 22%, 0.35);
    padding: 12px;
`;

export const ProfileInfoHeader= styled.div`
    font-size: 20px;
    font-family: "Roboto Light", sans-serif;
    font-weight: bold;
    color: black;
    margin-bottom: 8px;
`;

export const ProfileInfoText = styled.div`
    font-size: 16px;
    font-family: "Roboto Light", sans-serif;
    color: black;
    margin-bottom: 8px;
`;


export const ProfileSubjects = styled.div`
    justify-content: center;
    align-items: center;
    display: flex;
    width: 96%;
    margin: auto;
    background: var(--background);
    border-radius: 10px;
`;

export const SubjectsRowTitle = styled.div`
    text-align: start;
    font-size: 20px;
    font-weight: bold;
    font-family: "Roboto Light", sans-serif;
`;

export const SubjectRowInfo = styled.div`
    text-align: start;                        
    font-size: 16px;                          
    font-family: "Roboto Light", sans-serif;  
`;

export const Centered = styled.div`
    width: 100%;
    display: flex;
    height: 300px; 
    justify-content: center;
    align-items: center
`;

export const ReviewTitle = styled.div`
    display: flex;                   
    justify-content: space-between;  
    align-items: center;             
    width: 100%;                     
`;