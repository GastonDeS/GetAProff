import styled from "styled-components";

export const Wrapper = styled.div`
  width: 100%;
  height: 25vh;
  margin-bottom: 1rem;
  border-radius: 0.625rem;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: flex-start;
  background-color: white;
`;

export const Margin = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  padding: 3vh 1vw 2.5vh 1.5vw;
  background-color: #f2f2f2;
  width: 35%;
  height: 100%;
  border-top-left-radius: 0.625rem;
  border-bottom-left-radius: 0.625rem;
`;

export const Subject = styled.p`
  font-size: 1.15vw;
  color: var(--secondary);
  margin-bottom: 0.5rem;
`;

export const Title = styled.div`
  height: 13vh;

  h1 {
    font-size: 1.6vw;
    font-weight: bold;
    color: var(--secondary);
    text-align: start;
    margin: 0 0 0.75rem 0;

    @media screen and (max-height: 500px) {
      font-size: 3.5vh;
    }

    @media screen and (max-width: 500px) {
      font-size: 1.6vw!important;
    }
  }
`;

export const Body = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: space-between;
  padding: 3vh 1vw 2.5vh 1.5vw;
  height: 100%;
  width: 65%;
`;

export const ClassInfo = styled.div`
  width: fit-content;
  height: fit-content;
  display: flex;
  flex-direction: column;
  align-items: flex-start;

  h1 {
    font-size: 1.4vw;
    font-weight: bold;
    margin: 0 0 0.5rem 0;

    @media screen and (max-height: 500px) {
      font-size: 3.2vh;
    }

    @media screen and (max-width: 500px) {
      font-size: 1.4vw!important;
    }
  }

  p {
    font-size: 1.1vw;
    margin-bottom: 5px;
  }
`;

export const ButtonContainer = styled.div`
  display: flex;
  column-gap: 0.35rem;
  justify-content: flex-end;
  align-items: center;
  width: 100%;
`;
