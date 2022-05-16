import React, {useState} from "react";
import { useNavigate } from "react-router-dom";
import { userService } from "../../services";

import Navbar from "../../components/Navbar";
import Tab from "../../components/Tab";
import TabItem from "../../components/TabItem";
import Input from "../../components/Input";
import {
  WelcomeText,
  Button,
  ButtonContainer,
  Form,
  FormContainer,
  TabContainer,
  InputContainer,
  InputWrapper,
} from "./Register.styles";
import Textarea from "../../components/Textarea";
import DisplayImage from "../../components/DisplayImage";
import { Request, MainContainer, Wrapper } from "../../GlobalStyle";
import Default from "../../assets/img/add_img.png";
import {useForm} from "react-hook-form";
import {Error} from "../Login/Login.styles";
import AuthService from "../../services/authService";
import i18next from "i18next";


const Register = () => {
  const [index, setIndex] = useState(0);
  const navigate = useNavigate();
  const [image, setImage] = useState();
  const [displayImage, setDisplayImage] = useState(Default);
  const {register, formState: {errors}, handleSubmit, getValues, setValue, clearErrors} = useForm( {defaultValues: {"Role" : 1}});

  const EMAIL_PATTERN = /^$|^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$/;

  const onTabChange = () => {
    clearErrors();
  }

  const onImageChange = (event) => {
    if (event.target.files && event.target.files[0]) {
      setDisplayImage(URL.createObjectURL(event.target.files[0]));
      setImage(event.target.files[0])
    }
  };

  const onSubmit = async (data) => {

      let formData = {
        "mail": data.MailInput,
        "name": data.NameInput,
        "password": data.PassInput,
        "role": data.Role,
      }

      if (data.Role === 1) {
        formData = {...formData, 
          "description": data.DescriptionInput,
          "schedule": data.ScheduleInput
        }
      }
      
      await userService.register(formData).catch((error) => { navigate("/error") })
      
      if (image) {
        var imgData = new FormData();
        imgData.append("image", image, image.name)
        var currentUser = AuthService.getCurrentUser();
        userService.addUserImg(currentUser.id, imgData);
      }

      navigate("/");
  };
  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <FormContainer>
          <TabContainer>
            <WelcomeText>{i18next.t('register.title')}</WelcomeText>
            <Tab setIndex={setIndex} setValue={setValue} style={{ borderRadius: "2rem" }} onChange={onTabChange}>
              {/* index = 0 */}
              <TabItem style={{ borderBottomLeftRadius: "2rem" }}>
                  {i18next.t('register.teacher')}
              </TabItem>
              {/* index = 1 */}
              <TabItem style={{ borderBottomRightRadius: "2rem" }}>
                  {i18next.t('register.student')}
              </TabItem>
            </Tab>
          </TabContainer>
          <Form onSubmit={handleSubmit(onSubmit)}>
              <input type="number"  style={{display: 'none'}} {...register("Role")}/>
              <InputContainer>
              <div style={{ width: "80%" }}>
              <DisplayImage  register={register} name="ImgInput" image={displayImage} onImageChange={onImageChange}/>
              </div>
              <InputWrapper>
                <Input register={register} name="NameInput" options={{required: {value: true, message: i18next.t('form.requiredField')}}} type="text" placeholder={i18next.t('form.namePlaceholder')} />
                {errors.NameInput && <Error>{errors.NameInput.message}</Error>}
                <Input register={register} name="MailInput"
                       options={{
                         required: {
                           value: true,
                           message: i18next.t('form.requiredField')
                         },
                         pattern: {
                           value: EMAIL_PATTERN,
                           message: i18next.t('form.invalidFormat')
                         }
                       }} type="text" placeholder={i18next.t('form.mailPlaceholder')} />
                {errors.MailInput && <Error>{errors.MailInput.message}</Error>}
                <Input register={register} name="PassInput"
                       options={{
                         required: {
                           value: true,
                           message: i18next.t('form.requiredField')
                         },
                         minLength: {
                           value: 8,
                           message: i18next.t('form.shortPassword')
                         }
                       }} type="password" placeholder={i18next.t('form.passwordPlaceholder')}  />
                {errors.PassInput && <Error>{errors.PassInput.message}</Error>}
                <Input register={register} name="ConfirmPassInput"
                       options={{
                         required: {
                           value: true, message: i18next.t('form.requiredField')
                         },
                         validate: value => value === getValues("PassInput") || i18next.t('form.notMatchingPasswords')
                       }} type="password" placeholder={i18next.t('form.confirmPasswordPlaceholder')}  />
                {errors.ConfirmPassInput && <Error>{errors.ConfirmPassInput.message}</Error>}
                {index === 0 ? (
                  <>
                    <Textarea register={register} options={{
                         required: {
                           value: true,
                           message: i18next.t('form.requiredField')
                         }
                       }} name="DescriptionInput" placeholder={i18next.t('form.descriptionPlaceholder')} />
                    {errors.DescriptionInput && <Error>{errors.DescriptionInput.message}</Error>}
                    <Textarea register={register} options={{
                         required: {
                           value: true,
                           message: i18next.t('form.requiredField')
                         }
                       }} name="ScheduleInput" placeholder={i18next.t('form.schedulePlaceholder')} />
                    {errors.ScheduleInput && <Error>{errors.ScheduleInput.message}</Error>}
                  </>
                ) : (
                  <></>
                )}
              </InputWrapper>
            </InputContainer>
            <ButtonContainer>
              <Button type="Submit">{i18next.t('register.signUp')}</Button>
            </ButtonContainer>
            <Request>
              <p>{i18next.t('register.alreadyRegistered')}</p>
              <button onClick={() => { navigate('/users/login')}}>{i18next.t('register.login')}</button>
            </Request>
          </Form>
        </FormContainer>
      </MainContainer>
    </Wrapper>
  );
};

Register.propTypes = {};

export default Register;
