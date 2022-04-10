import React, { useState, useEffect } from "react";
import axios from "axios";
import AuthService from "../../services/authService";

import { Wrapper, MainContainer, Title, Request } from "../../GlobalStyle";
import { useForm } from "react-hook-form";
import Navbar from "../../components/Navbar";
import {Content, Form, InputContainer} from "./EditProfile.styles";
import Input, {StyledInput} from "../../components/Input";
import DisplayImage from "../../components/DisplayImage";
import Textarea from "../../components/Textarea";
import Button from "../../components/Button";
import Default from "../../assets/img/add_img.png";
import {Error} from "../Login/Login.styles";

const EditProfile = () => {
  const [isTeacher, setIsTeacher] = useState(true);
  const [change, setChange] = useState(false);
  const [image, setImage] = useState(Default);
  const [currentUser, setCurrentUser] = useState();
  const {register, formState: { errors }, handleSubmit, reset} = useForm({defaultValues : { nameInput: "", schedule: "", description: ""}});

  const handleRoleChange = () => {
    setChange(current => !current);
    setIsTeacher(current => !current);
  }

  useEffect(async () => {
    setCurrentUser(AuthService.getCurrentUser());
  }, []);


  useEffect(() => {
    if (currentUser) {
      let url = 'teachers';
      if (!currentUser.teacher) {
        url = 'students';
        setIsTeacher(false);
      }
      axios.get('/' + url + '/' + currentUser.id).then(res => {
        reset({
          nameInput: res.data.name
        })
        if (currentUser.teacher) {
          reset({
            schedule: res.data.schedule,
            description: res.data.description
          })
        }
      });
      axios.get('images/' + currentUser.id)
        .then(res => {
          setImage('data:image/png;base64,' + res.data.image);
        })
        .catch(error => {});
    }
  }, [currentUser]);

  const onS = (data) => console.log(data);

  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <Content>
          <Title>Edit profile</Title>
            <Form onSubmit={handleSubmit(onS)}>
              <DisplayImage image={image} setImage={setImage}/>
              <Input register={register} name = "nameInput" options={{required : {value: true, message: "This field is required"}}}
              />
              {errors.nameInput && <Error>{errors.nameInput.message}</Error>}
              {isTeacher &&
                <>
                  <Textarea name = "description" register = {register} options = {{required: {value: true, message: "This field is required"}}} placeholder="Description"
                  />
                  {errors.description && <Error>{errors.description.message}</Error>}
                  <Textarea name= "schedule" register = {register} options = {{required: {value: true, message: "This field is required"}}} placeholder="Schedule"
                  />
                  {errors.schedule && <Error>{errors.schedule.message}</Error>}
                </>
              }
              <Button type="submit" text="Save changes" />
            </Form>
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
