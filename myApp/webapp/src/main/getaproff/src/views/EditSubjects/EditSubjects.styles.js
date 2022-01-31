import styled from 'styled-components';

export const Wrapper = styled.div ``;

export const MainContainer = styled.div`
  width: 100%;
  height: 100%;
  padding: 3em;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  flex-direction: column;
  gap: 1rem;
`;

export const Content = styled.div`
  width: 50vw;
  height: fit-content;
  padding: 1rem;
  background-color: var(--primary);
  border-radius: 0.625rem;
  min-width: fit-content;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  gap: 0.75rem;
`;

export const Table = styled.table`
  width: 100%;
  margin: 12px 0 8px 0;
  border: none;
`;

export const Row = styled.tr`
  background: hsla(185, 96%, 22%, 0.35);
  box-shadow: 0 0 9px 0 rgb(0 0 0 / 10%);
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-radius: 0.625rem;
  padding: 0.65rem;
  margin-bottom: 8px;
  width: 100%;
`;

export const Headers = styled.th`
  text-align: start;
  font-size: var(--fontMed);
`;

export const SelectContainer = styled.div`
  width: 90%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 0.5rem 0;
  gap: 1rem;
`;

export const SingleSelect = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;

  p {
    color: var(--secondary);
  }

  input {
  border-radius: 0.625rem;
  padding: 0.25rem 0 0.25rem 0.5rem;
  border: 1px solid var(--secondary);
  outline: none;
  font-size: var(--fontSmall);
  height: fit-content;
  width: 70%;

  &:focus {
    display: inline-block;
    box-shadow: 0 0 0 0.1rem var(--secondary);
    backdrop-filter: blur(12rem);
  }

  &::placeholder {
    font-weight: 100;
    font-size: var(--fontSmall);
  }
  }
`;

export const Request = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: fit-content;
  gap: 0.25rem;

  p {
    color: var(--secondary);
  }

  a {
    color: var(--secondary);
    cursor: pointer;

    &:hover {
      color: black;
  }
  }
`;