import React, { useEffect, useState } from "react";
import axios from "axios";
import i18next from "i18next";
import { useNavigate } from "react-router-dom";

import {
  MainContainer,
  Content,
  SelectContainer,
  SingleSelect,
  ErrorMsg
} from "./EditSubjects.styles";
import Navbar from "../../components/Navbar";
import Rows from '../../components/Rows';
import SelectDropdown from "../../components/SelectDropdown";
import Button from "../../components/Button";
import { Request, Wrapper, Title, Levels, Row, Headers, Table } from "../../GlobalStyle";
import CheckBox from "../../components/CheckBox";

const EditSubjects = () => {
  const [subject, setSubject] = useState();
  const [price, setPrice] = useState("");
  const [level, setLevel] = useState();
  const [availableSubjects, setAvailableSubjects] = useState([]);
  const [subjectsTaught, setSubjectsTaught] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);
  const [checkAll, setCheckAll] = useState(false);
  const [deleted, setDeleted] = useState([])

  const navigate = useNavigate();

  const onChangePrice = (event) => {
    setPrice(event.target.value);
  }

  const handleAddSubject = async() => {
    if (!price || price <= 0) {
      setError(true);
    } else {
      setError(false);
      await axios.post("/users/145/" + subject.id + "/" + price + "/" + level.id).catch(error => {});
      setSubjectsTaught([...subjectsTaught, {
        name: subject.name,
        price: '$' + price + '/' + i18next.t('subjects.hour'),
        level: i18next.t('subjects.levels.' + level.id),
        url: '/' + subject.id + '/' + level.id,
        checked: false
      }])
      setPrice("");
      setLoading(true);
    }
  }

  const handleCheckedsubject = (checked, subject) => {
    setSubjectsTaught(subjectsTaught.map(item => {
      if (item.url === subject.url) item.checked = checked;
      return item;
    }));
  }

  const handleCheckAll = (event) => {
    setCheckAll(event.target.checked);
    if (event.target.checked) {
      setSubjectsTaught(subjectsTaught.map(subject => {
        return {
          ...subject,
          checked: true
        }
      }));
    } else {
      setSubjectsTaught(subjectsTaught.map(subject => {
        return {
          ...subject,
          checked: false
        }
      }));
    }
  }

  const handleDeleteSubjects = () => {
    setDeleted([]);
    subjectsTaught.forEach((subject) => {
      if (subject.checked) {
        axios
        .delete("/users/145" + subject.url)
        .then(() => {
          setDeleted((previous) => [...previous, subject.url]);
        })
        .catch((error) => {});
      }
    });
  }

  const handleLevelChange = (event) => {
    setLevel(subject.levels.filter(level => Number(level.id) === Number(event.target.value))[0]);
  }

  const handleSubjectChange = (event) => {
    setSubject(availableSubjects.filter((item) => Number(item.id) === Number(event.target.value))[0]);
  };

  const fetchSubjectsTaught = async (id) => {
    await axios.get("/users/145/subjects").then(res => {
      setSubjectsTaught(res.data.map((item) => {
        return { 
          name: item.subject,
          price: '$' + item.price + '/' + i18next.t('subjects.hour'),
          level: i18next.t('subjects.levels.' + item.level),
          url: '/' + item.id + '/' + item.level,
          checked: false
        };
      }))
    });
  }

  const fetchAvailableSubjects = async (id) => {
    await axios.get("/users/available-subjects/145").then(res => {
      setAvailableSubjects(res.data.map((item) => {
        var levels = []
        item.levels.forEach(level => {
          levels.push({
            id: level,
            name: i18next.t('subjects.levels.' + level)
          })
        })
        return {
          name: item.name,
          id: item.subjectId,
          levels: levels
        }
      }))
    });
  }

  useEffect(() => {
    deleted.forEach((url) => {
      setSubjectsTaught(subjectsTaught.filter((item) => item.url !== url));
    });
    fetchAvailableSubjects();
  }, [deleted]);

  useEffect(() => {
    level && setLoading(false);
  }, [level])

  useEffect(() => {
    subject && setLevel(subject.levels[0]);
  }, [subject]);

  useEffect(() => {
    availableSubjects && setSubject(availableSubjects[0]);
  }, [availableSubjects]);

  useEffect(() => {
    loading && fetchAvailableSubjects();
  }, [loading]);

  useEffect(() => {
    if (subjectsTaught && subjectsTaught.length === 0) setCheckAll(false);
  }, [subjectsTaught]);

  useEffect(async () => {
    fetchSubjectsTaught();
  }, []);

  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <Content>
          <Title>{i18next.t('editSubjects.title')}</Title>
          <SelectContainer>
            {
              !loading && <SelectDropdown options={availableSubjects} handler={handleSubjectChange}/>
            }
            <SingleSelect>
              <p>{i18next.t('editSubjects.price')}</p>
              <input type="text" placeholder="0" onChange={onChangePrice} value={price}/>
            </SingleSelect>
            {
              error && <ErrorMsg>{i18next.t('errors.price')}</ErrorMsg>
            }
            <SingleSelect>
              <p>{i18next.t('editSubjects.level')}</p>
              <div style={{ width: '70%' }}>
                {
                  !loading && <SelectDropdown options={subject.levels} handler={handleLevelChange} value={level.id}/>
                }
              </div>
            </SingleSelect>
          </SelectContainer>
          <Button text={i18next.t('editSubjects.addSubject')} callback={handleAddSubject} fontSize="1rem"/>
          <Request>
            <p>{i18next.t('editSubjects.request.question')}</p>
            <button onClick={() => { navigate('/request-subject')}}>{i18next.t('editSubjects.request.access')}</button>
          </Request>
        </Content>
        <Content>
          <Table>
            <thead>
              <Row>
                <Headers style={{ width: "45%" }}>{i18next.t('subjects.subject')}</Headers>
                <Headers style={{ width: "20%" }}>{i18next.t('subjects.price')}</Headers>
                <Headers style={{ width: "30%" }}>{i18next.t('subjects.level')}</Headers>
                <Headers style={{ width: "5%" }}><CheckBox checked={checkAll} handleCheck={handleCheckAll}/></Headers>
              </Row>
            </thead>
            <tbody>
              {subjectsTaught.map((item, index) => {
                return <Rows key={index} handleCheck={handleCheckedsubject} data={item} checked={item.checked}/>
              })}
            </tbody>
          </Table>
          {subjectsTaught.filter(subject => subject.checked).length === 0 ? (
              <></>
            ) : (
              <Button
                callback={handleDeleteSubjects}
                text="Borrar Materias"
                fontSize="1rem"
              />
          )}
        </Content>
      </MainContainer>
    </Wrapper>
  );
};

EditSubjects.propTypes = {};

export default EditSubjects;
