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
  const [price, setPrice] = useState();
  const [level, setLevel] = useState();
  const [availableSubjects, setAvailableSubjects] = useState([]);
  const [subjectsTaught, setSubjectsTaught] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);

  const navigate = useNavigate();

  const onChangePrice = (event) => {
    setPrice(event.target.value);
  }

  const handleAddSubject = () => {
    console.log(subject + ' ' + price + ' ' + level)
    if (!price || price <= 0) {
      setError(true);
    } else {
      setError(false);
      // TODO: POST SUBJECT
    }
  }

  const remove = (rowId, url) => {
    // Array.prototype.filter returns new array
    // so we aren't mutating state here
    // const arrayCopy = rows.data.filter((row) => row.id !== rowId);
    // setRows({ data: arrayCopy });
  }

  const handleLevelChange = (event) => {
    setLevel(subject.levels.filter(level => Number(level.id) === Number(event.target.value))[0]);
  }

  const handleSubjectChange = (event) => {
    setSubject(availableSubjects.filter((item) => Number(item.id) === Number(event.target.value))[0]);
  };

  useEffect(() => {
    level && setLoading(false);
  }, [level])

  useEffect(() => {
    subject && setLevel(subject.levels[0]);
  }, [subject]);

  useEffect(() => {
    availableSubjects && setSubject(availableSubjects[0]);
  }, [availableSubjects]);

  useEffect(async () => {
    axios.get("/teachers/subjects/145").then(res => {
      setSubjectsTaught(res.data.map((item) => {
        return { 
          first: item.subject,
          second: '$' + item.price + '/' + i18next.t('subjects.hour'),
          third: i18next.t('subjects.levels.' + item.level),
          url: item.id + '/' + item.level,
        };
      }))
    });
    axios.get("/teachers/available-subjects/145").then(res => {
      setAvailableSubjects(res.data.map((item) => {
        var levels = []
        item.levels.forEach(level => {
          levels.push({
            id: level,
            name: "subjects.levels." + level
          })
        })
        return {
          name: item.subject.name,
          id: item.subject.subjectId,
          levels: levels
        }
      }))
    })
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
              <input type="text" placeholder="0" onChange={onChangePrice}/>
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
                <Headers style={{ width: "5%" }}><CheckBox/></Headers>
              </Row>
            </thead>
            <tbody>
              {subjectsTaught.map((item, index) => {
                return <Rows key={index} remove={remove} data={item} rowId={index}/>
              })}
            </tbody>
          </Table>
        </Content>
      </MainContainer>
    </Wrapper>
  );
};

EditSubjects.propTypes = {};

export default EditSubjects;
