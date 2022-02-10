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
  ALL_SUBJECTS,
} from "../../hooks/useMyFilesFetch";

const MyFiles = () => {
  const inputFile = useRef(null);
  const {
    subject,
    level,
    allFiles,
    currentSubjects,
    currentLevels,
    filteredFiles,
    show,
    selected,
    newFiles,
    setShow,
    setNewFiles,
    setAllFiles,
    setSelected,
    setDeleted,
    setLevel,
    setSubject,
  } = useMyFilesFetch();

  const openFileUpload = () => {
    inputFile.current.click();
  };

  const handleUpload = () => {
    newFiles.forEach(async (item) => {
      const form = new FormData();
      form.append("file", item.file);
      await axios.post('subject-files/145/' + subject.id + '/' + level, form)
        .then(res => {
          setAllFiles([...allFiles, {
            first: res.data.name,
            second: res.data.subject.name,
            third: i18next.t("subjects.levels." + res.data.level),
            subjectId: res.data.subject.subjectId,
            levelId: res.data.level,
            id: res.data.fileId
          }])
        })
        .catch(error => {});
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

  const handleCheckedFile = (checked, file) => {
    if (checked) {
      setSelected((previous) => [...previous, file]);
    } else {
      setSelected(selected.filter((id) => id !== Number(file)));
    }
  };

  const handleDelete = () => {
    setDeleted([]);
    selected.forEach((id) => {
      axios
        .delete("/subject-files/" + id)
        .then(() => {
          setDeleted((previous) => [...previous, id]);
        })
        .catch((error) => {});
    });
    setSelected([]);
  };

  // const handleDeleteAll = () => {
  //   selected.map((item) => {
  //     for (var i = 0; i < allFiles.data.length; i++) {
  //       if (item === allFiles.data[i]) {
  //         allFiles.data.splice(i, 1);
  //         break;
  //       }
  //     }
  //   })
  // }

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
                  <CheckBox />
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
            {selected.length === 0 ? (
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
                style={{ display: "none" }}
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
