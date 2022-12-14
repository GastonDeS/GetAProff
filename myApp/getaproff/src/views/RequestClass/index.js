import React, {useEffect, useState} from 'react';
import { InputContainer, Content } from './RequestClass.styles';
import { Wrapper, MainContainer, Title } from '../../GlobalStyle';
import Navbar from '../../components/Navbar';
import Button from '../../components/Button';
import SelectDropdown from '../../components/SelectDropdown'
import { userService, classesService }  from "../../services";
import { useNavigate,  useParams} from "react-router-dom";
import i18next from "i18next";
import { handleService } from '../../handlers/serviceHandler';
import { handleAuthentication } from '../../handlers/accessHandler';

const RequestClass = () => {
  const [subject, setSubject] = useState();
  const [level, setLevel] = useState();
  const [levels, setLevels] = useState([]);
  const [subjects, setSubjects] = useState([]);
  const [loading, setLoading] = useState(true);
  const teacher = useParams();
  const [teacherInfo , setTeacherInfo] = useState();
  const [fetch, setFetch] = useState(false);
  const navigate = useNavigate();

  const handleClassRequest = async () => {
    const requestData = {
      teacherId: teacher.id,
      subject: subject,
      level: level
    };
    const res = await classesService.requestClass(requestData);
    handleService(res, navigate);
    navigate(`/classroom/${res.headers.location.split('/').pop()}`);
  }

  const handleIndex = (e) => setLevel(e.target.value);

  const handleSubject = (e) => setSubject(subjects.filter(s => s.id == e.target.value)[0]);

  useEffect(() => {
    handleAuthentication(navigate);
    setFetch(true);
  }, [])

  useEffect(async () => {
    if (fetch) {
      const res = await userService.getUserInfo(teacher.id);
      const data = handleService(res, navigate);
      setTeacherInfo(data);
    }
  }, [fetch])

  useEffect(async () => {
    if (teacherInfo) {
      const res = await userService.getUserSubjects(teacherInfo.id);
      const data = handleService(res, navigate);
      data.forEach(item => {
        setSubjects((previous) => [
          ...previous,
          {
            name: item.subject,
            id: item.id,
            prices: item.prices,
            levels: item.levels,
          }
        ]);
      })
    }
  }, [teacherInfo])

  useEffect(() => {
    if (subjects.length > 0) {
      setSubject(subjects[0]);
    }
  }, [subjects])

  useEffect( () => {
    if(subject) {
      setLevels(subject.levels.map((item) => {
        return {
          name: i18next.t("subjects.levels." + item),
          id: item
        }
      }));
      setLevel(subject.levels[0]);
      setLoading(false);
    };
  }, [subject])
  
  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <Content>
          <Title>{i18next.t('requestClass.title')} {teacherInfo && teacherInfo.name}</Title>
          <InputContainer>
            <p>{i18next.t('requestClass.selectSubject')}</p>
            {!loading && <SelectDropdown value={subject.id} handler={handleSubject}
                            options={subjects} disabled={subjects.length === 1}/>}
            <p>{i18next.t('requestClass.selectLevel')}</p>
            {!loading && <SelectDropdown value={level} handler={handleIndex} options={levels} disabled={levels.length === 1}/>}
          </InputContainer>
          <Button text={i18next.t('requestClass.sendRequest')} fontSize='1rem' callback={handleClassRequest}/>
        </Content>
      </MainContainer>
    </Wrapper>
  );
};

RequestClass.propTypes = {};

export default RequestClass;
