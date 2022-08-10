import React from 'react';

import { Body, ButtonContainer, ClassInfo, MainCardContainer, Margin, Subject, Title, Wrapper } from './ClassCard.styles';
import Status from '../Status';
import Button from '../Button';
import i18next from "i18next";

const ClassCard = ({ subject, user, level, price, statusCode, isTeacher, classId, handlers, notifications }) => {
  let color, status;
  switch (statusCode){
    case 0:
      color = 'orange';
      status = 'pending';
      break;
    case 1:
      color = 'green';
      status = 'active';
      break;
    case 2:
      color = 'red';
      status= 'finished';
      break;
    case 3:
      color = 'red';
      status= 'cancelledStudent';
      break;
    case 4:
      color = 'red';
      status = 'cancelledTeacher';
      break;
    case 5:
      color = 'red';
      status = 'declined';
      break;
      case 6:
      color = 'grey';
      status = 'rated';
      break;
    default:
      color = 'red';
      status = 'error';
      break;
  }


  return(
    <Wrapper>
      <Margin>
        <Subject>{i18next.t('classCard.subject')}</Subject>
        <Title>
          <h1>{subject}</h1>
        </Title>
        <Status color={color} status={i18next.t('classCard.status.'+ status)}/>
      </Margin>
      <Body>
        <MainCardContainer>
          <ClassInfo>
            {isTeacher ? <h1>{i18next.t('classCard.student')}: {user.name}</h1> :
              <h1>{i18next.t('classCard.teacher')}: {user.name}</h1>}
            <p>{i18next.t('classCard.level')}: {i18next.t("subjects.levels." + level)}</p>
            <p>{i18next.t('classCard.price')}: ${price}/{i18next.t('classCard.hour')}</p>
          </ClassInfo>
          {notifications !== 0 && <Status color="red" status={notifications}/>}
        </MainCardContainer>
        <ButtonContainer>
          {statusCode !== 4 && statusCode !== 3 && statusCode !== 5 && <Button text={i18next.t('classCard.enter')} fontSize='1rem' callback={() => handlers.enterClassroom(classId)}/>}
          {statusCode === 0 && <>
            {isTeacher ?
                <>
              <Button text={i18next.t('classCard.accept')} fontSize='1rem' callback={() => handlers.acceptClass(classId)}/>
              <Button text={i18next.t('classCard.decline')} fontSize='1rem' callback={() => handlers.declineClass(classId)}/>
              </> :
                <Button text={i18next.t('classCard.cancel')} fontSize='1rem' callback={() => handlers.cancelClassS(classId)}/>
            }
          </>}
          {statusCode === 1 && isTeacher && <Button text={i18next.t('classCard.finish')} color='red' fontSize='1rem' callback={() => handlers.finishClass(classId)}/>}
          {statusCode === 2 && !isTeacher && <Button text={i18next.t('classCard.rate')} fontSize='1rem'  callback={() => handlers.rateClass(user.id, classId)}/>}

        </ButtonContainer>
      </Body>
    </Wrapper>
  );
};

export default ClassCard;
