import React, {useEffect, useState} from 'react';

import { Content, FilterContainer, CardContainer, Filter, SelectContainer } from './MyClasses.styles';
import Navbar from '../../components/Navbar';
import ClassCard from '../../components/ClassCard';
import Tab from '../../components/Tab';
import TabItem from '../../components/TabItem'
import SelectDropdown from '../../components/SelectDropdown';
import { Wrapper, MainContainer } from "../../GlobalStyle";
import AuthService from "../../services/authService";
import {classroomService, userService} from "../../services";
import {useNavigate} from "react-router-dom";
import i18next from "i18next";
import authService from "../../services/authService";

const MyClasses = () => {
  const navigate = useNavigate();
  const [tabIndex, setTabIndex] = useState(0);
  const [status, setStatus] = useState(0);
  const [requestedClasses, setRequestedClasses] = useState([]);
  const [offeredClasses, setOfferedClasses] = useState([]);
  const currUser = AuthService.getCurrentUser();
  const options = [
    {
      name: i18next.t('myClasses.status.any'),
      id: 0
    },
    {
      name: i18next.t('myClasses.status.pending'),
      id: 1
    },
    {
      name: i18next.t('myClasses.status.active'),
      id: 2
    },
    {
      name: i18next.t('myClasses.status.finished'),
      id: 3
    }];

  const handleFilter = e => {
    setStatus(e.target.value);
  }
  const handleRate = uid => {
    navigate(`/users/${uid}/reviews`);
  }
  const handleEnterClassroom = classId => {
    navigate(`/classroom/${classId}`);
  }
  const handleFinishClass= classId => {
    classroomService.finishClass(classId, currUser.id)
        .then(r => console.log(r) )
  }
  const handleCancelClass = classId => {
    classroomService.cancelClass(classId, currUser.id)
        .then(r => console.log(r) )
  }

  const handleAcceptClass = classId => {
    classroomService.acceptClass(classId, currUser.id)
        .then(r => console.log(r) )
  }


  const handler = {
    rateClass: handleRate,
    enterClassroom: handleEnterClassroom,
    finishClass: handleFinishClass,
    acceptClass: handleAcceptClass,
    cancelClass: handleCancelClass
  }

  useEffect( () => {
    let asTeacher = tabIndex === 1;
    let setClasses = asTeacher ? setOfferedClasses : setRequestedClasses;
    userService.getUserClasses(currUser.id, asTeacher, status - 1)
        .then(res => setClasses([...res.data]))
  }, [tabIndex, status]);


  return (
    <Wrapper>
      <Navbar/>
      <MainContainer>
        <Content>
          <FilterContainer>
            <Tab setIndex={setTabIndex} flexDirection='column'>
              {/* index = 0 */}
              <TabItem style={{ borderRadius: '0.625rem' }} fontSize="1.1rem">Requested</TabItem>
              {/* index = 1 */}
              {
                currUser.teacher ? <TabItem fontSize="1.1rem">Offered</TabItem> : <></>
              }
            </Tab>
            <Filter>Filter status:</Filter>
            <SelectContainer>
              <SelectDropdown options={options} handler={handleFilter}/>
            </SelectContainer>
          </FilterContainer>
          <CardContainer>
            {tabIndex === 1 ?
                offeredClasses.map((Class, index) => {
                  return <ClassCard key={index} classId={Class.classId} subject={Class.subjectName} user={Class.student}
                    price={Class.price} level={Class.level} statusCode={Class.status} isTeacher={true} handlers={handler}/>
                })
                :
                requestedClasses.map((Class, index) => {
                  return <ClassCard key={index} classId={Class.classId} subject={Class.subjectName} user={Class.teacher}
                                    price={Class.price} level={Class.level} statusCode={Class.status} isTeacher={false} handlers={handler}/>
                })}
          </CardContainer>
        </Content>
      </MainContainer>
    </Wrapper>
  );
};

MyClasses.propTypes = {};

export default MyClasses;
