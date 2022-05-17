import React, { useEffect, useState } from 'react';

import { Wrapper, MainContainer, Title } from '../../GlobalStyle';
import { Content, InputContainer } from './RequestSubject.styles';
import Navbar from '../../components/Navbar';
import Button from '../../components/Button';
import i18next from "i18next";
import {classesService, subjectService} from "../../services";
import { useNavigate} from "react-router-dom";

const RequestSubject = () => {
  const [subject, setSubject] = useState();
  const [subjectError, setSubjectError] = useState(false);
  const [message, setMessage] = useState();
  const [messageError, setMessageError] = useState(false);
  const navigate = useNavigate();

  const submitRequest = async () => {
    if (!subject) setSubjectError(true);
    if (!message) setMessageError(true);
    const requestData = {
      subject : subject,
      message : message
    };
    await subjectService.requestSubject(requestData)
        .then(res => navigate('/'))
  }

  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <Content>
          <Title>{i18next.t('requestSubject.title')}</Title>
          <InputContainer>
            <h3>{i18next.t('requestSubject.insertSubject')}</h3>
            <input placeholder={i18next.t('requestSubject.insertSubjectPlaceholder')} onChange={(e) => setSubject(e.target.value)}/>
            {
              subjectError && <p>{i18next.t('form.requiredField')}</p>
            }
            <h3>{i18next.t('requestSubject.insertMessage')}</h3>
            <textarea placeholder={i18next.t('requestSubject.insertMessagePlaceholder')} onChange={(e) => setMessage(e.target.value)}/>
            {
              messageError && <p>{i18next.t('form.requiredField')}</p>
            }
          </InputContainer>
          <Button text={i18next.t('requestSubject.sendRequest')} fontSize='1rem' callback={submitRequest}/>
        </Content>
      </MainContainer>
    </Wrapper>
  );
};

RequestSubject.propTypes = {};

export default RequestSubject;
