import styled from "styled-components";

export const ProfileInfo = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-start;
  width: 90%;
  height: 100%;
`;

export const ProfileContainer = styled.div`
  width: 70vw;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  gap: 1rem;
`;

export const InfoContainer = styled.div`
  width: 100%;
  height: 30vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: var(--tertiary);
  border-radius: 0.625rem;
  min-height: 200px;
  padding: 0.625rem 0.9rem;
  gap: 1rem;

  img {
    width: 175px;
    height: 175px;
    object-fit: cover;
    border-radius: 0.25rem;
  }
`;

export const ProfileName = styled.div`
  display: flex;
  justify-content: flex-start;
  flex-direction: column;
  align-items: flex-start;
  width: fit-content;
  height: fit-content;
  margin: 0.5rem 0 0 0.5rem;
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
  gap: 0.5rem;
`;

export const ProfileInfoButtons = styled.div`
  display: flex;
  justify-content: flex-end;
  align-items: center;
  width: 100%;
  height: fit-content;
  gap: 0.3rem;
`;

export const TabInfoContainer = styled.div`
  width: 70vw;
  background: white;
  padding: 0.3rem;
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
  gap: 0.7rem;
  padding: 0.7rem 0;
`;

export const ProfileDataContainer = styled.div`
  width: 96%;
  border-radius: 0.625rem;
  height: fit-content;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  background: var(--tertiary);
  padding: 0.7rem;
  gap: 0.5rem;
`;

export const ProfileSubjects = styled.div`
  justify-content: center;
  align-items: center;
  display: flex;
  width: 96%;
  margin: auto;
`;

export const ReviewTitle = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
`;

export const TabContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
`;
