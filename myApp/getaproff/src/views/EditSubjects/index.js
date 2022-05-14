import React from "react";
import i18next from "i18next";
import { useNavigate } from "react-router-dom";

import {
  MainContainer,
  Content,
  SelectContainer,
  SingleSelect,
  ErrorMsg,
  ButtonContainer
} from "./EditSubjects.styles";
import Navbar from "../../components/Navbar";
import Rows from '../../components/Rows';
import SelectDropdown from "../../components/SelectDropdown";
import Button from "../../components/Button";
import { Request, Wrapper, Title, Levels, Row, Headers, Table } from "../../GlobalStyle";
import CheckBox from "../../components/CheckBox";
import { useEditSubjectsFetch } from "../../hooks/useEditSubjectsFetch";

const EditSubjects = () => {

  const {
    error,
    loading,
    availableSubjects,
    price,
    subjectsTaught,
    level,
    subject,
    checkAll,
    currentUser,
    handleCheckAll,
    handleSubjectChange,
    onChangePrice,
    handleCheckedsubject,
    handleAddSubject,
    handleDeleteSubjects,
    handleLevelChange
  } = useEditSubjectsFetch();

  const navigate = useNavigate();

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
          <ButtonContainer>
            <Button
              text={i18next.t('editSubjects.back')}
              fontSize="1rem"
              callback={() => {navigate('/users/' + currentUser.id)}}
            />
            {subjectsTaught.filter(subject => subject.checked).length === 0 ? (
                <></>
              ) : (
                <Button
                  callback={handleDeleteSubjects}
                  text={i18next.t('editSubjects.deleteSubject')}
                  fontSize="1rem"
                  color="red"
                />
            )}
          </ButtonContainer>
        </Content>
      </MainContainer>
    </Wrapper>
  );
};

EditSubjects.propTypes = {};

export default EditSubjects;
