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

export const TutorContainer = styled.div`
  grid-template-columns: repeat(auto-fit, minmax(12rem, 16rem));
  width: 90%;
  height: 100%;
  display: grid;
  column-gap: 2.5em;
  justify-content: center;
`;