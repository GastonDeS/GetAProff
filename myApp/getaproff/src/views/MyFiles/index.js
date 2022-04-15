import React, { useRef } from "react";
import i18next from "i18next";
import axios from "axios";

import Navbar from "../../components/Navbar";
import { MainContainer, Wrapper } from "../../GlobalStyle";
import {
  Content,
  SelectContainer,
  FilterContainer,
  ModalBody,
  ModalButtonContainer,
  Files,
  ButtonContainer,
} from "./MyFiles.styles";
import Button from "../../components/Button";
import Modal from "react-bootstrap/Modal";
import SelectDropdown from "../../components/SelectDropdown";
import Rows from "../../components/Rows";
import { Title, Row, Headers, Table } from "../../GlobalStyle";
import CheckBox from "../../components/CheckBox";
import {
  useMyFilesFetch,
  ALL_LEVELS,
} from "../../hooks/useMyFilesFetch";

const MyFiles = () => {
  const inputFile = useRef(null);
  const {
    subject,
    level,
    currentSubjects,
    currentLevels,
    filteredFiles,
    show,
    newFiles,
    checkAll,
    allFiles,
    setShow,
    setNewFiles, 
    setAllFiles,
    setDeleted,
    setLevel,
    setSubject,
    setFilteredFiles,
    setCheckAll
  } = useMyFilesFetch();

  const openFileUpload = () => {
    if (inputFile && inputFile.current) inputFile.current.click();
  };

  const handleUpload = () => {
    let files = [];
    newFiles.forEach(async (item) => {
      const form = new FormData();
      form.append("file", item.file);
      await axios.post('subject-files/145/' + subject.id + '/' + level, form)
      .then(res => {
        files.push({
          first: res.data.name,
          second: res.data.subject.name,
          third: i18next.t("subjects.levels." + res.data.level),
          subjectId: res.data.subject.subjectId,
          levelId: res.data.level,
          id: res.data.id,
          selected: false
        });
      })
      .catch(error => {});
      setAllFiles(allFiles.concat(files));
    })
    handleShow();
  }

  const handleShow = () => {
    setShow((current) => !current);
  };

  const displayNewFiles = (event) => {
    const files = event.target.files;
    var uploaded = [];
    for (var i = 0; i < files.length; i++) {
      if (newFiles.filter(item => item.name === files[i].name).length === 0) {
        uploaded.push({ file: files[i], name: files[i].name });
      }
    };
    setNewFiles(newFiles.concat(uploaded));
  };

  const removeNewFile = (name) => {
    setNewFiles(newFiles.filter(item => item.name !== name));
  };

  const handleCheckedFile = (checked, id) => {
    setFilteredFiles(filteredFiles.map(file => {
      if (Number(file.id) === id ) file.selected = checked;
      return file;
    }));
  };

  const handleDelete = () => {
    setDeleted([]);
    filteredFiles.forEach((file) => {
      if (file.selected) {
        axios
        .delete("/subject-files/" + file.id)
        .then(() => {
          setDeleted((previous) => [...previous, file.id]);
        })
        .catch((error) => {});
      }
    });
  };

  const handleDeleteAll = (event) => {
    setCheckAll(event.target.checked);
    if (event.target.checked) {
      setFilteredFiles(filteredFiles.map(file => {
        return {
          ...file,
          selected: true
        }
      }));
    };
  };

  const handleLevelChange = (event) => {
    setLevel(event.target.value);
  };

  const handleSubjectChange = (event) => {
    var newSubject = currentSubjects.filter((item) => Number(item.id) === Number(event.target.value))[0];
    if (show) {
      setLevel(newSubject.levels[0]);
    } else {
      setLevel(ALL_LEVELS);
    }
    setSubject(newSubject);
  };

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
              {subject ? (
                <SelectDropdown
                  options={currentSubjects}
                  handler={handleSubjectChange}
                  value={subject.id}
                />
              ) : (
                <SelectDropdown />
              )}
            </FilterContainer>
            <FilterContainer>
              <p>Level:</p>
              <SelectDropdown
                options={currentLevels}
                handler={handleLevelChange}
                value={level}
              />
            </FilterContainer>
          </SelectContainer>
          <Table className="subjects-table">
            <thead>
              <Row>
                <Headers style={{ width: "45%" }}>
                  {i18next.t("files.file")}
                </Headers>
                <Headers style={{ width: "20%" }}>
                  {i18next.t("files.subject")}
                </Headers>
                <Headers style={{ width: "30%" }}>
                  {i18next.t("files.level")}
                </Headers>
                <Headers style={{ width: "5%" }}>
                  <CheckBox checked={checkAll} handleCheck={handleDeleteAll}/>
                </Headers>
              </Row>
            </thead>
            <tbody>
              {filteredFiles &&
                filteredFiles.map((item, index) => {
                  return (
                    <Rows
                      key={index}
                      data={item}
                      handleCheck={handleCheckedFile}
                      checked={item.selected}
                    />
                  );
                })}
            </tbody>
          </Table>
          <ButtonContainer>
            <Button
              callback={handleShow}
              text="Agregar Archivos"
              fontSize="1rem"
            />
            {filteredFiles.filter(file => file.selected).length === 0 ? (
              <></>
            ) : (
              <Button
                callback={handleDelete}
                text="Borrar Archivos"
                fontSize="1rem"
              />
            )}
          </ButtonContainer>

          {/* MODAL */}
          <Modal show={show} onHide={handleShow} size="xl">
            <Modal.Header closeButton>
              <Title>Agregar Archivo</Title>
            </Modal.Header>
            <ModalBody>
              <input
                type="file"
                id="file"
                ref={inputFile}
                hidden={true}
                onChange={displayNewFiles}
                multiple
                onClick={(event) => event.target.value = null}
              />
              <h3>Elija en que clases quiere disponiblizar el archivo</h3>
              <SelectContainer>
                <FilterContainer>
                  <p>Subject:</p>
                  {subject ? (
                    <SelectDropdown
                      options={currentSubjects}
                      handler={handleSubjectChange}
                      value={subject.id}
                    />
                  ) : (
                    <SelectDropdown />
                  )}
                </FilterContainer>
                <FilterContainer>
                  <p>Level:</p>
                  <SelectDropdown
                    options={currentLevels}
                    handler={handleLevelChange}
                    value={level}
                  />
                </FilterContainer>
              </SelectContainer>
              <table style={{ width: "90%" }}>
                <Files>
                  {newFiles &&
                    newFiles.map((item, index) => {
                      return (
                        <Rows
                          key={index}
                          data={item}
                          multi={false}
                          type="remove"
                          handleCheck={removeNewFile}
                        />
                      );
                    })}
                </Files>
              </table>
              <ModalButtonContainer>
                <Button
                  text="Elegir archivos"
                  fontSize="1rem"
                  callback={openFileUpload}
                />
                <div>
                  <Button
                    color="grey"
                    fontSize="1rem"
                    callback={handleShow}
                    text="Cancelar"
                  />
                  <Button
                    callback={handleUpload}
                    fontSize="1rem"
                    text="Subir archivos"
                  />
                </div>
              </ModalButtonContainer>
            </ModalBody>
          </Modal>
        </Content>
      </MainContainer>
    </Wrapper>
  );
};
export default MyFiles;