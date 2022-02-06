import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import axios from "axios";
import i18next from "i18next";

import {
  MainContainer,
  Content,
  SelectContainer,
  SingleSelect
} from "./EditSubjects.styles";
import Navbar from "../../components/Navbar";
import Rows from '../../components/Rows';
import SelectDropdown from "../../components/SelectDropdown";
import Button from "../../components/Button";
import { Request, Wrapper, Title, Levels, Row, Headers, Table } from "../../GlobalStyle";
import CheckBox from "../../components/CheckBox";

const EditSubjects = () => {
  const [rows, setRows] = useState({data: []});
  const [subject, setSubject] = useState();
  const [price, setPrice] = useState();
  const [level, setLevel] = useState();
  const subjects = ['Matematica', 'Quimica', 'Frances'];

  const onChangePrice = (event) => {
    setPrice(event.target.value);
  }

  const handleAddSubject = () => {
    console.log(subject + ' ' + price + ' ' + level)
  }

  const remove = (rowId, url) => {
    // Array.prototype.filter returns new array
    // so we aren't mutating state here
    const arrayCopy = rows.data.filter((row) => row.id !== rowId);
    setRows({ data: arrayCopy });
  }

  useEffect(async () => {
    const res = await axios.get("/teachers/subjects/145");
    setRows({
      data: res.data.map((item) => {
        return { 
          first: item.name,
          second: '$' + item.price + '/' + i18next.t('subjects.hour'),
          third: i18next.t('subjects.levels.' + item.level),
          url: item.id + '/' + item.level,
        };
      }),
    });
  }, []);

  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <Content>
          <Title>Add subject</Title>
          <SelectContainer>
            <SelectDropdown type="Subjects" setIndex={setSubject} options={subjects}/>
            <SingleSelect>
              <p>Enter price per hour:</p>
              <input type="text" placeholder="0" onChange={onChangePrice}/>
            </SingleSelect>
            <SingleSelect>
              <p>Select level:</p>
              <div style={{ width: '70%' }}>
                <SelectDropdown type="Levels" setIndex={setLevel} options={Levels}/>
              </div>
            </SingleSelect>
          </SelectContainer>
          <Button text="Add subject" callback={handleAddSubject} fontSize="1rem"/>
          <Request>
            <p>Can't find the subject you want to teach?</p>
            <a href="/">Request new subject</a>
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
              {rows.data.map((item, index) => {
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
