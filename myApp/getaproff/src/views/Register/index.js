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

  const EMAIL_PATTERN = /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/;

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
                <Input register={register} name="NameInput" options={{required: {value: true, message: i18next.t('register.requiredField')}}} type="text" placeholder={i18next.t('register.namePlaceholder')} />
                <Input register={register} name="MailInput"
                       options={{
                         required: {
                           value: true,
                           message: i18next.t('register.requiredField')
                         },
                         pattern: {
                           value: EMAIL_PATTERN,
                           message: i18next.t('register.invalidFormat')
                         }
                       }} type="text" placeholder={i18next.t('register.mailPlaceholder')} />
                {errors.MailInput && <Error>{errors.MailInput.message}</Error>}
                <Input register={register} name="PassInput"
                       options={{
                         required: {
                           value: true,
                           message: i18next.t('register.requiredField')
                         },
                         minLength: {
                           value: 8,
                           message: i18next.t('register.shortPassword')
                         }
                       }} type="password" placeholder={i18next.t('register.password')}  />
                {errors.PassInput && <Error>{errors.PassInput.message}</Error>}
                <Input register={register} name="ConfirmPassInput"
                       options={{
                         required: {
                           value: true, message: i18next.t('register.requiredField')
                         },
                         validate: value => value === getValues("PassInput") || "The passwords don't match"
                       }} type="password" placeholder={i18next.t('register.confirmPasswordPlaceholder')}  />
                {errors.ConfirmPassInput && <Error>{errors.ConfirmPassInput.message}</Error>}
                {index === 0 ? (
                  <>
                    <Textarea register={register} options={{
                         required: {
                           value: true,
                           message: i18next.t('register.requiredField')
                         }
                       }} name="DescriptionInput" placeholder={i18next.t('register.descriptionPlaceholder')} />
                    {errors.DescriptionInput && <Error>{errors.DescriptionInput.message}</Error>}
                    <Textarea register={register} options={{
                         required: {
                           value: true,
                           message: i18next.t('register.requiredField')
                         }
                       }} name="ScheduleInput" placeholder={i18next.t('register.schedulePlaceholder')} />
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
