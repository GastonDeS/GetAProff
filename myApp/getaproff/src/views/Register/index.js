import React, {useState} from "react";
import { useNavigate } from "react-router-dom";
import { axiosService } from "../../services";

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


const Register = () => {
  const [index, setIndex] = useState(0);
  const navigate = useNavigate();
  const [image, setImage] = useState();
  const [displayImage, setDisplayImage] = useState(Default);
  const {register, formState: {errors}, handleSubmit, getValues, setValue} = useForm( {defaultValues: {"Role" : 1, "DescriptionInput": "", "ScheduleInput": ""}});

  const EMAIL_PATTERN = /^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/;

  const onImageChange = (event) => {
    if (event.target.files && event.target.files[0]) {
      setDisplayImage(URL.createObjectURL(event.target.files[0]));
      setImage(event.target.files[0])
    }
  };

  const onSubmit = async (data) => {
      let formData = new FormData();
      formData.append("name", data.NameInput);
      formData.append("mail", data.MailInput);
      formData.append("password", data.PassInput);
      formData.append("description", data.DescriptionInput);
      formData.append("schedule", data.ScheduleInput);
      formData.append("role", data.Role);
      
      await AuthService.register(formData).catch((error) => { navigate("/error") })
      
      if (image) {
        var imgData = new FormData();
        imgData.append("image", image, image.name)
        var currentUser = AuthService.getCurrentUser();
        await axiosService.authAxiosWrapper(axiosService.POST, `/users/${currentUser.id}/image`, {}, imgData)
          .catch( err => console.log(err));
      }

      navigate("/");
  };
  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <FormContainer>
          <TabContainer>
            <WelcomeText>Welcome</WelcomeText>
            <Tab setIndex={setIndex} setValue={setValue} style={{ borderRadius: "2rem" }}>
              {/* index = 0 */}
              <TabItem style={{ borderBottomLeftRadius: "2rem" }}>
                  Teacher
              </TabItem>
              {/* index = 1 */}
              <TabItem style={{ borderBottomRightRadius: "2rem" }}>
                Student
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
                <Input register={register} name="NameInput" options={{required: {value: true, message: "This field is required"}}} type="text" placeholder="Name" />
                <Input register={register} name="MailInput"
                       options={{
                         required: {
                           value: true,
                           message: "This field is required"
                         },
                         pattern: {
                           value: EMAIL_PATTERN,
                           message: "You must enter a valid mail"
                         }
                       }} type="text" placeholder="mail@example.com"  />
                {errors.MailInput && <Error>{errors.MailInput.message}</Error>}
                <Input register={register} name="PassInput"
                       options={{
                         required: {
                           value: true,
                           message: "This field is required"
                         },
                         minLength: {
                           value: 8,
                           message: "Password must have at least 8 characters"
                         }
                       }} type="password" placeholder="Password"  />
                {errors.PassInput && <Error>{errors.PassInput.message}</Error>}
                <Input register={register} name="ConfirmPassInput"
                       options={{
                         required: {
                           value: true, message: "This field is required"
                         },
                         validate: value => value === getValues("PassInput") || "The passwords don't match"
                       }} type="password" placeholder="Confirm Password"  />
                {errors.ConfirmPassInput && <Error>{errors.ConfirmPassInput.message}</Error>}
                {index === 0 ? (
                  <>
                    <Textarea register={register} name="DescriptionInput" placeholder="Description" />
                    <Textarea register={register} name="ScheduleInput" placeholder="Schedule" />
                  </>
                ) : (
                  <></>
                )}
              </InputWrapper>
            </InputContainer>
            <ButtonContainer>
              <Button type="Submit">Sign up</Button>
            </ButtonContainer>
            <Request>
              <p>Already have an account?</p>
              <button onClick={() => { navigate('/users/login')}}>Login</button>
            </Request>
          </Form>
        </FormContainer>
      </MainContainer>
    </Wrapper>
  );
};

Register.propTypes = {};

export default Register;
