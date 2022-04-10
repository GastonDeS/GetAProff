import React from 'react'
import PropTypes from 'prop-types'

import styled from "styled-components"
import Input, {StyledInput} from "../Input";

const Textarea = ({ register, name, options, ...rest }) => {
  return (
      <StyledTextarea {...register(name, options)} {...rest}/>
  );
};

const StyledTextarea = styled.textarea`
  background: rgba(255, 255, 255, 0.8);
  border-radius: 2rem;
  width: 100%;
  height: 16vh;
  padding: 1rem;
  border: 2.5px solid var(--secondary);
  outline: none;
  color: black;
  font-size: var(--fontSmall);
  font-weight: 100;
  resize: none;

  &:focus {
    display: inline-block;
    box-shadow: 0 0 0 0.2rem var(--secondary);
    backdrop-filter: blur(12rem);
    border-radius: 2rem;
  }

  &::placeholder {
    font-weight: 100;
    font-size: var(--fontSmall);
    text-transform: uppercase;
  }
`;

Textarea.propTypes = {
  type: PropTypes.string,
  placeholder: PropTypes.string,
  name: PropTypes.string,
  register: PropTypes.func,
  onChange: PropTypes.func,
  value: PropTypes.string
};

export default Textarea
