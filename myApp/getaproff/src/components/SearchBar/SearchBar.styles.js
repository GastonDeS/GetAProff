import styled from 'styled-components'

export const Wrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  height: fit-content;
  background: white;
  padding-left: 1em;
  border-radius: 10px;
  width: 100%;

  img {
    width: 25px;
    @media screen and (max-width: 1200px) {
      width: 1.75vw;
    }
  }

  input {
    font-size: var(--fontSmall);
    padding: 0.5em;
    border: 0;
    width: 95%;
    background: transparent;
    height: fit-content;
    color: var(--white);
    :focus {
      outline: none;
    }
  }
`;