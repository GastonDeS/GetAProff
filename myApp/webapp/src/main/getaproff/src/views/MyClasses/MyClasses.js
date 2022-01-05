import React, {useState} from 'react';
import PropTypes from 'prop-types';
import styles from './MyClasses.module.css';
import {Tab, Tabs} from 'react-bootstrap';
import Navbar from '../../components/navbarCust/navbarCust';
import ClassCard from '../../components/classCard/classCard';

const MyClasses = () => {
  const [key, setKey] = useState('home');

  return (
    <div>
      <Navbar/>
      <div className={styles.Main}>
        <Tabs
          id="controlled-tab-example"
          activeKey={key}
          onSelect={(k) => setKey(k)}
          className={styles.TabContainer}
        >
          <Tab eventKey="home" title="Home">
            
          </Tab>
          <Tab eventKey="profile" title="Profile">
            
          </Tab>
          <Tab eventKey="contact" title="Contact" disabled>
            
          </Tab>
        </Tabs>
        <ClassCard/>
      </div>
    </div>
  );
}

MyClasses.propTypes = {};

MyClasses.defaultProps = {};

export default MyClasses;
