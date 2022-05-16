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

  h3 {
    color: var(--secondary);
    padding-left: 0.5rem;
    font-weight: normal;
  }

  p {
    color: red;
    padding-left: 0.5rem;
  }

  input {
    border-radius: 0.625rem;
    padding: 0.25rem 0.75rem;
    border: 1px solid var(--secondary);
    outline: none;
    font-size: var(--fontSmall);
    height: fit-content;
    width: 100%;

    &:focus {
      display: inline-block;
      box-shadow: 0 0 0 0.1rem var(--secondary);
      backdrop-filter: blur(12rem);
    }

    &::placeholder {
      font-size: var(--fontSmall);
    }
  }

  textarea {
    border-radius: 0.625rem;
    width: 100%;
    height: 16vh;
    padding: 0.35rem 0.75rem;
    border: 1px solid var(--secondary);
    outline: none;
    color: black;
    font-size: var(--fontSmall);
    resize: none;

    &:focus {
      display: inline-block;
      box-shadow: 0 0 0 0.1rem var(--secondary);
      backdrop-filter: blur(12rem);
    }

    &::placeholder {
      font-size: var(--fontSmall);
    }
  }
`;