import styled from 'styled-components';

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
  gap: 1.2rem;
`;

export const Files = styled.tbody`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
`;

export const ButtonContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: fit-content;
  height: fit-content;
  gap: 0.5rem;

  input {
    display: none;
  }
`;