import styled from "styled-components";

export const SearchContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 1rem;
  height: fit-content;
  width: fit-content;
  border-radius: 10px;
  background-color: var(--primary);
  margin-bottom: 1rem;
  padding: 1.25rem 1.75rem;
`;

export const SearchBarContainer = styled.div`
  height: fit-content;
  width: 55vw;
`;

export const ButtonContainer = styled.div`
  height: fit-content;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  column-gap: 1em;
`;

export const Content = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
  width: 90%;
  gap: 1rem;
  margin-bottom: 1rem;

  h2 {
    font-size: 2rem;
    color: var(--secondary);
    margin-bottom: 0;
  }
`;

export const TutorContainer = styled.div`
  grid-template-columns: repeat(auto-fit, minmax(12rem, 16rem));
  width: 100%;
  height: 100%;
  display: grid;
  column-gap: 2.5em;
  justify-content: center;
`;
