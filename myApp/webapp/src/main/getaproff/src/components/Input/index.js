import React from "react";
import PropTypes from "prop-types";

import styled from "styled-components";

const Input = ({ type, placeholder, register, required, name, onChange }) => {
  return (
    <>
      {/* <StyledInput type={type} placeholder={placeholder} {...register(name, required)} /> */}
      <StyledInput type={type} placeholder={placeholder} onChange={onChange}/>
    </>
  );
};

export const StyledInput = styled.input`
  background: var(--tertiary);
  border-radius: 2rem;
  width: 100%;
  height: 35%;
  padding: 1rem;
  border: none;
  outline: none;
  color: white;
  font-size: var(--fontSmall);
  font-weight: 100;

  &:focus {
    display: inline-block;
    box-shadow: 0 0 0 0.2rem var(--secondary);
    backdrop-filter: blur(12rem);
    border-radius: 2rem;
  }

  &::placeholder {
    color: white;
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
  onChange: PropTypes.func
};

export default Input;
