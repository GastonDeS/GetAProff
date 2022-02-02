import React, { useRef, useState, useEffect } from "react";
import i18next from "i18next";
import axios from "axios";

import Navbar from "../../components/Navbar";
import { MainContainer, Wrapper } from "../../GlobalStyle";
import {
  Content,
  SelectContainer,
  FilterContainer,
  ModalBody,
  ButtonContainer,
  Files,
} from "./MyFiles.styles";
import Button from "../../components/Button";
import Modal from "react-bootstrap/Modal";
import SelectDropdown from "../../components/SelectDropdown";
import Rows from '../../components/Rows';
import { Title, Levels, Row, Headers, Table } from "../../GlobalStyle";
import CheckBox from "../../components/CheckBox";

const MyFiles = () => {
  const inputFile = useRef(null);
  const [show, setShow] = useState(false);
  const [subject, setSubject] = useState(0);
  const [level, setLevel] = useState(0);
  const [rows, setRows] = useState({data: []});
  const [files, setFiles] = useState([]);

  const subjects = ["Matematicas", "Fisica", "Cocina", "Ingles"];

  const handleShow = () => setShow(current => !current);

  const displayFiles = (event) => {
    if (event.target.files && event.target.files[0]) {
      setFiles([...files, {file: event.target.files[0], name: event.target.files[0].name}]);
    }
  };

  const openFile = () => {
    inputFile.current.click();
  };

  const remove = (rowId, url) => {
    // Array.prototype.filter returns new array
    // so we aren't mutating state here
    const arrayCopy = rows.data.filter((row) => row.id !== rowId);
    setRows({ data: arrayCopy });
  }

  useEffect(async () => {
    const res = await axios.get("/api/subject-files/145");
    setRows({
      data: res.data.map((item) => {
        return { 
          first: item.name, 
          second: item.subject,
          third: i18next.t('subjects.levels.' + item.level),
          url: item.fileId,
        };
      }),
    });
  }, []);

  return (
    <Wrapper>
      <Navbar />
      <MainContainer>
        <Content>
          <Title>Mis archivos</Title>
          <h5>Filtrar por:</h5>
          <SelectContainer>
            <FilterContainer>
              <p>Subject:</p>
              <SelectDropdown
                options={subjects}
                setIndex={setSubject}
                type="Subjects"
              />
            </FilterContainer>
            <FilterContainer>
              <p>Level:</p>
              <SelectDropdown
                options={Levels}
                setIndex={setLevel}
                type="Levels"
              />
            </FilterContainer>
          </SelectContainer>
          <Table className="subjects-table">
            <thead>
              <Row>
                <Headers style={{ width: "45%" }}>{i18next.t('files.file')}</Headers>
                <Headers style={{ width: "20%" }}>{i18next.t('files.subject')}</Headers>
                <Headers style={{ width: "30%" }}>{i18next.t('files.level')}</Headers>
                <Headers style={{ width: "5%" }}>
                  <CheckBox/>
                </Headers>
              </Row>
            </thead>
            <tbody>
              {rows.data.map((item, index) => {
                return <Rows key={index} remove={remove} data={item} rowId={index}/>
              })}
            </tbody>
          </Table>
          <Button callback={handleShow} text="Agregar Archivos" fontSize="1rem"/>

          {/* MODAL */}
          <Modal show={show} onHide={handleShow} size="xl">
            <Modal.Header closeButton>
              <Title>Agregar Archivo</Title>
            </Modal.Header>
            <ModalBody>
              <input type="file" id="file" ref={inputFile} style={{ display: "none" }} onChange={displayFiles}/>
              <h3>Elija en que clases quiere disponiblizar el archivo</h3>
              <SelectContainer>
                <FilterContainer>
                  <p>Subject:</p>
                  <SelectDropdown
                    options={subjects}
                    setIndex={setSubject}
                    type="Subjects"
                  />
                </FilterContainer>
                <FilterContainer>
                  <p>Level:</p>
                  <SelectDropdown
                    options={Levels}
                    setIndex={setLevel}
                    type="Levels"
                  />
                </FilterContainer>
              </SelectContainer>
              <table style={{ width: '90%' }}>
                <Files>
                  {files && files.map((item, index) => {
                    return <Rows key={index} data={item.name} rowId={index} multi={false} check={false}/>
                  })}
                </Files>
              </table>
              <ButtonContainer>
                <Button
                  text="Elegir archivos"
                  fontSize="1rem"
                  callback={openFile}
                />
                <div>
                  <Button
                    color="grey"
                    fontSize="1rem"
                    callback={handleShow}
                    text="Cancelar"
                  />
                  <Button
                    callback={handleShow}
                    fontSize="1rem"
                    text="Guardar Cambios"
                  />
                </div>
              </ButtonContainer>
            </ModalBody>
          </Modal>
        </Content>
      </MainContainer>
    </Wrapper>
  );
};
export default MyFiles;
