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
    width: 28px;
    @media screen and (max-width: 1200px) {
      width: 1.25vw;
    }
  }

  input {
    font-size: var(--fontMed);
    padding: 0.5em;
    border: 0;
    width: 95%;
    background: transparent;
    height: fit-content;
    color: var(--white);
    :focus {
      outline: none;
    }

    @media screen and (max-width: 1200px) {
      font-size: 1.25vw;
    }
  }
`;

export const Content = styled.div`
  position: relative;
  max-width: var(--maxWidth);
  width: 100%;
  height: 100%;
  /* height: 55px; */
  background-color: white;

  img {
    position: absolute;
    left: 15px;
    top: 14px;
    width: 28px;
  }

  input {
    font-size: var(--fontMed);
    position: absolute;
    left: 0;
    padding: 0 0 0 60px;
    border: 0;
    width: 95%;
    background: transparent;
    height: 40px;
    color: var(--white);
    :focus {
      outline: none;
    }
  }
`;