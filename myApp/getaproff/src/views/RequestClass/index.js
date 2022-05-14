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
  const [level, setLevel] = useState();
  const [levels, setLevels] = useState([]);
  const [subjects, setSubjects] = useState([]);
  const { register} = useForm();
  const teacher = useParams();
  const [teacherInfo , setTeacherInfo] = useState()
  const navigate = useNavigate();
  const currUser = authService.getCurrentUser();

  const handleClassRequest = () => {
    const requestData = {
      subject: subject,
      level: level,
      studentId: currUser.id
    }
    console.log(requestData)
    userService.requestClass(teacher.id, requestData)
        .then(res => navigate(`/classroom/${res.headers.location.split('/').pop()}`));
  }

  const handleLevel = e => setLevel(e.target.value);

  const handleSubject = e => {
    console.log(e.target.value)
    setSubject(subjects.filter(s => s.id == e.target.value)[0])
  }

  useEffect( () => {
    userService.getUserInfo(teacher.id).then(data => setTeacherInfo(data))
    userService.getUserSubjects(teacher.id)
        .then(data => setSubjects(data))
  }, [])

  useEffect( () => {
    if(subject) {
      setLevels(subject.levels)
      setLevel(subject.levels[0])
    }
  }, [subject])
  
  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <Content>
          <Title>Request class to {teacherInfo && teacherInfo.name}</Title>
          <InputContainer>
            <p>Select subject</p>
            <SelectDropdown type="Subjects" value={subjectIdx} handler={handleSubject}
                            options={subjects}/>
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
