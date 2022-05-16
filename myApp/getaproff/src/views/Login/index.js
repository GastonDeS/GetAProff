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

const EMAIL_PATTERN = /^$|^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$/;

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
                      placeholder={i18next.t('form.emailPlaceholder')}
                      {...register(
                          "username",
                          {
                            required: {
                              value: true,
                              message: i18next.t('form.requiredField')
                            },
                            pattern: {
                              value: EMAIL_PATTERN,
                              message: i18next.t('form.invalidFormat')
                            }
                          })
                      }
                  />
                  {errors.username && <Error>{errors.username.message}</Error>}
                </InputWrapper>
                <InputWrapper>
                  <StyledInput type="password"
                               placeholder={i18next.t('form.passwordPlaceholder')}
                      {...register(
                          "password",
                          {
                            required: {
                              value: true,
                              message: i18next.t('form.requiredField')
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
