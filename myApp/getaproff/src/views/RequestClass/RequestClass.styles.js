import styled from "styled-components";

export const Content = styled.div`
  background-color: var(--primary);
  height: fit-content;
  width: 40vw;
  min-width: fit-content;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  border-radius: 0.625rem;
  padding: 1rem;
  gap: 1rem;
`;

export const InputContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  width: 100%;
  gap: 0.5rem;
  padding: 0 1rem;

  p {
    color: var(--secondary);
    padding-left: 0.5rem;
  }
`;