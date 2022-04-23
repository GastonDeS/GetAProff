import React from 'react';
import PropTypes from 'prop-types';

import { Body, ButtonContainer, ClassInfo, Margin, Subject, Title, Wrapper } from './ClassCard.styles';
import Status from '../Status';
import Button from '../Button';

const ClassCard = ({ subject, teacherId, level, price, status }) => {
  return(
    <Wrapper>
      <Margin>
        <Subject>Subject</Subject>
        <Title>
          <h1>{subject}</h1>
        </Title>
        <Status color='green' status={status}/>
      </Margin>
      <Body>
        <ClassInfo>
          <h1>Teacher: {teacherId}</h1>
          <p>Level: {level}</p>
          <p>Price: ${price}/hour</p>
        </ClassInfo>
        <ButtonContainer>
          <Button text='Finish' color='red' fontSize='1rem'/>
          <Button text='Enter' fontSize='1rem'/>
        </ButtonContainer>
      </Body>
    </Wrapper>
  );
};

export default ClassCard;
