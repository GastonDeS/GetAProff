import React, { useState } from 'react'
import PropTypes from 'prop-types'

import Navbar from '../../components/Navbar'
import Tab from '../../components/Tab'
import TabItem from '../../components/TabItem'
import Input from '../../components/Input'

import { WelcomeText, Button, ButtonContainer, Form, FormContainer, MainContainer, TabContainer, Wrapper, InputContainer, Login } from './Register.styles'
import Textarea from '../../components/Textarea'

const Register = () => {
  const [index, setIndex] = useState(0);

  return (
    <Wrapper>
      <Navbar empty={true}/>
      <MainContainer>
        <FormContainer>
          <TabContainer>
            <WelcomeText>Welcome</WelcomeText>
            <Tab setIndex={setIndex}>
              {/* index = 0 */}
              <TabItem style={{ borderBottomLeftRadius: '2rem' }}>Teacher</TabItem> 
              {/* index = 1 */}
              <TabItem style={{ borderBottomRightRadius: '2rem' }}>Student</TabItem>
            </Tab>
          </TabContainer>
          <Form>
            <InputContainer>
              <Input type="text" placeholder="Name"/>
              <Input type="text" placeholder="Email"/>
              <Input type="password" placeholder="Password"/>
              <Input type="password" placeholder="Confirm password"/>
              {
                index == 0 ? 
                (<>
                    <Textarea placeholder="Description"/>
                    <Textarea placeholder="Schedule"/>
                </>) : <div></div>
              }
            </InputContainer>
            <ButtonContainer>
              <Button>Sign up</Button>
            </ButtonContainer>
            <Login href='/login'>Already have an account? Login</Login>
          </Form>
        </FormContainer>
      </MainContainer>
    </Wrapper>
  )
}

Register.propTypes = {

}

export default Register
