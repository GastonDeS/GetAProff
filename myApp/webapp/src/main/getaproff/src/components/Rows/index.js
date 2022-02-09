import React, { useEffect, useState } from "react";
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

const Rows = ({ edit, data, multi, type, handleCheck }) => {

  const handleChange = (event) => {
    handleCheck(event.target.checked, data.id);
  };

  return (
    <>
      <Row>
        {multi ? (
          edit ? (
            <>
              <Data style={{ width: "45%" }}>{data.first}</Data>
              <Data style={{ width: "20%" }}>{data.second}</Data>
              <Data style={{ width: "30%" }}>{data.third}</Data>
              <Data style={{ width: "5%", textAlign: "end" }}>
                <CheckBox handleCheck={handleChange} />
              </Data>
            </>
          ) : (
            <>
              <Data style={{ width: "50%" }}>{data.first}</Data>
              <Data style={{ width: "20%" }}>{data.second}</Data>
              <Data style={{ width: "30%" }}>{data.third}</Data>
            </>
          )
        ) : (
          <>
            <Data style={{ width: "95%" }}>{data}</Data>
            <Data style={{ width: "5%", textAlign: "end" }}>
              {type === "check" ? (
                <CheckBox handleCheck={handleChange} />
              ) : (
                <Button text="X" fontSize="0.8rem" />
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
