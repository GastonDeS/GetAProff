import React, { useRef, useState } from "react";
import i18next from "i18next";

import Navbar from "../../components/Navbar";
import { MainContainer, Wrapper } from "../Home/Home.styles";
import {
  MainDiv,
  SelectContainer,
  FilterContainer,
  Title,
  ModalBody,
  ButtonContainer,
} from "./MyFiles.styles";
import Button from "../../components/Button";
import Modal from "react-bootstrap/Modal";
import "bootstrap/dist/css/bootstrap.min.css";
import Form from "react-bootstrap/Form";
import SelectDropdown from "../../components/SelectDropdown";
import { Row, Headers, Table } from "../EditSubjects/EditSubjects.styles";

const MyFiles = () => {
  const inputFile = useRef(null);
  const [show, setShow] = useState(false);
  const [subject, setSubject] = useState(0);
  const [level, setLevel] = useState(0);

  const levels = [
    "subjects.levels.0",
    "subjects.levels.1",
    "subjects.levels.2",
    "subjects.levels.3",
  ];
  const subjects = ["Matematicas", "Fisica", "Cocina", "Ingles"];

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const openFile = () => {
    inputFile.current.click();
  };

  return (
    <Wrapper>
      <Navbar />
      <MainContainer>
        <MainDiv>
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
                options={levels}
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
                <Headers style={{ width: "5%" }}></Headers>
              </Row>
            </thead>
            <tbody>
              
            </tbody>
          </Table>
          <Button callback={handleShow} text="Agregar Archivos" fontSize="1rem"/>

          {/* MODAL */}
          <Modal show={show} onHide={handleClose} size="xl">
            <Modal.Header closeButton>
              <Title>Agregar Archivo</Title>
            </Modal.Header>
            <ModalBody>
              <input type="file" id="file" ref={inputFile} style={{ display: "none" }}/>
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
                    options={levels}
                    setIndex={setLevel}
                    type="Levels"
                  />
                </FilterContainer>
              </SelectContainer>
              <ul style={{ width: "100%", marginTop: "45px" }}>
                {/* <FileItem fileName={"Archivo1.pdf"}/>
                                <FileItem fileName={"Archivo2.pdf"}/> */}
              </ul>
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
                    callback={handleClose}
                    text="Cancelar"
                  />
                  <Button
                    callback={handleClose}
                    fontSize="1rem"
                    text="Guardar Cambios"
                  />
                </div>
              </ButtonContainer>
            </ModalBody>
          </Modal>
        </MainDiv>
      </MainContainer>
    </Wrapper>
  );
};
export default MyFiles;
