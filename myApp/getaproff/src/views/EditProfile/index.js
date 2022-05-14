import React, { useState, useEffect } from "react";
import axios from "axios";
import AuthService from "../../services/authService";
import {axiosService} from "../../services";
import { Wrapper, MainContainer, Title, Request } from "../../GlobalStyle";
import { useForm } from "react-hook-form";
import Navbar from "../../components/Navbar";
import {Content, Form} from "./EditProfile.styles";
import Input from "../../components/Input";
import DisplayImage from "../../components/DisplayImage";
import Textarea from "../../components/Textarea";
import Button from "../../components/Button";
import Default from "../../assets/img/add_img.png";
import {Error} from "../Login/Login.styles";
import {useNavigate} from "react-router-dom";
import i18next from "i18next";

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
          if (res.data.image) {
            setDisplayImage('data:image/png;base64,' + res.data.image);
          }
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
    
    if (image) {
      var imgData = new FormData();
      imgData.append("image", image, image.name)
      await axiosService.authAxiosWrapper(axiosService.POST, `/users/${currentUser.id}/image`, {}, imgData)
        .catch( err => console.log(err));
    }
  
    await axiosService.authAxiosWrapper(axiosService.POST, `/users/${currentUser.id}`, {}, formData)
      .then((res) => {
        if (change) {
          AuthService.toTeacher(res.data);
        }
        navigate("/users/" + currentUser.id);
      }).catch( err => console.log(err));

  }

  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <Content>
          <Title>{i18next.t('editProfile.title')}</Title>
            <Form onSubmit={handleSubmit(onSubmit)}>
              <DisplayImage register={register} name = "image" image = {displayImage} onImageChange={onImageChange}/>
              <Input register={register} name = "nameInput" options={{required : {value: true, message: i18next.t('editProfile.required')}}}
              />
              {errors.nameInput && <Error>{errors.nameInput.message}</Error>}
              {isTeacher &&
                <>
                  <Textarea name = "description" register = {register} options = {{required: {value: true, message: i18next.t('editProfile.required')}}} placeholder={i18next.t('editProfile.descriptionPlaceholder')}
                  />
                  {errors.description && <Error>{errors.description.message}</Error>}
                  <Textarea name= "schedule" register = {register} options = {{required: {value: true, message: i18next.t('editProfile.required')}}} placeholder={i18next.t('editProfile.schedulePlaceholder')}
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
