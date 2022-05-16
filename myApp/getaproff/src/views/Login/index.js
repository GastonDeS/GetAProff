import React, {useState} from "react";
import Navbar from "../../components/Navbar/index";
import {useNavigate} from "react-router-dom";

import {
  LoginContainer,
  WelcomeText,
  InputContainer,
  ButtonContainer,
  Button,
  Form,
  InputWrapper,
  Error,
} from "./Login.styles";
import {StyledInput} from "../../components/Input";
import AuthService from "../../services/authService";
import {useForm} from "react-hook-form";
import {Request, Wrapper, MainContainer} from "../../GlobalStyle";
import i18next from "i18next";

const EMAIL_PATTERN = /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/;

const Login = () => {

  const {register, formState: {errors}, handleSubmit} = useForm();
  const [invalidCredentials, setInvalidCredentials] = useState(false);

  const navigate = useNavigate();


  const handleLogin = async (event) => {
    setInvalidCredentials(false)
    await AuthService.login(event.username, event.password)
        .then((token) => {
          if (token) {
            navigate(`/`);
          }
          setInvalidCredentials(true);
        });
  };


  return (
      <Wrapper>
        <Navbar empty={true}/>
        <MainContainer>
          <LoginContainer>
            <WelcomeText>{i18next.t('login.welcome')}</WelcomeText>
            <Form onSubmit={handleSubmit(handleLogin)}>
              <InputContainer>
                {invalidCredentials && <Error>{i18next.t('login.invalidCredentials')}</Error>}
                <InputWrapper>
                  <StyledInput
                      placeholder={i18next.t('login.emailPlaceholder')}
                      {...register(
                          "username",
                          {
                            required: {
                              value: true,
                              message: i18next.t('login.requiredField')
                            },
                            pattern: {
                              value: EMAIL_PATTERN,
                              message: i18next.t('login.invalidFormat')
                            }
                          })
                      }
                  />
                  {errors.username && <Error>{errors.username.message}</Error>}
                </InputWrapper>
                <InputWrapper>
                  <StyledInput type="password"
                               placeholder={i18next.t('login.passwordPlaceholder')}
                      {...register(
                          "password",
                          {
                            required: {
                              value: true,
                              message: i18next.t('login.requiredField')
                            }
                          })
                      }
                  />
                  {errors.password && <Error>{errors.password.message}</Error>}
                </InputWrapper>
              </InputContainer>
              <ButtonContainer>
                <Button type="submit">
                  {i18next.t('login.login')}
                </Button>
              </ButtonContainer>
            </Form>
            <Request>
              <p>{i18next.t('login.notRegistered')}</p>
              <button onClick={() => {
                navigate('/users/new')
              }}> {i18next.t('login.signUp')}
              </button>
            </Request>
          </LoginContainer>
        </MainContainer>
      </Wrapper>
  );
};

Login.propTypes = {};

export default Login;
