import React, { useRef, useState } from "react";
import PropTypes from "prop-types";
import Navbar from "../../components/Navbar/index";
import { useNavigate } from "react-router-dom";

import {
  LoginContainer,
  MainContainer,
  Wrapper,
  WelcomeText,
  InputContainer,
  ButtonContainer,
  Button,
  SignUp,
  FormContainer,
  InputWrapper,
  Error,
} from "./Login.styles";
import Input from "../../components/Input";
import AuthService from "../../services/authService";
import { useForm } from "react-hook-form";

const EMAIL_PATTERN =
  '/^(([^<>()[]\\.,;:s@"]+(.[^<>()[]\\.,;:s@"]+)*)|(".+"))@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}])|(([a-zA-Z-0-9]+.)+[a-zA-Z]{2,}))$/';

const Login = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({ mode: "onSubmit" });

  const [mail, setMail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");

  const navigate = useNavigate();

  const onChangeMail = (e) => {
    setMail(e.target.value);
  };

  const onChangePassword = (e) => {
    setPassword(e.target.value);
  };

  const form = useRef();
  const submitBtn = useRef();

  const handleLogin = (event) => {
    event.preventDefault();
    setMessage("");

    AuthService.login(mail, password).then(
      () => {
        navigate("/");
        window.location.reload();
      },
      (error) => {
        const resMessage =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();
        setMessage('Please enter valid credentials');
        console.log(AuthService.getCurrentUser())
      }
    );
  };

  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <LoginContainer>
          <WelcomeText>Welcome</WelcomeText>
          <FormContainer ref={form} onSubmit={handleLogin}>
            <InputContainer>
              <InputWrapper>
                {/* <Input
                  type="text"
                  placeholder="Email"
                  name="mail"
                  register={register}
                  required={{ required: true, pattern: EMAIL_PATTERN }}
                />
                {errors.mail.type === "required" && (
                  <Error>This field is required</Error>
                )} */}
                <Input
                  type="text"
                  placeholder="Email"
                  onChange={onChangeMail}
                />
              </InputWrapper>
              <InputWrapper>
                <Input
                  type="password"
                  placeholder="Password"
                  onChange={onChangePassword}
                />
              </InputWrapper>
              {message && <Error>{message}</Error>}
            </InputContainer>
            <ButtonContainer>
              <Button ref={submitBtn} type="submit">
                Login
              </Button>
            </ButtonContainer>
          </FormContainer>
          <SignUp href="/register">Don't have an account yet? Sign up</SignUp>
        </LoginContainer>
      </MainContainer>
    </Wrapper>
  );
};

Login.propTypes = {};

export default Login;
