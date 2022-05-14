import React, { useEffect, useRef, useState } from "react";
import AuthService from "../../services/authService";
import axios from "axios";
import { userService } from "../../services";

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
  const [reload, setReload] = useState(true);
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

  const handleDelete = async () => {
    const files = certifications.filter((file) => file.selected);
    for (var i = 0; i < files.length; i++) {
      await userService.removeCertification(files[i].id);
    }
    setCertifications([]);
    setReload(true);
    // certifications.filter((file) => file.selected).forEach((file) => {
    //   axios.delete('/user-files/' + file.id);
    // });
    // setCertifications(certifications.filter(file => !file.selected));
  };

  const handleUpload = async (event) => {
    const files = event.target.files;
    for (var i = 0; i < files.length; i++) {
      if (certifications.filter(item => item.name === files[i].name).length === 0) {
        const form = new FormData();
        form.append("file", files[i]);
        await userService.addCertification(currentUser.id, form);
      };
    };
    setCertifications([]);
    setReload(true);
  };

  const handleCheckedFile = (checked, data) => {
    setCertifications(
      certifications.map((file) => {
        if (Number(file.id) === data.id) file.selected = checked;
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
        <Button text="Save changes" fontSize="1rem" callback={() => navigate('/users/' + currentUser.id)}/>
      </ButtonContainer>
    );
  };

  const fetchCertifications = async () => {
    await userService.getUserCertifications(currentUser.id).then(res => {
      res.forEach((file) => {
        setCertifications((previous) => [
          ...previous,
          {
            ...file,
            selected: false,
          },
        ]);
      });
    })
    setReload(false);
  }

  useEffect(() => {
    setCurrentUser(AuthService.getCurrentUser());
  }, []);

  useEffect(() => {
    if (reload && currentUser) {
      fetchCertifications();
    }
  }, [reload, currentUser]);

  // useEffect(async () => {
  //   if (currentUser) {
  //     fetchCertifications();
  //   }
  // }, [currentUser]);

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
