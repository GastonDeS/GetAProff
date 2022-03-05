import React, { useEffect, useRef, useState } from "react";
import PropTypes from "prop-types";
import AuthService from "../../services/authService";
import axios from "axios";

import {
  Wrapper,
  MainContainer,
  Title,
  Row,
  Headers,
  Table,
} from "../../GlobalStyle";
import Navbar from "../../components/Navbar";
import { ButtonContainer, Content, DeleteButton, Files } from "./EditCertifications.styles";
import Rows from "../../components/Rows";
import Button from "../../components/Button";
import CheckBox from "../../components/CheckBox";
import { useNavigate } from "react-router-dom";

const EditCertifications = () => {
  const [currentUser, setCurrentUser] = useState();
  const [certifications, setCertifications] = useState([]);
  const [checkAll, setCheckAll] = useState(false);
  const inputFile = useRef(null);

  const navigate = useNavigate();

  const openFile = () => {
    inputFile.current.click();
  };

  const handleDeleteAll = (event) => {
    setCheckAll(event.target.checked);
    if (event.target.checked) {
      setCertifications(certifications.map(file => {
        return {
          ...file,
          selected: true
        };
      }));
    };
  };

  const handleDelete = () => {
    certifications.filter((file) => file.selected).forEach((file) => {
      axios.delete('/user-files/' + file.id);
    });
    setCertifications(certifications.filter(file => !file.selected));
  };

  const handleUpload = async (event) => {
    const files = event.target.files;
    for (var i = 0; i < files.length; i++) {
      if (certifications.filter(item => item.name === files[i].name).length === 0) {
        const form = new FormData();
        form.append("file", files[i]);
        const res = await axios.post('/user-files/' + currentUser.id, form);
        setCertifications(previous => [...previous, {
          ...res.data,
          selected: false
        }]);
      };
    };
  };

  const handleCheckedFile = (checked, id) => {
    setCertifications(
      certifications.map((file) => {
        if (Number(file.id) === id) file.selected = checked;
        return file;
      })
    );
  };

  const displayDeleteButton = () => {
    return (
      <DeleteButton>
        {displayButtons()}
        <Button text="Delete" fontSize="1rem" color="red" callback={handleDelete}/>
      </DeleteButton>
    )
  };

  const displayButtons = () => {
    return (
      <ButtonContainer>
        <label>
          <Button text="Upload file" fontSize="1rem" callback={openFile} />
          <input
            type="file"
            id="certification"
            ref={inputFile}
            accept="application/pdf"
            onChange={handleUpload}
            multiple
          />
        </label>
        <Button text="Save changes" fontSize="1rem" callback={() => navigate('/profile/' + currentUser.id)}/>
      </ButtonContainer>
    );
  };

  useEffect(() => {
    setCurrentUser(AuthService.getCurrentUser());
  }, []);

  useEffect(async () => {
    if (currentUser) {
      const res = await axios.get("/user-files/" + currentUser.id);
      res.data.forEach((file) => {
        setCertifications((previous) => [
          ...previous,
          {
            ...file,
            selected: false,
          },
        ]);
      });
    }
  }, [currentUser]);

  useEffect(() => {
    if (certifications && certifications.length === 0) setCheckAll(false) 
  }, [certifications]);

  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <Content>
          <Title>Edit certifications</Title>
          <Table>
            <thead>
              <Row>
                <Headers style={{ width: "95%" }}>Files</Headers>
                <Headers style={{ width: "5%" }}>
                  <CheckBox checked={checkAll} handleCheck={handleDeleteAll}/>
                </Headers>
              </Row>
            </thead>
            <Files>
              {certifications &&
                certifications.map((item, index) => {
                  return (
                    <Rows
                      key={index}
                      data={item}
                      handleCheck={handleCheckedFile}
                      checked={item.selected}
                      multi={false}
                    />
                  );
                })}
            </Files>
          </Table>
          {certifications.filter(item => item.selected).length > 0 ? displayDeleteButton() : displayButtons()}
        </Content>
      </MainContainer>
    </Wrapper>
  );
};

EditCertifications.propTypes = {};

export default EditCertifications;
