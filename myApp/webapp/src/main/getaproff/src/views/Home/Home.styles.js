import styled from 'styled-components';

export const Wrapper = styled.div `
  height: 100%;
`;

export const MainContainer = styled.div`
  width: 100%;
  height: 100%;
  padding: 3em;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
`;

export const SearchContainer = styled.div `
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  row-gap: 1rem;
  height: fit-content;
  width: 60vw;
  border-radius: 10px;
  background-color: var(--primary);
  margin-bottom: 1rem;
  padding: 1.75rem;
`;

export const SearchBarContainer = styled.div `
  height: fit-content;
  width: 100%;
`;

export const ButtonContainer = styled.div `
  height: fit-content;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  column-gap: 1em;
`;

export const TutorContainer = styled.div`
  grid-template-columns: repeat(auto-fit, minmax(12rem, 16rem));
  width: 90%;
  height: 100%;
  display: grid;
  column-gap: 2.5em;
  justify-content: center;
`;