import React, {useRef, useState} from "react";
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
import Input, {StyledInput} from "../../components/Input";
import AuthService from "../../services/authService";
import {useForm} from "react-hook-form";
import {Request, Wrapper, MainContainer} from "../../GlobalStyle";

const EMAIL_PATTERN = /^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/;

const Login = () => {

  const [mail, setMail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const {register, formState: {errors}, handleSubmit} = useForm();
  const [invalidCredentials, setInvalidCredentials] = useState(false);

  const navigate = useNavigate();

  const onChangeMail = (event) => {
    setMail(event.target.value);
  };

  const onChangePassword = (event) => {
    setPassword(event.target.value);
  };

  const handleLogin = event => {
    AuthService
        .login(event.username, event.password)
        .then(
            (u) => {
              console.log('aaa' + u)
              navigate("/");
            }
        )
  };


  return (
      <Wrapper>
        <Navbar empty={true}/>
        <MainContainer>
          <LoginContainer>
            <WelcomeText>Welcome</WelcomeText>
            <Form onSubmit={handleSubmit(handleLogin)}>
              <InputContainer>
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
                  {errors.username && <span className="text-danger">{errors.username.message}</span>}
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
                  {errors.password && <span className="text-danger">{errors.password.message}</span>}
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
