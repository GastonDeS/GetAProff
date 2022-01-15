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

export const FormContainer = styled.div`
  height: fit-content;
  width: 30vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  row-gap: 1rem;

  @media only screen and (min-width: 360px) {
    width: 80vw;
    h4 {
      font-size: small;
    }
  }
  @media only screen and (min-width: 411px) {
    width: 80vw;
  }
  @media only screen and (min-width: 768px) {
    width: 80vw;
  }
  @media only screen and (min-width: 1024px) {
    width: 70vw;
  }
  @media only screen and (min-width: 1280px) {
    width: 40vw;
  }
`;

export const TabContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: var(--primary);
  border-radius: 2rem;
  padding-bottom: 1em;
`;

export const WelcomeText = styled.h2`
  margin: 3rem 0 2rem 0;
  text-transform: uppercase;
  letter-spacing: 0.4rem;
  color: var(--secondary);
`;

export const Form = styled.div`
  padding-top: 1rem;
  width: 100%;
  height: fit-content;
  display: flex;
  flex-direction: column;
  align-items: center;
  background: var(--primary);
  border-radius: 2rem;
`;

export const InputContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: fit-content;
  width: 100%;
  row-gap: 1em;
`;

export const ButtonContainer = styled.div`
  margin: 1rem 0 1rem 0;
  width: 100%;
  height: 12%;
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const Button = styled.button`
  background: var(--secondary);
  text-transform: uppercase;
  letter-spacing: 0.2rem;
  width: 55%;
  height: 7vh;
  border: none;
  color: white;
  border-radius: 2rem;
  cursor: pointer;

  &:hover {
    color: black;
  }
`;

export const Login = styled.a`
  text-decoration: none;
  color: var(--secondary);
  cursor: pointer;
  text-transform: none;
  letter-spacing: 0;
  margin-bottom: 1rem;
  &:hover {
    color: black;
  }
`;