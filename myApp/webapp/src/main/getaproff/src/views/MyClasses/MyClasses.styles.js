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

export const Content = styled.div`
  width: 80vw;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  column-gap: 1rem;
`;

export const FilterContainer = styled.div`
  width: 20%;
  height: 100%;
  background-color: var(--primary);
`;

export const CardContainer = styled.div`
  width: 65%;
  height: fit-content;
`;