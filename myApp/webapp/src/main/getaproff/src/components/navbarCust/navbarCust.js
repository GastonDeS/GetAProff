import React from 'react';
import PropTypes from 'prop-types';
import {
    BrowserRouter as Router,
    Routes,
    Route,
} from "react-router-dom";
import {Navbar, Nav, NavDropdown, Container} from 'react-bootstrap'
import styles from './navbarCust.module.css'
import Logo from '../../assets/img/logo_green.png'
import i18next from "i18next";

const NavbarCust = () => {
    return (
        <div>
            <div className="w-100">
                <div className="p-0">
                    <Router>
                        <Navbar expand="lg" sticky="top" className={styles.customNav}>
                            <Navbar.Brand href="#home">
                                <img src={Logo} alt="logo"/>
                            </Navbar.Brand>
                            <Navbar.Toggle aria-controls="basic-navbar-nav" />
                            <Navbar.Collapse id="basic-navbar-nav">
                                <Nav className={styles.main}>
                                    <Container className={styles.sections}>
                                        <Nav.Link className={styles.font} href="/">{i18next.t('navbar.explore')}</Nav.Link>
                                        <Nav.Link className={styles.font} href="/about-us">{i18next.t('navbar.myClasses')}</Nav.Link>
                                        <Nav.Link className={styles.font} href="/contact-us">{i18next.t('navbar.myFavourites')}</Nav.Link>
                                    </Container>
                                    <NavDropdown className={styles.font} title={i18next.t('navbar.myAccount')} id="basic-nav-dropdown" align={{ sm: 'end' }}>
                                        <NavDropdown.Item href="#action/3.1">{i18next.t('navbar.myProfile')}</NavDropdown.Item>
                                        <NavDropdown.Item href="#action/3.2">{i18next.t('navbar.myFiles')}</NavDropdown.Item>
                                        <NavDropdown.Divider />
                                        <NavDropdown.Item href="#action/3.4">{i18next.t('navbar.logout')}</NavDropdown.Item>
                                    </NavDropdown>
                                </Nav>
                            </Navbar.Collapse>
                        </Navbar>
                        <br />
                        <Routes>
                            <Route exact path="/">
                                {/*<Home />*/}
                            </Route>
                            <Route path="/about-us">
                                {/*<AboutUs />*/}
                            </Route>
                            <Route path="/contact-us">
                                {/*<ContactUs />*/}
                            </Route>
                        </Routes>
                    </Router>
                </div>
            </div>
        </div>
    );
};

NavbarCust.propTypes = {};

export default NavbarCust;