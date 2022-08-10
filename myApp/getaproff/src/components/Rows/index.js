import React from "react";
import PropTypes from "prop-types";
import styled from "styled-components";

import { Row } from "../../GlobalStyle";
import Button from "../Button";
import CheckBox from "../CheckBox";

const Data = styled.td`
  border: none;
  font-size: var(--fontSmall);
  img {
    width: 18px;
  }

  a {
    cursor: pointer;
  }
`;

const Rows = ({ edit, data, multi, type, handleCheck, checked }) => {

  let jsonValues = [];
  for (let key in data) {
    jsonValues.push(data[key]);
  }

  const handleChange = (event) => {
    handleCheck(event.target.checked, data);  
  };

  const handleRemove = () => {
    handleCheck(data);
  };

  return (
    <>
      <Row>
        {multi ? (
          edit ? (
            <>

              <Data style={{ width: "45%" }}>{jsonValues[0]}</Data>
              <Data style={{ width: "20%" }}>{jsonValues[1]}</Data>
              <Data style={{ width: "30%" }}>{jsonValues[2]}</Data>
              <Data style={{ width: "5%", textAlign: "end" }}>
                <CheckBox handleCheck={handleChange} checked={checked}/>
              </Data>
            </>
          ) : (
            <>
              <Data style={{ width: "50%" }}>{jsonValues[0]}</Data>
              <Data style={{ width: "20%" }}>{jsonValues[1]}</Data>
              <Data style={{ width: "30%" }}>{jsonValues[2]}</Data>
            </>
          )
        ) : (
          <>
            <Data style={{ width: "95%" }}>{data.name}</Data>
            <Data style={{ width: "5%", textAlign: "end" }}>
              {type === "check" ? (
                <CheckBox handleCheck={handleChange} checked={checked}/>
              ) : (
                <Button text="X" fontSize="0.8rem" callback={handleRemove}/>
              )}
            </Data>
          </>
        )}
      </Row>
    </>
  );
};

Rows.propTypes = {
  edit: PropTypes.bool,
  multi: PropTypes.bool,
  data: PropTypes.object,
  type: PropTypes.string,
  handleCheck: PropTypes.func,
};

Rows.defaultProps = {
  multi: true,
  edit: true,
  type: "check",
};

export default Rows;
