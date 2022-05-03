import React from 'react';
import PropTypes from 'prop-types';

import { Body, ButtonContainer, ClassInfo, Margin, Subject, Title, Wrapper } from './ClassCard.styles';
import Status from '../Status';
import Button from '../Button';

const ClassCard = ({ subject, user, level, price, statusCode, canRate, classId, handlers }) => {
  let color, status;
  switch (statusCode){
    case 0:
      color = 'orange';
      status = 'Pending';
      break;
    case 1:
      color = 'green';
      status = 'Active'
      break;
    case 2:
      color = 'red';
      status= 'Finished';
      break;
    case 3:
      color = 'red';
      status= 'Finished';
      break;
    default:
      color = 'red';
      status = 'Error'
      break;
  }


  return(
    <Wrapper>
      <Margin>
        <Subject>Subject</Subject>
        <Title>
          <h1>{subject}</h1>
        </Title>
        <Status color={color} status={status}/>
      </Margin>
      <Body>
        <ClassInfo>
          <h1>Teacher: {user.name}</h1>
          <p>Level: {level}</p>
          <p>Price: ${price}/hour</p>
        </ClassInfo>
        <ButtonContainer>
          {statusCode !== 2 ? (
              <>
              <Button text='Finish' color='red' fontSize='1rem' callback={handlers.finishClass}/>
              <Button text='Enter' fontSize='1rem' callback={() => handlers.enterClassroom(classId)}/>
              </>
            ) :
              (canRate && <Button text='Rate' fontSize='1rem' value = {classId} callback={() => handlers.rateClass(classId)}/>)
            }

        </ButtonContainer>
      </Body>
    </Wrapper>
  );
};

export default ClassCard;
