import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import {
  MainContainer,
  Wrapper,
  Subjects,
  Headers,
  Table,
  Row,
} from "./EditSubjects.styles";
import Navbar from "../../components/Navbar";
import axios from "axios";
import Rows from '../../components/Rows';

import i18next from "i18next";

const EditSubjects = () => {
  const [rows, setRows] = useState({data: []});

  const remove = (rowId, id) => {
    // Array.prototype.filter returns new array
    // so we aren't mutating state here
    const arrayCopy = rows.data.filter((row) => row.id !== rowId);
    setRows({ data: arrayCopy });
  }

  useEffect(async () => {
    const res = await axios.get("/api/subjects/145");
    setRows({
      data: res.data.map((item, index) => {
        return { ...item, rowId: index };
      }),
    });
  }, []);

  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <Subjects>
          <Table>
            <thead>
              <Row>
                <Headers style={{ width: "45%" }}>{i18next.t('subjects.subject')}</Headers>
                <Headers style={{ width: "20%" }}>{i18next.t('subjects.price')}</Headers>
                <Headers style={{ width: "30%" }}>{i18next.t('subjects.level')}</Headers>
                <Headers style={{ width: "5%" }}></Headers>
              </Row>
            </thead>
            <tbody>
              {rows.data.map((item, index) => {
                return <Rows key={index} edit={true} remove={remove} rowId={index} subject={item}/>
              })}
            </tbody>
          </Table>
        </Subjects>
      </MainContainer>
    </Wrapper>
  );
};

EditSubjects.propTypes = {};

export default EditSubjects;
