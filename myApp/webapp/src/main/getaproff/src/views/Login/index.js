import React from 'react'
import PropTypes from 'prop-types'
import Navbar from '../../components/Navbar/index'

import { FormContainer, MainContainer, Wrapper, WelcomeText, InputContainer, ButtonContainer, Button, SignUp } from './Login.styles'
import Input from '../../components/Input'

const Login = () => {
  return (
    <Wrapper>
      <Navbar empty='true'/>
      <MainContainer>
        <FormContainer>
          <WelcomeText>Welcome</WelcomeText>
          <InputContainer>
            <Input type="text" placeholder="Email"/>
            <Input type="password" placeholder="Password"/>
          </InputContainer>
          <ButtonContainer>
            <Button>Login</Button>
          </ButtonContainer>
          <SignUp href='/register'>Don't have an account yet? Sign up</SignUp>
        </FormContainer>
      </MainContainer>
    </Wrapper>
  )
}

Login.propTypes = {

}

export default Login

