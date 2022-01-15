import React, { useState } from 'react';
import PropTypes from 'prop-types';

import {
  DropdownItem,
  DropdownMenu,
  DropdownDivider
} from 'styled-dropdown-component';

import { 
  DropdownContainer,
  DropdownBtn} from './DropDown.styles';

import i18next from "i18next";

const DropDown = ( {brand, options, endOption} ) => {

  const [hidden, setHidden] = useState(true);

  return (
    <DropdownContainer>
      <DropdownBtn dropdownToggle onClick={() => setHidden(!hidden)}>
        {brand}
      </DropdownBtn>
      <DropdownMenu hidden={hidden} toggle={() => setHidden(!hidden)}>
        {options.map(option => (
          <DropdownItem key={Math.random()}>{i18next.t(option)}</DropdownItem>
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
