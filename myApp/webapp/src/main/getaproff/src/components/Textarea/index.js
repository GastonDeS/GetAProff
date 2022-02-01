import React from 'react'
import PropTypes from 'prop-types'

import styled from "styled-components"

const Textarea = ({ placeholder }) => {
  return <StyledTextarea placeholder={placeholder} />;
}

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
  placeholder: PropTypes.string
}

export default Textarea
