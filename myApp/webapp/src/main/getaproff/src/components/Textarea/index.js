import React from 'react'
import PropTypes from 'prop-types'

import styled from "styled-components"

const Textarea = ({ placeholder }) => {
  return <StyledTextarea placeholder={placeholder} />;
}

const StyledTextarea = styled.textarea`
  background: hsla(185, 96%, 22%, 0.35);
  border-radius: 2rem;
  width: 100%;
  height: 16vh;
  padding: 1rem;
  border: none;
  outline: none;
  color: white;
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
    color: white;
    font-weight: 100;
    font-size: var(--fontSmall);
    text-transform: uppercase;
  }
`;

Textarea.propTypes = {
  placeholder: PropTypes.string
}

export default Textarea
