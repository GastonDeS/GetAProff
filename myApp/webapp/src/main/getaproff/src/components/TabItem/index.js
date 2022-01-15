import React, { memo } from 'react'
import PropTypes from 'prop-types'

import styled from 'styled-components';

const selectedColor = "hsla(185, 96%, 22%, 0.35);";
const defaultColor = "transparent";

const Item = styled.div`
  background-color: var(--primary);
  width: 100%;
  padding: 10px;
  cursor: pointer;
  transition: 0.3s;
  display: flex;
  align-items: center;
  justify-content: center;
  backdrop-filter: blur(8.5px);
  -webkit-backdrop-filter: blur(8.5px);

  > p {
    width: fit-content;
    border-radius: 2rem;
    padding: 0.5em 0.75em;
    color: var(--secondary);
    text-transform: uppercase;
    letter-spacing: 0.4rem;
    background-color: ${(props) => (
      props.selected ? selectedColor : defaultColor
    )};
  }
`;

const TabItem = memo(({ children, ...restProps }) => (
  <Item {...restProps}><p>{children}</p></Item>
));

TabItem.propTypes = {}

export default TabItem
