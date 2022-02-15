import React, { useState, useEffect } from "react";
import axios from "axios";
import AuthService from "../../services/authService";

import { Wrapper, MainContainer, Title, Request } from "../../GlobalStyle";
import Navbar from "../../components/Navbar";
import { Content, InputContainer } from "./EditProfile.styles";
import Input from "../../components/Input";
import DisplayImage from "../../components/DisplayImage";
import Textarea from "../../components/Textarea";
import Button from "../../components/Button";
import Default from "../../assets/img/add_img.png";

const EditProfile = () => {
  const [isTeacher, setIsTeacher] = useState(true);
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [schedule, setSchedule] = useState('');
  const [change, setChange] = useState(false);
  const [image, setImage] = useState(Default);
  const [currentUser, setCurrentUser] = useState();

  const onChangeName = (event) => {
    setName(event.target.value);
  };

  const onChangeDescription = (event) => {
    setDescription(event.target.value);
  };

  const onChangeSchedule = (event) => {
    setSchedule(event.target.value);
  };

  const handleRoleChange = () => {
    setChange(current => !current);
    setIsTeacher(current => !current);
  }

  // const saveChanges = () => {
  //   if (change) {

  //   }
  // }

  useEffect(async () => {
    setCurrentUser(AuthService.getCurrentUser());
  }, []);

  useEffect(() => {
    if (currentUser) {
      let url = 'teachers';
      if (!currentUser.teacher) {
        url = 'students';
        setIsTeacher(false);
      };
      axios.get('/' + url + '/' + currentUser.id).then(res => {
        setName(res.data.name);
        if (currentUser.teacher) {
          setDescription(res.data.description);
          setSchedule(res.data.schedule);
        }
      });
      axios.get('images/' + currentUser.id)
        .then(res => {
          setImage('data:image/png;base64,' + res.data.image);
        })
        .catch(error => {});
    }
  }, [currentUser]);
  
  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <Content>
          <Title>Edit profile</Title>
          <InputContainer>
            <DisplayImage image={image} setImage={setImage}/>
            <Input type="text" placeholder="Name" onChange={onChangeName} value={name}/>
            {isTeacher && 
              (<>
                <Textarea placeholder="Description" value={description} onChange={onChangeDescription}/>
                <Textarea placeholder="Schedule" value={schedule} onChange={onChangeSchedule}/>
              </>)
            }
          </InputContainer>
          <Button text="Save changes"/>
          {!isTeacher && (<>
            <Request>
              <p>Want to become a teacher?</p>
              <button onClick={handleRoleChange}>Click here</button>
            </Request>
          </>)}
        </Content>
      </MainContainer>
    </Wrapper>
  );
};

EditProfile.propTypes = {};

export default EditProfile;
