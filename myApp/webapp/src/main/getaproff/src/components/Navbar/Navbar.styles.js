import styled from 'styled-components';
import { Link } from "react-router-dom";

export const Wrapper = styled.div `
  padding: 0 20px;
  background-color: var(--primary);
`;

export const Content = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 0;
  margin: 0;
`;

export const LogoImg = styled.img`
  width: 200px;
  
  @media screen and (max-width: 900px) {
    width: 150px;
  }
`;

export const Container = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: fit-content;
  column-gap: 1em;
`;

export const NavLink = styled(Link)`
  font-size: var(--fontLarge);
  font-weight: bold;
  text-decoration: none;
  color: var(--secondary);
  
  &:hover {
    color: black;
  }
`;