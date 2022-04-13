import React, { useState } from "react";
import PropTypes from "prop-types";
import i18next from "i18next";
import { useNavigate } from 'react-router-dom';

import {
  DropdownItem,
  DropdownMenu,
  DropdownDivider,
} from "styled-dropdown-component";
import styled from "styled-components";

const DropdownContainer = styled.div`
  position: relative;
  display: inline-block;
  cursor: pointer;

  button,
  input[type="submit"],
  input[type="reset"] {
    background: none;
    color: inherit;
    border: none;
    padding: 0;
    font: inherit;
    cursor: pointer;
    outline: inherit;
  }

  button {
    font-size: ${(props) => props.size};
    font-weight: ${(props) => props.weight};
    background: ${(props) => props.background};
    color: ${(props) => props.color};
    padding: ${(props) => props.padding};
    border: none;
    border-radius: ${(props) => props.radius};

    &:hover {
      color: black;
    }

    @media screen and (max-width: 1200px) {
      font-size: 2.4vw !important;
    }

    @media screen and (max-height: 500px) {
      font-size: 4.5vh !important;
    }
  }
`;

const DropDown = ({ brand, options, endOption, background, radius, padding, size, color, weight }) => {
  const [hidden, setHidden] = useState(true);

  const navigate = useNavigate();

  return (
    <DropdownContainer background={background} radius={radius} padding={padding} size={size} color={color} weight={weight}>
      <button onClick={() => setHidden(!hidden)}>{brand}</button>
      <DropdownMenu hidden={hidden} toggle={() => setHidden(!hidden)}>
        {options.map((option, index) => (
          <DropdownItem key={index} onClick={() => { navigate(option.path) }}>{i18next.t(option.name)}</DropdownItem>
        ))}
        {endOption ? (
          <>
            <DropdownDivider />
            <DropdownItem onClick={endOption.callback}>{endOption.name}</DropdownItem>
          </>
        ) : <></>}
      </DropdownMenu>
    </DropdownContainer>
  );
};

DropDown.propTypes = {
  brand: PropTypes.string,
  options: PropTypes.array,
  endOption: PropTypes.string,
  background: PropTypes.string,
  padding: PropTypes.string,
  radius: PropTypes.string,
  size: PropTypes.string,
  color: PropTypes.string,
  weight: PropTypes.string,
};

DropDown.defaultProps = {
  background: 'none',
  padding: '0',
  radius: '0',
  size: '1.45rem',
  color: 'var(--secondary)',
  weight: 'normal',
}

export default DropDown;
