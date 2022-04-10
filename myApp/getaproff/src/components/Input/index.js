import React from "react";
import PropTypes from "prop-types";

import styled from "styled-components";

const Input = ({ type, placeholder, register, required, name, onChange, value }) => {
  return (
    <>
      {/* <StyledInput type={type} placeholder={placeholder} {...register(name, required)} /> */}
      <StyledInput type={type} placeholder={placeholder} onChange={onChange} defaultValue={value}/>
    </>
  );
};

export const StyledInput = styled.input`
  background: rgba(255, 255, 255, 0.8);
  border-radius: 2rem;
  width: 100%;
  height: 35%;
  padding: 1rem;
  border: 2.5px solid var(--secondary);
  outline: none;
  color: black;
  font-size: var(--fontSmall);
  font-weight: 100;

  &:focus {
    display: inline-block;
    box-shadow: 0 0 0 0.2rem var(--secondary);
    backdrop-filter: blur(12rem);
    border-radius: 2rem;
  }

  &::placeholder {
    /* color: black; */
    font-weight: 100;
    font-size: var(--fontSmall);
    text-transform: uppercase;
  }
`;

Input.propTypes = {
  type: PropTypes.string,
  placeholder: PropTypes.string,
  name: PropTypes.string,
  register: PropTypes.func,
  onChange: PropTypes.func,
  value: PropTypes.string
};

export default Input;
