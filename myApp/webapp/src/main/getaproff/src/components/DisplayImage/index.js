import React, { useState, useRef } from "react";

import styled from "styled-components";
import Default from "../../assets/img/add_img.png";
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

const DisplayImage = () => {
  const [image, setImage] = useState(Default);
  const inputFile = useRef(null);

  const onImageChange = (event) => {
    if (event.target.files && event.target.files[0]) {
      setImage(URL.createObjectURL(event.target.files[0]));
    }
  };

  const openFile = () => {
    inputFile.current.click();
  };

  return (
    <Wrapper>
      <img src={image} alt="userImage"/>
      <label>
        <Button text="Choose photo" fontSize="1rem" callback={openFile}/>
        <input
          type="file"
          name="userImage"
          onChange={onImageChange}
          accept="image/*"
          ref={inputFile}
        />
      </label>
    </Wrapper>
  );
};

export default DisplayImage;
