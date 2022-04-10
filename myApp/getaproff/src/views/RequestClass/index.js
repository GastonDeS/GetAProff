import React, { useState } from 'react';
import PropTypes from 'prop-types';

import { InputContainer, Content } from './RequestClass.styles';
import { Wrapper, MainContainer, Title } from '../../GlobalStyle';
import Navbar from '../../components/Navbar';
import Button from '../../components/Button';
import SelectDropdown from '../../components/SelectDropdown'

const RequestClass = () => {
  const [subject, setSubject] = useState();
  const [level, setLevel] = useState();
  const subjects = ['Matematica', 'Quimica', 'Frances'];
  const levels = [
    "subjects.levels.0",
    "subjects.levels.3",
  ];
  
  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <Content>
          <Title>Request class</Title>
          <InputContainer>
            <p>Select subject</p>
            <SelectDropdown type="Subjects" setIndex={setSubject} options={subjects}/>
            <p>Select Level</p>
            <SelectDropdown type="Levels" setIndex={setLevel} options={levels}/>
          </InputContainer>
          <Button text='Send request' fontSize='1rem'/>
        </Content>
      </MainContainer>
    </Wrapper>
  );
};

RequestClass.propTypes = {};

export default RequestClass;
