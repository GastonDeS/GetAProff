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
  const [options, setOptions] = useState();

  const onLogout = () => {
    AuthService.logout();
    navigate('/');
    window.location.reload();
  };
  
  const endOption = {name: i18next.t('navbar.logout'), callback: onLogout}

  useEffect(() => {
    var curr = AuthService.getCurrentUser();
    if (curr) {
      setAuth(true);
      setOptions([{name: 'navbar.myProfile', path: '/users/' + curr.id}, {name: 'navbar.myFiles', path: '/my-files'}]);
    }
  }, []);

  return (
    <Wrapper>
      <Content>
        <LogoImg onClick={() => { navigate('/')}}>
          <img src={Logo} alt='logo'/>
        </LogoImg>
        {
          empty ? <></> :
          (auth ? 
            <Container>
              <Container style={{gap: '1em'}}>
                <NavLink to="/">{i18next.t('navbar.explore')}</NavLink>
                <NavLink to="/my-classes">{i18next.t('navbar.myClasses')}</NavLink>
                <NavLink to="/">{i18next.t('navbar.myFavourites')}</NavLink>
              </Container>
              <Dropdown brand={i18next.t('navbar.myAccount')} options={options} endOption={endOption} weight="bold"/>
            </Container> :
            <Container>
              <Button text='Login' callback={() => { navigate('/users/login')}}/>
              <Button text='Register' callback={() => { navigate('/users/new')}}/>
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