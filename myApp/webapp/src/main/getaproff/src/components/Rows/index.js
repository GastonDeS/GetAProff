import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import styled from "styled-components";

import CheckBox from "../CheckBox";
import { Row } from "../../GlobalStyle";
import Button from "../Button";

export const Data = styled.td`
  border: none;
  font-size: var(--fontSmall);

  img {
    width: 18px;
  }

  a {
    cursor: pointer;
  }
`;

const Rows = ({ edit, remove, data, rowId, multi, check }) => {

  return (
    <>
      <Row>
        {multi ? (edit ? (
          <>
            <Data style={{ width: "45%" }}>{data.first}</Data>
            <Data style={{ width: "20%" }}>{data.second}</Data>
            <Data style={{ width: "30%" }}>{data.third}</Data>
            <Data style={{ width: "5%", textAlign: "end" }}>
              <CheckBox/>
            </Data>
          </>
        ) : (
          <>
            <Data style={{ width: "50%" }}>{data.first}</Data>
            <Data style={{ width: "20%" }}>{data.second}</Data>
            <Data style={{ width: "30%" }}>{data.third}</Data>
          </>
        )) :
        (
          <>
            <Data style={{ width: "95%" }}>{data}</Data>
            <Data style={{ width: "5%", textAlign: "end" }}>
              {
                check ? <CheckBox/> : <Button text="X" fontSize="0.8rem"/>
              }
            </Data>
          </>
        )
        }
      </Row>
    </>
  );
};

Rows.propTypes = {
  edit: PropTypes.bool,
  multi: PropTypes.bool,
  remove: PropTypes.func,
  data: PropTypes.object,
  rowId: PropTypes.number
};

Rows.defaultProps = {
  multi: true,
  edit: true,
  check: true,
}

export default Rows;
