import React from 'react'
import PropTypes from 'prop-types'

import styled from 'styled-components'

const Button = ({ text, callback, color, fontSize }) => {
  const Wrapper = styled.button`
    width: fit-content;
    border-radius: 2rem;
    color: white;
    height: fit-content;
    font-size: ${fontSize};
    border: transparent;
    padding: 0.45em 1.3em;

    &:hover {
      color: black;
    }

    @media screen and (max-width: 1200px) {
      font-size: 1.75vw;
    }
  `;

  return (
    <Wrapper onClick={callback} style={{backgroundColor: color}}>
      {text}
    </Wrapper>
  )
}

Button.propTypes = {
  text: PropTypes.string,
  color: PropTypes.string,
  callback: PropTypes.func,
  fontSize: PropTypes.string,
};

Button.defaultProps = {
  color: '#026670',
  fontSize: '1.2rem'
};

export default Button;

