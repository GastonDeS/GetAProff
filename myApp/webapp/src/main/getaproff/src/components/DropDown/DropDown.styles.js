import styled from "styled-components";

import { Dropdown } from 'styled-dropdown-component';
import { Button } from 'styled-button-component';

export const DropdownContainer = styled(Dropdown)`
`;

export const DropdownBtn = styled(Button)`
  font-size: 1.45rem;
  font-weight: bold;
  background: none;
  color: var(--secondary);
  padding: 0;
  border: none;

  &:not(.disable):hover {
    color: black;
    border: none;
    background: none;
  }
`;