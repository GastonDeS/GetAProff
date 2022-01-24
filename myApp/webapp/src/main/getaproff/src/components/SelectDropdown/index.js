import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import styled from "styled-components";

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

const SelectDropdown = ({ type, options, setIndex }) => {

  const handleClick = (event) => {
    setIndex(event.target.value);
  }
  
  return (
    <Select onChange={handleClick}>
      {
        type ? <option value="" hidden>{type}</option> : <></>
      }
      {
        options.map((option, index) => {
          return <option key={index} value={index}>{option}</option>
        })
      }
    </Select>
  );
};

SelectDropdown.propTypes = {
  options: PropTypes.array,
  type: PropTypes.string,
  setIndex: PropTypes.func
};

export default SelectDropdown;
