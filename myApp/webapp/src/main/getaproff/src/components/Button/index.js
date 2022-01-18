import React from 'react'
import PropTypes from 'prop-types'

import styled from 'styled-components'

const Wrapper = styled.button`
  width: 7.5rem;
  border-radius: 2rem;
  color: white;
  height: fit-content;
  font-size: var(--fontMed);
  border: transparent;
  padding: 0.25em 1em;

  &:hover {
    color: black;
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

