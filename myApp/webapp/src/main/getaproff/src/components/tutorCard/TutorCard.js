import React from 'react';
import PropTypes from 'prop-types';
import styles from './tutorCard.module.css';
import {Card, Button} from 'react-bootstrap'
import Profile from '../../assets/img/profile.png'
import Logo from '../../assets/img/logo_green.png'
import CustomButton from '../customButton/customButton';

const TutorCard = (props) => {
  const text = 'Go to profile';
  const handleClick = () => {
    alert('I have been clicked');
  }
  return (
    <div style={{display: 'flex'}}>
      <Card className={styles.Card} onClick={props.action}>
        <Card.Img variant="top" src={Profile} className={styles.Img} alt='tutorImage'/>
        <Card.Body className={styles.Body}>
          <Card.Title>John Doe</Card.Title>
          <Card.Text className={styles.Text}>
            Some quick example text to build on the card title and make up the bulk of
            the card's content. Some more text to test.
          </Card.Text>
          <Card.Text className={styles.Price}>
            800/hour
          </Card.Text>
        </Card.Body>
      </Card>
      <Card className={styles.Card} onClick={props.action}>
        <Card.Img variant="top" src={Profile} className={styles.Img} alt='tutorImage'/>
        <Card.Body className={styles.Body}>
          <Card.Title>Card Title</Card.Title>
          <Card.Text className={styles.Text}>
          </Card.Text>
        </Card.Body>
      </Card>
      <Card className={styles.Card} onClick={props.action}>
        <Card.Img variant="top" src={Profile} className={styles.Img} alt='tutorImage'/>
        <Card.Body className={styles.Body}>
          <Card.Title>Card Title</Card.Title>
          <Card.Text className={styles.Text}>
            Some quick example text t
          </Card.Text>
        </Card.Body>
      </Card>
    </div>
  );
};

TutorCard.propTypes = {
  action: PropTypes.func
};

export default TutorCard;
