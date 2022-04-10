import React, { useRef } from "react";

import styled from "styled-components";
import Button from "../Button";

const Wrapper = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: fit-content;
  margin-bottom: 8px;

  img {
    width: 175px;
    height: 175px;
    object-fit: cover;
    margin: 15px;
    border-radius: 5px;
  }

  input {
    display: none;
  }
`;

const DisplayImage = ({ image, setImage, register, name }) => {


  const onImageChange = (event) => {
    if (event.target.files && event.target.files[0]) {
      setImage(URL.createObjectURL(event.target.files[0]));
    }
  };



  const openFile = () => {
    document.getElementById("usrPhoto").click()
    };

  return (
    <Wrapper>
      <img src={image} id="img-con" alt="userImage" />
        <Button type="button" text="Choose photo" fontSize="1rem" callback={openFile} />
        <input
            id="usrPhoto"
            type="file"
            {...register(name)}
            onInput={onImageChange}
            accept="image/png, image/jpeg"
        />
    </Wrapper>
  );
};

export default DisplayImage;
