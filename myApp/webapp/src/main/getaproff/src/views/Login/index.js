import React, {useRef, useState} from 'react'
import PropTypes from 'prop-types'
import Navbar from '../../components/Navbar/index'
import { useNavigate } from 'react-router-dom'
import { isEmail } from 'react-validation'

import {
  LoginContainer,
  MainContainer,
  Wrapper,
  WelcomeText,
  InputContainer,
  ButtonContainer,
  Button,
  SignUp,
  FormContainer
} from './Login.styles'
import Input from '../../components/Input'
import AuthService from '../../services/authService'

const Login = () => {
  const [mail, setMail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState('');

  const navigate = useNavigate();

  const email = (value) => {
    if (!isEmail(value)) {
      return (
        <div className="alert alert-danger" role="alert">
          This is not a valid email.
        </div>
      );
    }
  };

  const onChangeMail = (e) => {
    const mail = e.target.value;
    setMail(mail);
  };

  const onChangePassword = (e) => {
    const password = e.target.value;
    setPassword(password);
    window.location.reload();
  };

  const form = useRef();
  const submitBtn = useRef();

  const handleLogin = (event) => {
    event.preventDefault();
    setMessage('');

    var formData = new FormData();
    formData.append('mail', mail);
    AuthService.login(formData).then(
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
        setMessage(resMessage);
      });
  };

  return (
    <Wrapper>
      <Navbar empty={true}/>
      <MainContainer>
        <LoginContainer>
          <WelcomeText>Welcome</WelcomeText>
          <FormContainer ref={form} onSubmit={handleLogin}>
            <InputContainer>
              <Input type="text" placeholder="Email" onChange={onChangeMail}/>
              <Input type="password" placeholder="Password" onChange={onChangePassword}/>
            </InputContainer>
            <ButtonContainer>
              <Button ref={submitBtn} type='submit'>Login</Button>
            </ButtonContainer>
          </FormContainer>
          <SignUp href='/register'>Don't have an account yet? Sign up</SignUp>
        </LoginContainer>
      </MainContainer>
    </Wrapper>
  )
}

Login.propTypes = {}

export default Login