import React from 'react'
import PropTypes from 'prop-types'

import styled from 'styled-components'

const Wrapper = styled.button`
  width: fit-content;
  border-radius: 2rem;
  color: white;
  height: fit-content;
  border: transparent;
  padding: 0.45em 1.3em;

  &:hover {
    color: black;
  }

  @media screen and (max-width: 1200px) {
    font-size: 1.75vw!important;
  }

  @media screen and (max-height: 500px) {
    font-size: 2vh!important;
  }
`;

const Button = ({ text, callback, color, fontSize }) => {

  return (
    <Wrapper onClick={callback} style={{backgroundColor: color, fontSize: fontSize}}>
      {text}
    </Wrapper>
  )
}

Button.propTypes = {
  text: PropTypes.string,
  color: PropTypes.string,
  callback: PropTypes.func,
  fontSize: PropTypes.string,
  radius: PropTypes.string
};

Button.defaultProps = {
  color: '#026670',
  fontSize: '1.2rem'
};

export default Button;

