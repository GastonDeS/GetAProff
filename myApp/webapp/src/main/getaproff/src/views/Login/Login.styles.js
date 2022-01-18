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

export const LoginContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  height: fit-content;
  width: 30vw;
  background: var(--primary);
  /* box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37); */
  backdrop-filter: blur(8.5px);
  -webkit-backdrop-filter: blur(8.5px);
  border-radius: 2rem;
  color: var(--secondary);
  text-transform: uppercase;
  letter-spacing: 0.4rem;
  @media only screen and (max-width: 320px) {
    width: 80vw;
    height: 90vh;
    hr {
      margin-bottom: 0.3rem;
    }
    h4 {
      font-size: small;
    }
  }
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

export const FormContainer = styled.form`
  width: 100%;
  height: 100%;
`;

export const WelcomeText = styled.h2`
  margin: 3rem 0 2rem 0;
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

export const SignUp = styled.a`
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