import React, {useEffect, useState} from 'react';
import PropTypes from 'prop-types';

import { InputContainer, Content } from './RequestClass.styles';
import { Wrapper, MainContainer, Title } from '../../GlobalStyle';
import Navbar from '../../components/Navbar';
import Button from '../../components/Button';
import SelectDropdown from '../../components/SelectDropdown'
import { userService }  from "../../services";
import authService from "../../services/authService";
import { useNavigate,  useParams} from "react-router-dom";
import {useForm} from "react-hook-form";

const RequestClass = () => {
  const [subject, setSubject] = useState();
  const [subjectIdx, setSubjectIdx] = useState();
  const [levelIndex, setLevelIndex] = useState();
  const [levels, setLevels] = useState([]);
  const [subjects, setSubjects] = useState([]);
  const { register} = useForm();
  const id = useParams();
  const navigate = useNavigate();
  const currUser = authService.getCurrentUser();

  const handleClassRequest = () => {
    const requestData = {
      subject: subject,
      levelIdx: levelIndex,
      studentId: currUser.id
    }
    userService.requestClass(id.id, requestData)
        .then(res => navigate(`/classroom/${res.headers.location.split('/').pop()}`));
  }

  const handleLevel = e => setLevelIndex(e.target.value);

  const handleSubject = e => {
    setSubject(subjects[e.target.value]);
  }

  useEffect( () => {
    userService.getUserSubjects(id.id)
        .then(data => setSubjects(data))
  }, [])

  useEffect( () => {
    if(subject)
      setLevels(subject.levels)
    setLevelIndex('0')
  }, [subject])
  
  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <Content>
          <Title>Request class</Title>
          <InputContainer>
            <p>Select subject</p>
            <SelectDropdown type="Subjects" value={subjectIdx} handler={handleSubject} options={subjects.map( s => s.subjectName)}/>
            <p>Select Level</p>
            <SelectDropdown type="Levels" handler={handleLevel} options={levels} disabled={levels.length === 1 }/>
          </InputContainer>
          <Button text='Send request' fontSize='1rem' callback={handleClassRequest}/>
        </Content>
      </MainContainer>
    </Wrapper>
  );
};

RequestClass.propTypes = {};

export default RequestClass;
