import React from "react";
import PropTypes from "prop-types";
import styled from "styled-components";

import i18next from "i18next";

import Delete from "../../assets/img/delete_icon.png";

const Row = styled.tr`
  background: hsla(185, 96%, 22%, 0.35);
  box-shadow: 0 0 9px 0 rgb(0 0 0 / 10%);
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-radius: 0.625rem;
  padding: 0.65rem;
  margin-bottom: 8px;
  width: 100%;
`;

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

const Rows = ({ edit, remove, data, rowId }) => {
  return (
    <>
      <Row>
        {edit ? (
          <>
            <Data style={{ width: "45%" }}>{data.first}</Data>
            <Data style={{ width: "20%" }}>{data.second}</Data>
            <Data style={{ width: "30%" }}>{data.third}</Data>
            <Data style={{ width: "5%", textAlign: "end" }}>
              <a onClick={() => remove(rowId, data.url)}>
                <img src={Delete} alt="delete-icon"/>
              </a>
            </Data>
          </>
        ) : (
          <>
            <Data style={{ width: "50%" }}>{data.first}</Data>
            <Data style={{ width: "20%" }}>{data.second}</Data>
            <Data style={{ width: "30%" }}>{data.third}</Data>
          </>
        )}
      </Row>
    </>
  );
};

Rows.propTypes = {
  edit: PropTypes.bool,
  remove: PropTypes.func,
  subject: PropTypes.object
};

export default Rows;
