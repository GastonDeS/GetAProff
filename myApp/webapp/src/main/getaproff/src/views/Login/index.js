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

const EMAIL_PATTERN = /^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/;

const Login = () => {

  const {register, formState: {errors}, handleSubmit} = useForm();
  const [invalidCredentials, setInvalidCredentials] = useState(false);

  const navigate = useNavigate();


  const handleLogin = event => {
    setInvalidCredentials(false)
    AuthService
        .login(event.username, event.password)
        .then(
            () => {
              navigate("/");
            },
            () => {
              setInvalidCredentials(true)
            }
        )
        .catch(() => navigate("/error"))
  };


  return (
      <Wrapper>
        <Navbar empty={true}/>
        <MainContainer>
          <LoginContainer>
            <WelcomeText>Welcome</WelcomeText>
            <Form onSubmit={handleSubmit(handleLogin)}>
              <InputContainer>
                {invalidCredentials && <Error>You have entered an invalid username or password</Error>}
                <InputWrapper>
                  <StyledInput
                      placeholder="example@gmail.com"
                      {...register(
                          "username",
                          {
                            required: {
                              value: true,
                              message: 'This field is required'
                            },
                            pattern: {
                              value: EMAIL_PATTERN,
                              message: "Invalid format"
                            }
                          })
                      }
                  />
                  {errors.username && <Error>{errors.username.message}</Error>}
                </InputWrapper>
                <InputWrapper>
                  <StyledInput type="password"
                               placeholder="password"
                      {...register(
                          "password",
                          {
                            required: {
                              value: true,
                              message: 'This field is required'
                            }
                          })
                      }
                  />
                  {errors.password && <Error>{errors.password.message}</Error>}
                </InputWrapper>
              </InputContainer>
              <ButtonContainer>
                <Button type="submit">
                  Login
                </Button>
              </ButtonContainer>
            </Form>
            <Request>
              <p>Don't have an account yet?</p>
              <button onClick={() => {
                navigate('/register')
              }}>Sign Up
              </button>
            </Request>
          </LoginContainer>
        </MainContainer>
      </Wrapper>
  );
};

Login.propTypes = {};

export default Login;
