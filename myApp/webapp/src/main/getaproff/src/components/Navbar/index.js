import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import { useNavigate } from 'react-router-dom';
import AuthService from "../../services/authService";

//Styles
import { Wrapper, Content, LogoImg, Container, NavLink } from "./Navbar.styles";
import Button from '../Button';
import Dropdown from '../DropDown';
import Logo from '../../assets/img/logo_green.png';

import i18next from "i18next";

const Navbar = ({ empty }) => {
  const navigate = useNavigate();
  const [auth, setAuth] = useState(false);

  const onLogout = () => {
    AuthService.logout();
    navigate('/login');
  }

  const options = [{name: 'navbar.myProfile', path: '/profile'}, {name: 'navbar.myFiles', path: '/my-files'}];
  const endOption = {name: i18next.t('navbar.logout'), callback: onLogout}

  useEffect(() => {
    AuthService.getCurrentUser() ? setAuth(true) : setAuth(false);
  }, []);

  return (
    <Wrapper>
      <Content>
        <LogoImg src={Logo} alt='logo'/>
        {
          empty ? <></> :
          (auth ? 
            <Container>
              <NavLink to="/">{i18next.t('navbar.explore')}</NavLink>
              <NavLink to="/my-classes">{i18next.t('navbar.myClasses')}</NavLink>
              <NavLink to="/">{i18next.t('navbar.myFavourites')}</NavLink>
              <Dropdown brand={i18next.t('navbar.myAccount')} options={options} endOption={endOption}/>
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