import React, { useState, useEffect } from "react";
import AuthService from "../../services/authService";
import { userService } from "../../services";
import { Wrapper, MainContainer, Title, Request } from "../../GlobalStyle";
import { useForm } from "react-hook-form";
import Navbar from "../../components/Navbar";
import { Content, Form } from "./EditProfile.styles";
import Input from "../../components/Input";
import DisplayImage from "../../components/DisplayImage";
import Textarea from "../../components/Textarea";
import Button from "../../components/Button";
import Default from "../../assets/img/add_img.png";
import { Error } from "../Login/Login.styles";
import { useNavigate } from "react-router-dom";
import i18next from "i18next";
import { handleService } from "../../handlers/serviceHandler";
import { handleAuthentication } from "../../handlers/accessHandler";

const EditProfile = () => {
  const [isTeacher, setIsTeacher] = useState(true);
  const navigate = useNavigate();
  const [change, setChange] = useState(false);
  const [image, setImage] = useState();
  const [displayImage, setDisplayImage] = useState(Default);
  const [currentUser, setCurrentUser] = useState();
  const {register, formState: { errors }, handleSubmit, reset, setValue} = useForm({defaultValues : { nameInput: "", schedule: "", description: "" }});

  const handleRoleChange = () => {
    setChange(current => !current);
    setIsTeacher(current => !current);
  }

  const onImageChange = (event) => {
    if (event.target.files && event.target.files[0]) {
      setDisplayImage(URL.createObjectURL(event.target.files[0]));
      setImage(event.target.files[0])
    }
  };

  useEffect(async () => {
    handleAuthentication(navigate);
    setCurrentUser(AuthService.getCurrentUser());
  }, []);


  useEffect(async () => {
    if (currentUser) {
      if (!currentUser.teacher) {
        setIsTeacher(false);
      }
      
      const userRes = await userService.getUserInfo(currentUser.id);
      const userData = handleService(userRes, navigate);
      reset({nameInput: userData.name});
      if (currentUser.teacher) {
        reset({
          schedule: userData.schedule,
          description: userData.description
        });
      };

      const imgRes = await userService.getUserImg(currentUser.id);
      const imgData = handleService(imgRes, navigate);
      if (imgData) setDisplayImage('data:image/png;base64,' + imgData.image);
    }
  }, [currentUser]);

  const onSubmit = async (data) => {
    
    if (image) {
      var imgData = new FormData();
      imgData.append("image", image, image.name)
      const res = await userService.addUserImg(currentUser.id, imgData);
      handleService(res, navigate);
    }

    let formData = {
      "name": data.nameInput
    }

    if (currentUser.teacher || change) {
      formData = {...formData, 
        "id": currentUser.id,
        "switchRole": change ? true : false,
        "description": data.description,
        "schedule": data.schedule
      }
      const res = await userService.editProfile(currentUser.id, 'teacher', formData);
      const userData = handleService(res, navigate);
      if (change) AuthService.toTeacher(userData);
    } else {
      const res = await userService.editProfile(currentUser.id, 'student', formData);
      handleService(res, navigate);
    }
    navigate("/users/" + currentUser.id);
  }

  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <Content>
          <Title>{i18next.t('editProfile.title')}</Title>
            <Form onSubmit={handleSubmit(onSubmit)}>
              <DisplayImage register={register} name = "image" image = {displayImage} onImageChange={onImageChange}/>
              <Input register={register} name = "nameInput" options={{required : {value: true, message: i18next.t('form.requiredField')}}} placeholder={i18next.t('form.namePlaceholder')}
              />
              {errors.nameInput && <Error>{errors.nameInput.message}</Error>}
              {isTeacher &&
                <>
                  <Textarea name = "description" register = {register} options = {{required: {value: true, message: i18next.t('form.requiredField')}}} placeholder={i18next.t('form.descriptionPlaceholder')}
                  />
                  {errors.description && <Error>{errors.description.message}</Error>}
                  <Textarea name= "schedule" register = {register} options = {{required: {value: true, message: i18next.t('form.requiredField')}}} placeholder={i18next.t('form.schedulePlaceholder')}
                  />
                  {errors.schedule && <Error>{errors.schedule.message}</Error>}
                </>
              }
              <Button type="Submit" text={i18next.t('editProfile.save')}/>
            </Form>
          {!isTeacher && (<>
            <Request>
              <p>{i18next.t('editProfile.changeToTeacher')}</p>
              <button onClick={handleRoleChange} type="button">{i18next.t('editProfile.clickHere')}</button>
            </Request>
          </>)}
        </Content>
      </MainContainer>
    </Wrapper>
  );
};

EditProfile.propTypes = {};

export default EditProfile;
