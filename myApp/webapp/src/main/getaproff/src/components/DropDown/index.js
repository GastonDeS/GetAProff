import React, { useState } from 'react';
import PropTypes from 'prop-types';
import i18next from "i18next";

import {
  DropdownItem,
  DropdownMenu,
  DropdownDivider
} from 'styled-dropdown-component';
import styled from "styled-components";
import {Link} from "react-router-dom";

const DropdownContainer = styled.div`
  position: relative;
  display: inline-block;
  cursor: pointer;
  padding: 10px;

  button, input[type="submit"], input[type="reset"] {
    background: none;
    color: inherit;
    border: none;
    padding: 0;
    font: inherit;
    cursor: pointer;
    outline: inherit;
  }

  button {
    font-size: 1.45rem;
    font-weight: bold;
    background: none;
    color: var(--secondary);
    padding: 0;
    border: none;

    &:hover {
      color: black;
    }
  }
`;

const DropDown = ( {brand, options, endOption} ) => {

  const [hidden, setHidden] = useState(true);

  return (
    <DropdownContainer>
      <button onClick={() => setHidden(!hidden)}>{brand}</button>
      <DropdownMenu hidden={hidden} toggle={() => setHidden(!hidden)}>
        {options.map((option, index) => (
          <DropdownItem tag="span" key={index}>
            <Link to='/my-files' style={{ textDecoration: 'none'}}>
              {i18next.t(option)}
            </Link></DropdownItem>
        ))}
        <DropdownDivider />
        <DropdownItem>{endOption}</DropdownItem>
        </DropdownMenu>
    </DropdownContainer>
  );
};

DropDown.propTypes = {
  brand: PropTypes.string,
  options: PropTypes.array,
  endOption: PropTypes.string
};

export default DropDown;
