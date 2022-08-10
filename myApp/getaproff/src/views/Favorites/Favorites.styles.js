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
    width: 100%;
    height: 85%;
    margin: 10px 10px 10px 0;

    h1 {
      font-size: 2rem;
      color: var(--secondary);
      font-weight: bold;
    }
`;

export const TextContainer = styled.div`
    display: flex;
    width: fit-content;
    background-color: var(--tertiary);
    margin: 0.625rem;
    padding: 0.75rem;
    border-radius: 0.625rem;
`;