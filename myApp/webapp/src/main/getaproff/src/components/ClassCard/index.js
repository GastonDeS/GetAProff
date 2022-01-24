import React from 'react';
import PropTypes from 'prop-types';

import { Body, ButtonContainer, ClassInfo, Margin, Subject, Title, Wrapper } from './ClassCard.styles';
import Status from '../Status';
import Button from '../Button';

const ClassCard = ({ subject }) => {
  return(
    <Wrapper>
      <Margin>
        <Subject>Subject</Subject>
        <Title>
          <h1>{subject}</h1>
        </Title>
        <Status color='green' status='Active'/>
      </Margin>
      <Body>
        <ClassInfo>
          <h1>Teacher: John Doe</h1>
          <p>Level: Secondary</p>
          <p>Price: 800/hour</p>
        </ClassInfo>
        <ButtonContainer>
          <Button text='Finish' color='red' fontSize='1rem'/>
          <Button text='Enter' fontSize='1rem'/>
        </ButtonContainer>
      </Body>
    </Wrapper>
  );
};

ClassCard.propTypes = {};

export default ClassCard;
