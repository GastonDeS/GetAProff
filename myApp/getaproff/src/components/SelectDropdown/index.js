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

  option {
    color: black;
    background: white;
    display: flex;
    white-space: pre;
    min-height: 20px;
    padding: 0px 2px 1px;
  }
`;

const SelectDropdown = ({ type, options, handler, value }) => {

  return (
    <Select onChange={handler} value={value}>
      {
        type ? <option value="" hidden>{type}</option> : <></>
      }
      {
        options && options.map((option, index) => {
          var name;
          if (i18next.t(option)) {
            name = i18next.t(option)
          } else {
            name = option
          }
          return <option key={index} value={option.id}>{name}</option>
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

export default SelectDropdown;
