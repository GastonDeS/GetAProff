import React from 'react';
import PropTypes from 'prop-types';
import styled from "styled-components";

const Wrapper = styled.div`
  display: flex;
  column-gap: 0.5rem;
  width: fit-content;
  height: fit-content;
  justify-content: center;
  align-items: center;

  p {
    color: var(--secondary);
    font-size: 1.1vw;
  }
`;

const Dot = styled.span`
  height: 0.9vw;
  width: 0.9vw;
  border-radius: 50%;
  display: inline-block;
`

const Status = ({ color, status }) => {
  return (
    <Wrapper>
      <Dot className='dot' style={{backgroundColor: color}}></Dot>
      <p>{status}</p>
    </Wrapper>
  );
};

Status.propTypes = {
  color: PropTypes.string,
  status: PropTypes.string
};

export default Status;
