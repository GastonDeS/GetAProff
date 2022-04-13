import React from 'react';
import PropTypes from 'prop-types';

import { Wrapper, MainContainer, Title } from '../../GlobalStyle';
import { Content, InputContainer } from './RequestSubject.styles';
import Navbar from '../../components/Navbar';
import Button from '../../components/Button';

const RequestSubject = () => {
  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <Content>
          <Title>Request subject</Title>
          <InputContainer>
            <p>Insert subject</p>
            <input placeholder='Ex. Algebra'/>
            <p>Your message:</p>
            <textarea placeholder='Tell us why you want to request this subject'/>
          </InputContainer>
          <Button text='Send request' fontSize='1rem'/>
        </Content>
      </MainContainer>
    </Wrapper>
  );
};

RequestSubject.propTypes = {};

export default RequestSubject;
