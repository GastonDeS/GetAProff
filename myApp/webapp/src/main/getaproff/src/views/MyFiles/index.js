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
import { Title, Row, Headers, Table } from "../../GlobalStyle";
import CheckBox from "../../components/CheckBox";

const ALL_LEVELS = 4;

const MyFiles = () => {
  const initialLevel = {
    name: i18next.t('subjects.all'),
    id: ALL_LEVELS
  }
  const inputFile = useRef(null);
  const [show, setShow] = useState(false);
  const [subject, setSubject] = useState();
  const [level, setLevel] = useState(ALL_LEVELS);
  const [currentFiles, setCurrentFiles] = useState({data: []});
  const [newFiles, setNewFiles] = useState([]);
  const [currentLevels, setCurrentLevels] = useState([initialLevel]);
  const [currentSubjects, setCurrentSubjects] = useState([]);
  const [selected, setSelected] = useState([]);

  const handleShow = () => setShow(current => !current);

  const displayNewFiles = (event) => {
    if (event.target.files && event.target.files[0]) {
      setNewFiles([...newFiles, {file: event.target.files[0], name: event.target.files[0].name}]);
    }
  };

  const handleSelected = (item) => {
    setSelected(previous => [...previous, item]);
  };

  const handleDeleteAll = () => {
    selected.map((item) => {
      for (var i = 0; i < currentFiles.data.length; i++) {
        if (item === currentFiles.data[i]) {
          currentFiles.data.splice(i, 1);
          break;
        }
      }
    })
  }

  const renderFiles = (item, index) => {
    if (subject && Number(subject.id) > 0 && Number(subject.id) !== Number(item.subjectId)) {
      return <></>
    }
    else if (Number(level) !== ALL_LEVELS && Number(level) !== Number(item.levelId)) {
      return <></>
    }
    return <Rows key={index} remove={remove} data={item} rowId={index} checkHandler={handleSelected}/>
  }

  const openFile = () => {
    inputFile.current.click();
  };

  const remove = (rowId, url) => {
    // Array.prototype.filter returns new array
    // so we aren't mutating state here
    const arrayCopy = currentFiles.data.filter((row) => row.id !== rowId);
    setCurrentFiles({ data: arrayCopy });
  }

  const handleLevelChange = (event) => {
    setLevel(event.target.value)
  }

  const handleSubjectChange = (event) => {
    setCurrentLevels([initialLevel]);
    setSubject(currentSubjects.filter((item) => Number(item.id) === Number(event.target.value))[0])
  }

  const addAllLevels = (all, levels) => {
    if (all.length === 0) {
      levels.forEach((level) => all.push(level));
    }
    else {levels.forEach((level) => {
        if (all.filter(item => item === level).length === 0) {
          all.push(level)
        }
      })
    }
  }

  useEffect(async () => {
    const files = await axios.get("/subject-files/145");
    setCurrentFiles({
      data: files.data.map((item) => {
        return { 
          first: item.name, 
          second: item.subject.name,
          third: i18next.t('subjects.levels.' + item.level),
          url: item.fileId,
          subjectId: item.subject.subjectId,
          levelId: item.level,
        };
      }),
    });
    const subjects = await axios.get("/teachers/subjects/levels/145");
    var allLevels = [];
    subjects.data.map((item) => {
      addAllLevels(allLevels, item.levels);
      setCurrentSubjects(previous => [...previous, {
        name: item.subject.name,
        id: item.subject.subjectId,
        levels: item.levels
      }]);
    });
    var initialState = {
      id: 0,
      name: i18next.t('subjects.all'),
      levels: allLevels
    };
    setCurrentSubjects(previous => [initialState, ...previous]);
    setSubject(initialState);
  }, []);

  useEffect(() => {
    console.log(subject)
    subject && subject.levels.map((item) => {
      setCurrentLevels(previous => [...previous, {
        name: i18next.t('subjects.levels.' + item),
        id: item
      }])
    });
  }, [subject]);

  useEffect(() => {
    console.log(selected)
  }, [selected])

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
                options={currentSubjects}
                handler={handleSubjectChange}
              />
            </FilterContainer>
            <FilterContainer>
              <p>Level:</p>
              <SelectDropdown
                options={currentLevels}
                handler={handleLevelChange}
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
              {currentFiles && currentFiles.data.map((item, index) => renderFiles(item, index))}
            </tbody>
          </Table>
          <Button callback={handleShow} text="Agregar Archivos" fontSize="1rem"/>

          {/* MODAL */}
          <Modal show={show} onHide={handleShow} size="xl">
            <Modal.Header closeButton>
              <Title>Agregar Archivo</Title>
            </Modal.Header>
            <ModalBody>
              <input type="file" id="file" ref={inputFile} style={{ display: "none" }} onChange={displayNewFiles}/>
              <h3>Elija en que clases quiere disponiblizar el archivo</h3>
              <SelectContainer>
                <FilterContainer>
                  <p>Subject:</p>
                  <SelectDropdown
                    options={currentSubjects}
                    handler={setSubject}
                  />
                </FilterContainer>
                <FilterContainer>
                  <p>Level:</p>
                  <SelectDropdown
                    options={currentLevels}
                    handler={setLevel}
                  />
                </FilterContainer>
              </SelectContainer>
              <table style={{ width: '90%' }}>
                <Files>
                  {newFiles && newFiles.map((item, index) => {
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
