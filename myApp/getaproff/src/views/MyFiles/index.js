import React, { useRef } from "react";
import i18next from "i18next";
import { useNavigate } from "react-router-dom";
import { handleService } from "../../handlers/serviceHandler";
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
import { filesService } from "../../services";

const MyFiles = () => {
  const inputFile = useRef(null);
  const navigate = useNavigate();
  const {
    subject,
    level,
    currentSubjects,
    currentLevels,
    filteredFiles,
    show,
    newFiles,
    checkAll,
    currentUser,
    setReload,
    setShow,
    setNewFiles, 
    setAllFiles,
    setLevel,
    setSubject,
    setFilteredFiles,
    setCheckAll
  } = useMyFilesFetch();

  const openFileUpload = () => {
    if (inputFile && inputFile.current) inputFile.current.click();
  };

  const handleUpload = async () => {
    for (var i = 0; i < newFiles.length; i++) {
      const form = new FormData();
      form.append("file", newFiles[i].file);
      const res = await filesService.addSubjectFiles(currentUser.id, level, subject.id, form);
      handleService(res, navigate);
    }
    setAllFiles([]);
    setNewFiles([]);
    setReload(true);
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

  const removeNewFile = (file) => {
    setNewFiles(newFiles.filter(item => item.name !== file.name));
  };

  const handleCheckedFile = (checked, checkedFile) => {
    setFilteredFiles(filteredFiles.map(file => {
      if (Number(file.id) === Number(checkedFile.id)) file.selected = checked;
      return file;
    }));
  };

  const handleDelete = async () => {
    for (var i = 0; i < filteredFiles.length; i++) {
      if (filteredFiles[i].selected) {
        const res = await filesService.removeSubjectFiles(filteredFiles[i].id);
        handleService(res, navigate);
      }
    }
    setAllFiles([]);
    setReload(true);
  };

  const handleCheckAll = (event) => {
    setCheckAll(event.target.checked);
    if (event.target.checked) {
      setFilteredFiles(filteredFiles.map(file => {
        return {
          ...file,
          selected: true
        }
      }));
    } else {
      setFilteredFiles(filteredFiles.map(file => {
        return {
          ...file,
          selected: false
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
          <Title>{i18next.t('myFiles.title')}</Title>
          <h5>{i18next.t('myFiles.filterBy')}</h5>
          <SelectContainer>
            <FilterContainer>
              <p>{i18next.t('myFiles.subject')}:</p>
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
              <p>{i18next.t('myFiles.level')}:</p>
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
                  {i18next.t("myFiles.file")}
                </Headers>
                <Headers style={{ width: "20%" }}>
                  {i18next.t("myFiles.subject")}
                </Headers>
                <Headers style={{ width: "30%" }}>
                  {i18next.t("myFiles.level")}
                </Headers>
                <Headers style={{ width: "5%" }}>
                  <CheckBox checked={checkAll} handleCheck={handleCheckAll}/>
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
              text= {i18next.t('myFiles.add')}
              fontSize="1rem"
            />
            {filteredFiles.filter(file => file.selected).length === 0 ? (
              <></>
            ) : (
              <Button
                callback={handleDelete}
                text={i18next.t('myFiles.remove')}
                fontSize="1rem"
              />
            )}
          </ButtonContainer>

          {/* MODAL */}
          <Modal show={show} onHide={handleShow} size="xl">
            <Modal.Header closeButton>
              <Title>{i18next.t('myFiles.new.title')}</Title>
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
              <h3>{i18next.t('myFiles.new.chooseSubjects')}</h3>
              <SelectContainer>
                <FilterContainer>
                  <p>{i18next.t('myFiles.subject')}:</p>
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
                  <p>{i18next.t('myFiles.level')}:</p>
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
                  text={i18next.t('myFiles.new.chooseFiles')}
                  fontSize="1rem"
                  callback={openFileUpload}
                />
                <div>
                  <Button
                    color="grey"
                    fontSize="1rem"
                    callback={handleShow}
                    text={i18next.t('myFiles.new.cancel')}
                  />
                  <Button
                    callback={handleUpload}
                    fontSize="1rem"
                    text={i18next.t('myFiles.new.upload')}
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
