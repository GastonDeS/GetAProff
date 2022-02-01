import React, { useState } from "react";
import PropTypes from "prop-types";

import { Wrapper, MainContainer, Title, Request } from "../../GlobalStyle";
import Navbar from "../../components/Navbar";
import { Content, InputContainer } from "./EditProfile.styles";
import Input from "../../components/Input";
import DisplayImage from "../../components/DisplayImage";
import Textarea from "../../components/Textarea";
import Button from "../../components/Button";

const EditProfile = () => {
  const [isTeacher, setIsTeacher] = useState(false);
  const [name, setName] = useState("Gaston");
  const [description, setDescription] = useState("Hola soy Gaston");
  const [schedule, setSchedule] = useState("No tengo horarios disponibles");
  const [change, setChange] = useState(false);

  const onChangeName = (event) => {
    setName(event.target.value);
  };

  const handleRoleChange = () => {
    setChange(current => !current);
    setIsTeacher(current => !current);
  }

  return (
    <Wrapper>
      <Navbar empty={true} />
      <MainContainer>
        <Content>
          <Title>Edit profile</Title>
          {/* <div style={{ width: "80%" }}>
            <DisplayImage />
          </div> */}
          <InputContainer>
            <DisplayImage />
            <Input type="text" placeholder="Name" onChange={onChangeName} value={name}/>
            {isTeacher && 
              (<>
                <Textarea placeholder="Description" value={description}/>
                <Textarea placeholder="Schedule" value={schedule}/>
              </>)
            }
          </InputContainer>
          <Button text="Save changes"/>
          {!isTeacher && (<>
            <Request>
              <p>Want to become a teacher?</p>
              <a href="#" onClick={handleRoleChange}>Click here</a>
            </Request>
          </>)}
        </Content>
      </MainContainer>
    </Wrapper>
  );
};

EditProfile.propTypes = {};

export default EditProfile;
