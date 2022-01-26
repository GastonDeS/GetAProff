import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { useNavigate } from 'react-router-dom';

import Logo from '../../assets/img/logo_green.png';

//Styles
import { Wrapper, Content, LogoImg, Container, NavLink } from "./Navbar.styles";
import Button from '../Button';
import Dropdown from '../DropDown';

import i18next from "i18next";

const options = ['navbar.myProfile', 'navbar.myFiles'];

const Navbar = ({ empty }) => {
  const [auth, setAuth] = useState(true);

  const navigate = useNavigate();

  return (
    <Wrapper>
      <Content>
        <LogoImg src={Logo} alt='lgoo'/>
        {
          empty ? <></> :
          (auth ? 
            <Container>
              <NavLink to="/">{i18next.t('navbar.explore')}</NavLink>
              <NavLink to="/my-classes">{i18next.t('navbar.myClasses')}</NavLink>
              <NavLink to="/">{i18next.t('navbar.myFavourites')}</NavLink>
              <Dropdown brand={i18next.t('navbar.myAccount')} options={options} endOption={i18next.t('navbar.logout')}/>
            </Container> :
            <Container>
              <Button text='Login' callback={() => { navigate('/login') }}/>
              <Button text='Register' callback={() => { navigate('/register') }}/>
            </Container>)
        }
      </Content>
    </Wrapper>
  );
};

Navbar.propTypes = {
  empty: PropTypes.bool
};

Navbar.defaultProps = {
  empty: false
}

export default Navbar;