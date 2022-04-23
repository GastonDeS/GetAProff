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
  const [level, setLevel] = useState();
  const [levels, setLevels] = useState([]);
  const [subjects, setSubjects] = useState([]);
  const id = useParams();
  const navigate = useNavigate();

  const handleClassRequest = () => {
    userService.requestClass(id.id)
        .then(res => navigate(`classes/${res.id}`))
  }

  useEffect( () => {
    console.log(id);
    userService.getUserSubjects(id.id)
        .then(data => {setSubjects(data)
        console.log(data);})
  }, [])

  useEffect( () => {
      setLevels(subjects.map(s => s.level))
  }, [subject])
  
  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <Content>
          <Title>Request class</Title>
          <InputContainer>
            <p>Select subject</p>
            <SelectDropdown type="Subjects" setIndex={setSubject} options={subjects.map( s => s.name)}/>
            <p>Select Level</p>
            <SelectDropdown type="Levels" setIndex={setLevel} options={levels}/>
          </InputContainer>
          <Button text='Send request' fontSize='1rem' callback={handleClassRequest}/>
        </Content>
      </MainContainer>
    </Wrapper>
  );
};

RequestClass.propTypes = {};

export default RequestClass;
