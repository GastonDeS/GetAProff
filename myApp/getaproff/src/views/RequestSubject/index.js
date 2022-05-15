import React from 'react';
import PropTypes from 'prop-types';

import { Wrapper, MainContainer, Title } from '../../GlobalStyle';
import { Content, InputContainer } from './RequestSubject.styles';
import Navbar from '../../components/Navbar';
import Button from '../../components/Button';
import i18next from "i18next";

const RequestSubject = () => {
  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <Content>
          <Title>{i18next.t('requestSubject.title')}</Title>
          <InputContainer>
            <p>{i18next.t('requestSubject.insertSubject')}</p>
            <input placeholder={i18next.t('requestSubject.insertSubjectPlaceholder')}/>
            <p>{i18next.t('requestSubject.insertMessage')}</p>
            <textarea placeholder={i18next.t('requestSubject.insertMessagePlaceholder')}/>
          </InputContainer>
          <Button text={i18next.t('requestSubject.sendRequest')} fontSize='1rem'/>
        </Content>
      </MainContainer>
    </Wrapper>
  );
};

RequestSubject.propTypes = {};

export default RequestSubject;
