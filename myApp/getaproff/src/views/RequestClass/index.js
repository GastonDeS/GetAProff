import React, {useEffect, useState} from 'react';
import PropTypes from 'prop-types';

import { InputContainer, Content } from './RequestClass.styles';
import { Wrapper, MainContainer, Title } from '../../GlobalStyle';
import Navbar from '../../components/Navbar';
import Button from '../../components/Button';
import SelectDropdown from '../../components/SelectDropdown'
import { userService }  from "../../services";
import AuthService from "../../services/authService";
import { useNavigate,  useParams} from "react-router-dom";
import i18next from "i18next";

const RequestClass = () => {
  const [subject, setSubject] = useState();
  const [level, setLevel] = useState();
  const [levels, setLevels] = useState([]);
  const [subjects, setSubjects] = useState([]);
  const [currentUser, setCurrentUser] = useState();
  const [loading, setLoading] = useState(true);
  const teacher = useParams();
  const [teacherInfo , setTeacherInfo] = useState()
  const navigate = useNavigate();

  const handleClassRequest = async () => {
    const requestData = {
      subject: subject,
      level: level,
      teacherId: teacherInfo.id
    };
    await userService.requestClass(teacher.id, requestData)
        .then(res => navigate(`/classroom/${res.headers.location.split('/').pop()}`));
  }

  const handleLevel = (e) => setLevel(e.target.value);

  const handleSubject = (e) => {
    setSubject(subjects.filter(s => s.id == e.target.value)[0])
  }

  useEffect(async () => {
    setCurrentUser(AuthService.getCurrentUser());
    await userService.getUserInfo(teacher.id)
      .then(data => {
        setTeacherInfo(data)
      });
  }, []);

  useEffect(async () => {
    if (loading) {
      await userService.getUserSubjects(teacher.id)
        .then(data => {
          data.forEach(item => {
            setSubjects((previous) => [
              ...previous,
              {
                name: item.subject,
                id: item.id,
                levels: item.levels,
              }
            ]);
          })
        });
    }
  }, [loading]);

  useEffect(() => {
    if (subjects.length > 0) {
      setSubject(subjects[0]);
    }
  }, [subjects]);

  useEffect( () => {
    if(subject) {
      setLevels(subject.levels.map(item => {
        return {
          name: i18next.t("subjects.levels." + item),
          id: item
        }
      }));
      setLevel(subject.levels[0]);
      setLoading(false);
    };
  }, [subject]);
  
  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <Content>
          <Title>{i18next.t('requestClass.title')} {teacherInfo && teacherInfo.name}</Title>
          <InputContainer>
            <p>{i18next.t('requestClass.selectSubject')}</p>
            {!loading && <SelectDropdown type={i18next.t('requestClass.subjects')} value={subject.id} handler={handleSubject}
                            options={subjects} disabled={subjects.length === 1}/>}
            <p>{i18next.t('requestClass.selectLevel')}l</p>
            {!loading && <SelectDropdown type={i18next.t('requestClass.levels')} handler={handleLevel} options={levels} disabled={levels.length === 1}/>}
          </InputContainer>
          <Button text={i18next.t('requestClass.sendRequest')} fontSize='1rem' callback={handleClassRequest}/>
        </Content>
      </MainContainer>
    </Wrapper>
  );
};

RequestClass.propTypes = {};

export default RequestClass;
