import React, {useRef, useState} from 'react'
import PropTypes from 'prop-types'
import Navbar from '../../components/Navbar/index'
import { useNavigate } from 'react-router-dom'
import axios from "axios"

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

const Login = (props) => {
  const [mail, setMail] = useState("");
  const [password, setPassword] = useState("");
  const [state, setState] = useState('');

  const navigate = useNavigate();

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
  const handleLogin = (event) => {
    event.preventDefault();
    const loginFormData = new FormData();
    loginFormData.append("mail", mail)

    try {
      // make axios post request
      const response = axios({
        method: "post",
        url: "/api/auth/login",
        data: loginFormData,
        headers: { "Content-Type": "multipart/form-data" },
      });
    } catch(error) {
      console.log(error)
    }
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
              <Button type='submit'>Login</Button>
            </ButtonContainer>
          </FormContainer>
          <SignUp href='/register'>Don't have an account yet? Sign up</SignUp>
        </LoginContainer>
      </MainContainer>
    </Wrapper>
  )
}

Login.propTypes = {

}

export default Login

