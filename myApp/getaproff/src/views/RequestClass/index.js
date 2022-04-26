import React, {useEffect, useState} from 'react';
import PropTypes from 'prop-types';

import { InputContainer, Content } from './RequestClass.styles';
import { Wrapper, MainContainer, Title } from '../../GlobalStyle';
import Navbar from '../../components/Navbar';
import Button from '../../components/Button';
import SelectDropdown from '../../components/SelectDropdown'
import { userService }  from "../../services";
import { useNavigate,  useParams} from "react-router-dom";

const RequestClass = () => {
  const [subject, setSubject] = useState();
  const [subjectIdx, setSubjectIdx] = useState();
  const [level, setLevel] = useState();
  const [levels, setLevels] = useState([]);
  const [subjects, setSubjects] = useState([]);
  const id = useParams();
  const navigate = useNavigate();

  const handleClassRequest = () => {
    userService.requestClass(id.id)
        .then(res => navigate(`classroom/${res.id}`))
  }

  const handleSubject = e => setSubject(subjects[e.target.value]);

  useEffect( () => {
    console.log(id);
    userService.getUserSubjects(id.id)
        .then(data => {setSubjects(data)
        console.log(data);})
  }, [])

  useEffect( () => {
      setLevels(subjects.filter(o => o.name === subject.name).map(value => value.level))
  }, [subject])
  
  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <Content>
          <Title>Request class</Title>
          <InputContainer>
            <p>Select subject</p>
            <SelectDropdown type="Subjects" value={subjectIdx} handler={handleSubject} options={subjects.map( s => s.name)}/>
            <p>Select Level</p>
            <SelectDropdown type="Levels" handler={setLevel} options={levels} disabled={levels.length === 1 }/>
          </InputContainer>
          <Button text='Send request' fontSize='1rem' callback={handleClassRequest}/>
        </Content>
      </MainContainer>
    </Wrapper>
  );
};

RequestClass.propTypes = {};

export default RequestClass;
