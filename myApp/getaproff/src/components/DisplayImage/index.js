import React, { useRef } from "react";

import styled from "styled-components";
import Button from "../Button";
import i18next from "i18next";

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

const DisplayImage = ({ image, onImageChange, register, name }) => {

  const openFile = () => {
    document.getElementById("usrPhoto").click()
    };

  return (
    <Wrapper>
      <img src={image} id="img-con" alt="userImage" />
        <Button type="button" text={i18next.t('displayImage.choose')} fontSize="1rem" callback={openFile} />
        <input
            {...register(name)}
            id="usrPhoto"
            type="file"
            onInput={onImageChange}
            accept="image/png, image/jpeg"
        />
    </Wrapper>
  );
};

export default DisplayImage;
