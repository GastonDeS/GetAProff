import React from 'react';
import PropTypes from 'prop-types';
import styles from './customButton.module.css';
import { Button } from 'react-bootstrap';

const CustomButton = (props) => {
  return (
    <Button onClick={props.action} className={styles.CustomButton}>
      {props.text}
    </Button>
  );
}

CustomButton.propTypes = {
  action: PropTypes.func,
  text: PropTypes.string
};

CustomButton.defaultProps = {};

export default CustomButton;
