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
import {StyledPagination} from "../Tutors/Tutors.styles";
import {PageItem} from "react-bootstrap";

const MyClasses = () => {
  const navigate = useNavigate();
  const [tabIndex, setTabIndex] = useState(0);
  const [status, setStatus] = useState(0);
  const [requestedClasses, setRequestedClasses] = useState([]);
  const [offeredClasses, setOfferedClasses] = useState([]);
  const [reloadCards, setReloadCards] = useState(false)
  const [page, setPage] = useState(1)
  const [pageQty, setPageQty] = useState(1)
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
    },
    {
      name: i18next.t('myClasses.status.rated'),
      id: 4
    },
    {
      name: i18next.t('myClasses.status.canceled'),
      id: 5
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
    setReloadCards(!reloadCards)
    classroomService.cancelClass(classId, currUser.id)
        .then(r => console.log(r) )
  }

  const handleAcceptClass = classId => {
    setReloadCards(!reloadCards)
    classroomService.acceptClass(classId, currUser.id)
        .then(r => console.log(r) )
  }

  let items = [];
  for (let number = 1; number <= pageQty; number++) {
    items.push(
        <PageItem
            key={number}
            active={number === page}
            onClick={() => setPage(number)}
        >
          {number}
        </PageItem>
    );
  }

  const handler = {
    rateClass: handleRate,
    enterClassroom: handleEnterClassroom,
    finishClass: handleFinishClass,
    acceptClass: handleAcceptClass,
    cancelClass: handleCancelClass
  }

  useEffect( () => {
    setPage(1)
  }, [tabIndex, status])

  useEffect( () => {
    let asTeacher = tabIndex === 1;
    let setClasses = asTeacher ? setOfferedClasses : setRequestedClasses;
    userService.getUserClasses(currUser.id, asTeacher, status - 1, page)
        .then(res => {
          setClasses([...res.data]);
          setPageQty((parseInt(res.headers['x-total-pages']) + 1));
        })
  }, [tabIndex, status, reloadCards, page]);


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
                <>
                  {offeredClasses.length === 0 && <h1>Parece que aun no tenes clases</h1>}
                  {offeredClasses.map((Class, index) => {
                  return <ClassCard key={index} classId={Class.classId} subject={Class.subjectName} user={Class.student}
                  price={Class.price} level={Class.level} statusCode={Class.status} isTeacher={true} handlers={handler}/>
                })}
                </>
                :
                requestedClasses.map((Class, index) => {
                  return <ClassCard key={index} classId={Class.classId} subject={Class.subjectName} user={Class.teacher}
                                    price={Class.price} level={Class.level} statusCode={Class.status} isTeacher={false} handlers={handler}/>
                })}
            {pageQty !== 1 && <StyledPagination>{items}</StyledPagination>}
          </CardContainer>
        </Content>
      </MainContainer>
    </Wrapper>
  );
};

MyClasses.propTypes = {};

export default MyClasses;
