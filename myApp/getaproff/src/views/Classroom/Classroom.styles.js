import styled from 'styled-components';

export const Wrapper = styled.div ``;


export const MainContainer = styled.div`
  width: 100%;
  height: 100%;
  padding: 3em;
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const PageContainer = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    width: 90%;
    height: 85%;
    margin: 10px 10px 10px 0;

`;

export const ImageHeader = styled.img`
    width: 90%;
    margin-bottom: 20px;
    object-fit: cover;
    border-radius: 10px;
    height: 200px;
`;

export const ClassroomContainer = styled.div`
    width: 100%;
    height: fit-content;
    display: flex;
    align-items: flex-start;
    justify-content: space-evenly;
    ${'p'} {
        margin: 10px 0;
    }
`;

export const ClassroomSidePanel = styled.div`
    display: flex;
    flex-direction: column;
    width: 25%;
`;

export const ClassContentSide = styled.div`
    background-color: white;
    border-radius: 10px;
    margin: 0 0 10px 0 ;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
    height: 100%;
    padding: 8px;
`;

export const ClassStatus = styled.div`
    margin: 0 0 10px 0;
    border-radius: 10px;
    width: 100%;
    padding: 5px;
`;

export const ClassroomCenterPanel = styled.div`
    display: flex;
    flex-direction: column;
    width: 50%;
    align-items: center;
`;

export const SharedFilesContainer = styled.div`
    display: flex;
    flex-direction: column;
    width: 100%;
    margin-top: 10px;
`;


export const SubjectsRow = styled.div`
    background: hsla(185, 96%, 22%, 0.35);
    box-shadow: 0 0 9px 0 rgba(0, 0, 0, 0.1);
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-radius: 10px;
    padding: 5px;
    margin-bottom: 8px;
    width: 100%;
`;

export const PostFormContainer = styled.form`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 90%;
    flex: 0 0 auto;
    padding: 5px 10px;
    background-color: white;
    border-radius: 10px;
    margin: 0 0 10px 0; 
`;

export const ButtonContainer = styled.div `
    margin-top: 10px;
    width: 100%;
    display: flex;
    justify-content: space-between;
    flex-direction: row;
    align-items: center;
`;

export const BigBox = styled.div`
    width: 90%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
`;

export const Ul = styled.ul`
    list-style: none;
    padding: 0;
;`

export const PostBox = styled.div`
    width: 100%;
    padding: 10px 20px 10px 20px;
    border-radius: 10px;
    margin-top: 20px;
    background-color: white;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
`;

export const ButtonHolder =styled.div`
    display: flex;
    height: fit-content;
    width: 100%;
    justify-content: space-between;
    align-items: center;
`;