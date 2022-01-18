import React from 'react';
import PropTypes from 'prop-types';
import styles from './classCard.module.css';
import {Card, Container} from 'react-bootstrap';
import CustomButton from '../customButton/customButton';
import '../../index.css';

const ClassCard = () => {
  return (
    <Card className={styles.Card}>
      <Container className={styles.Subject}>
        <Card.Text className={styles.SubjectText}>Subject</Card.Text>
        <Card.Title className={styles.Title}>Programación Imperativa</Card.Title>
      </Container>
      <Card.Body className={styles.Body}>
        <Container className={styles.BodyMain}>
          <Card.Text className={styles.Teacher}>Teacher: John Doe</Card.Text>
          <Card.Text className={styles.Text}>Level: Secondary</Card.Text>
          <Card.Text className={styles.Text}>Price: 800/hour</Card.Text>
        </Container>
        <Container className={styles.Buttons}>
          <CustomButton text="Finish" color="red"/>
          <CustomButton text="Enter classroom"/>
        </Container>
      </Card.Body>
    </Card>
  );
}

ClassCard.propTypes = {};

ClassCard.defaultProps = {};

export default ClassCard;
