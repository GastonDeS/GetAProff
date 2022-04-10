import styled from 'styled-components';

export const Content = styled.div`
  width: 40vw;
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

export const Form = styled.form`
  width: 80%;
  display: flex;
  gap: 1rem;
  flex-direction: column;
`;