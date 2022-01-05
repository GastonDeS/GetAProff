import React from 'react';
import PropTypes from 'prop-types';
import styles from './classCard.module.css';
import {Card} from 'react-bootstrap';

const ClassCard = () => {
  return (
    <Card className={styles.Card}>
      <Card.Header className={styles.Header}>Header</Card.Header>
      <Card.Body>
        <Card.Title>Primary Card Title</Card.Title>
        <Card.Text>
          Some quick example text to build on the card title and make up the bulk
          of the card's content.
        </Card.Text>
      </Card.Body>
    </Card>
  );
}

ClassCard.propTypes = {};

ClassCard.defaultProps = {};

export default ClassCard;
