import React from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';

const checkedColor = "#026670";
const uncheckedColor = "hsla(185, 96%, 22%, 0.35)";
const visible = "visible";
const hidden = "hidden";

const CheckboxContainer = styled.div`
  display: flex;
  vertical-align: middle;
  align-items: center;
  justify-content: center;
`;

const Icon = styled.svg`
  fill: none;
  stroke: white;
  stroke-width: 2px;
`;

const HiddenCheckbox = styled.input.attrs({ type: 'checkbox' })`
  display: none;
`;

const StyledCheckbox = styled.div`
  display: flex;
  width: 16px;
  height: 16px;
  background: ${(props) => (props.checked ? checkedColor : uncheckedColor)};
  border-radius: 3px;
  transition: all 150ms;
  cursor: pointer;

  ${HiddenCheckbox}:focus + & {
    box-shadow: 0 0 0 3px pink;
  }

  ${Icon} {
    visibility: ${(props) => (props.checked ? visible : hidden)};
  }
`;

const CheckBox = ({ handleCheck, checked }) => {
  return (
    <CheckboxContainer>
      <label>
        <HiddenCheckbox 
          checked={checked} 
          onChange={handleCheck}
        />
        <StyledCheckbox checked={checked}>
          <Icon viewBox="0 0 24 24">
            <polyline points="20 6 9 17 4 12" />
          </Icon>
        </StyledCheckbox>
      </label>
    </CheckboxContainer>
  );
};

CheckBox.propTypes = {
  handleCheck: PropTypes.func
};

export default CheckBox;
