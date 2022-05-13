import React, { useState, useEffect } from "react";
import axios from "axios";
import AuthService from "../../services/authService";
import {axiosService} from "../../services";
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
import {useNavigate} from "react-router-dom";
import { act } from "react-dom/test-utils";

const EditProfile = () => {
  const [isTeacher, setIsTeacher] = useState(true);
  const navigate = useNavigate();
  const [change, setChange] = useState(false);
  const [image, setImage] = useState(Default);
  const [isNewImage, setIsNewImage] = useState(false);
  const [currentUser, setCurrentUser] = useState();
  const {register, formState: { errors }, handleSubmit, reset, setValue} = useForm({defaultValues : { nameInput: "", schedule: "", description: ""}});

  const handleRoleChange = () => {
    setChange(current => !current);
    setIsTeacher(current => !current);
  }

  useEffect(() => {
    console.log(image === Default);
  }, [isNewImage])

  useEffect(async () => {
    setCurrentUser(AuthService.getCurrentUser());
  }, []);


  useEffect(() => {
    if (currentUser) {
      let getUserUrl = '/users';
      if (!currentUser.teacher) {
        setIsTeacher(false);
      }
      axios.get(getUserUrl + '/' + currentUser.id).then(res => {
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
      //TODO: Revisar este endpoint
      axios.get( `/users/${currentUser.id}/image`)
        .then(res => {
          setImage('data:image/png;base64,' + res.data.image);
        })
        .catch(error => {});
    }
  }, [currentUser]);

  const onSubmit = async (data) => {
    var formData = new FormData();
    formData.append("name", data.nameInput);
    formData.append("description", data.description);
    formData.append("schedule", data.schedule);
    formData.append("teach", change ? "true" : "false");

    if (image !== Default) {
      var imgData = new FormData().append("image", image);
      await axiosService.axiosWrapper(axiosService.POST, `/users/${currentUser.id}/image`, imgData)
        .catch( err => console.log(err));
    }
    //TODO: service
    await axiosService.axiosWrapper(axiosService.POST, `/users/${currentUser.id}`, formData )
      .then( () => {
        navigate("/users/"+ currentUser.id);
      }).catch( err => console.log(err));

  }

  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <Content>
          <Title>Edit profile</Title>
            <Form onSubmit={handleSubmit(onSubmit)}>
              <DisplayImage register={register} name = "image" image = {image} setImage={setImage}/>
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
              <Button type="Submit" text="Save changes"/>
            </Form>
          {!isTeacher && (<>
            <Request>
              <p>Want to become a teacher?</p>
              <button onClick={handleRoleChange} type="button">Click here</button>
            </Request>
          </>)}
        </Content>
      </MainContainer>
    </Wrapper>
  );
};

EditProfile.propTypes = {};

export default EditProfile;
