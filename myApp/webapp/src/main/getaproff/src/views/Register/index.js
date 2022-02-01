import React, { useState } from "react";
import PropTypes from "prop-types";
import { useNavigate } from "react-router-dom";

import Navbar from "../../components/Navbar";
import Tab from "../../components/Tab";
import TabItem from "../../components/TabItem";
import Input from "../../components/Input";
import {
  WelcomeText,
  Button,
  ButtonContainer,
  Form,
  FormContainer,
  TabContainer,
  InputContainer,
  InputWrapper,
} from "./Register.styles";
import Textarea from "../../components/Textarea";
import DisplayImage from "../../components/DisplayImage";
import { Request, MainContainer, Wrapper } from "../../GlobalStyle";

const Register = () => {
  const [index, setIndex] = useState(0);
  const navigate = useNavigate();

  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <FormContainer>
          <TabContainer>
            <WelcomeText>Welcome</WelcomeText>
            <Tab setIndex={setIndex} style={{ borderRadius: "2rem" }}>
              {/* index = 0 */}
              <TabItem style={{ borderBottomLeftRadius: "2rem" }}>
                Teacher
              </TabItem>
              {/* index = 1 */}
              <TabItem style={{ borderBottomRightRadius: "2rem" }}>
                Student
              </TabItem>
            </Tab>
          </TabContainer>
          <Form>
            <InputContainer>
              <div style={{ width: "80%" }}>
                <DisplayImage />
              </div>
              <InputWrapper>
                <Input type="text" placeholder="Name" />
                <Input type="text" placeholder="Email" />
                <Input type="password" placeholder="Password" />
                <Input type="password" placeholder="Confirm password" />
                {index === 0 ? (
                  <>
                    <Textarea placeholder="Description" />
                    <Textarea placeholder="Schedule" />
                  </>
                ) : (
                  <div></div>
                )}
              </InputWrapper>
            </InputContainer>
            <ButtonContainer>
              <Button>Sign up</Button>
            </ButtonContainer>
            <Request>
              <p>Already have an account?</p>
              <a href="/login">Login</a>
            </Request>
          </Form>
        </FormContainer>
      </MainContainer>
    </Wrapper>
  );
};

Register.propTypes = {};

export default Register;
