import React from 'react';
import PropTypes from 'prop-types';
import styled from "styled-components";
import i18next from "i18next";


const Select = styled.select`
  width: 100%;
  height: fit-content;
  background: white;
  color: black;
  padding: 0.25rem 0 0.25rem 0.5rem;
  font-size: var(--fontSmall);
  border: 1px solid var(--secondary);
  border-radius: 0.625rem;
  
  &:disabled {
    background: #DEDEDE;
    -webkit-appearance: none;
    -moz-appearance: none;
    text-indent: 0.01px;
    text-overflow: '';
   
  }

  option {
    color: black;
    background: white;
    display: flex;
    white-space: pre;
    min-height: 20px;
    padding: 0px 2px 1px;
  }
`;

const SelectDropdown = ({ register, name, registerOptions, type, options, handler, value, disabled }) => {

  return (
    <Select {...register(name, registerOptions)} onChange={handler} value={value} disabled={disabled}>
      {
        type && !disabled ? <option value="" hidden>{type}</option> : <></>
      }
      {
        options && options.map((option, index) => {
          return <option key={index} value={option.id}>{option.name}</option>
        })
      }
    </Select>
  );
};

SelectDropdown.propTypes = {
  options: PropTypes.array,
  type: PropTypes.string,
  handler: PropTypes.func
};

SelectDropdown.defaultProps = {
  register: (x) => x,
  registerOptions: {}
}


export default SelectDropdown;
