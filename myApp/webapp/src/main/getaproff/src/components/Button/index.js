import React from 'react'
import PropTypes from 'prop-types'

import styled from 'styled-components'

const Wrapper = styled.button`
  width: fit-content;
  border-radius: 2rem;
  color: white;
  height: fit-content;
  font-size: var(--fontMed);
  border: transparent;
  padding: 0.45em 1.3em;

  &:hover {
    color: black;
  }

  @media screen and (max-width: 1200px) {
    font-size: 1.25vw;
  }
`;

const Button = ({ text, callback, color, type }) => {
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
  type: PropTypes.string,
};

Button.defaultProps = {
  color: '#026670'
};

export default Button;

