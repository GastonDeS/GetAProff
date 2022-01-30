import React, { useState } from "react";

import styled from "styled-components";
import Default from "../../assets/img/add_img.png";

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

  label {
    width: fit-content;
    border-radius: 2rem;
    color: white;
    height: fit-content;
    border: transparent;
    padding: 0.45em 1.3em;
    background-color: var(--secondary);

    &:hover {
      color: black;
      cursor: pointer;
    }

    @media screen and (max-width: 1200px) {
      font-size: 1.75vw !important;
    }

    @media screen and (max-height: 400px) {
      font-size: 4vh !important;
    }
  }
`;

const DisplayImage = () => {
  const [image, setImage] = useState(Default);

  const onImageChange = (event) => {
    if (event.target.files && event.target.files[0]) {
      setImage(URL.createObjectURL(event.target.files[0]));
    }
  };

  return (
    <Wrapper>
      <img src={image} />
      <label>
        Choose photo
        <input
          type="file"
          name="userImage"
          onChange={onImageChange}
          accept="image/*"
        />
      </label>
    </Wrapper>
  );
};

export default DisplayImage;
