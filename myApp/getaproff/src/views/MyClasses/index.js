import React, {useEffect, useState} from 'react';
import { handleService } from "../../handlers/serviceHandler";
import { Content, FilterContainer, CardContainer, Filter, SelectContainer } from './MyClasses.styles';
import Navbar from '../../components/Navbar';
import ClassCard from '../../components/ClassCard';
import Tab from '../../components/Tab';
import TabItem from '../../components/TabItem'
import SelectDropdown from '../../components/SelectDropdown';
import { Wrapper, MainContainer } from "../../GlobalStyle";
import AuthService from "../../services/authService";
import {classesService, classroomService} from "../../services";
import {useNavigate} from "react-router-dom";
import i18next from "i18next";
import {StyledPagination} from "../Tutors/Tutors.styles";
import {PageItem} from "react-bootstrap";
import { classStatus } from '../../assets/constants';

const MyClasses = () => {
  const navigate = useNavigate();
  const [tabIndex, setTabIndex] = useState(0);
  const [status, setStatus] = useState(-1);
  const [requestedClasses, setRequestedClasses] = useState([]);
  const [offeredClasses, setOfferedClasses] = useState([]);
  const [reloadCards, setReloadCards] = useState(false);
  const [page, setPage] = useState(1);
  const [pageQty, setPageQty] = useState(1);
  const currUser = AuthService.getCurrentUser();
  const options = [
    {
      name: i18next.t('myClasses.status.any'),
      id: classStatus.ANY
    },
    {
      name: i18next.t('myClasses.status.pending'),
      id: classStatus.PENDING
    },
    {
      name: i18next.t('myClasses.status.active'),
      id: classStatus.ACCEPTED
    },
    {
      name: i18next.t('myClasses.status.finished'),
      id: classStatus.FINISHED
    },
    {
      name: i18next.t('myClasses.status.rated'),
      id: classStatus.RATED
    },
    {
      name: i18next.t('myClasses.status.cancelled'),
      id: classStatus.ALLCANCELLED
    }];

  const handleFilter = (e) => {
    setStatus(e.target.value);
    setPage(1);
  }

  const handleRate = (uid, classId) => {
    navigate(`/users/${uid}/reviews/${classId}`);
  }

  const handleEnterClassroom = (classId) => {
    navigate(`/classroom/${classId}`);
  }

  const handleFinishClass = async (classId) => {
    await classroomService.finishClass(classId)
      .then(() => setReloadCards(true));
  }

  const handleDeclineClass = async (classId) => {
    await classroomService.declineClass(classId)
        .then(() => setReloadCards(true));
  }

  const handleCancelClassS = async (classId) => {
    await classroomService.cancelClassS(classId)
        .then(() => setReloadCards(true));
  }

  const handleAcceptClass = async (classId) => {
    await classroomService.acceptClass(classId)
      .then(() => setReloadCards(true));
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
  };

  const fetchCancelledClasses = async (setClasses, asTeacher) => {
    let classes = [];
    let pages = 0;
    const resTeacher = await classesService.getUserClasses(asTeacher, classStatus.CANCELLEDT, page);
    if (!resTeacher.failure) pages += parseInt(resTeacher.headers['x-total-pages'])
    const dataTeacher = handleService(resTeacher, navigate);
    const resStudent = await classesService.getUserClasses(asTeacher, classStatus.CANCELLEDT, page);
    if (!resStudent.failure) pages += parseInt(resStudent.headers['x-total-pages'])
    const dataStudent = handleService(resStudent, navigate);
    setClasses(classes.concat(dataTeacher).concat(dataStudent));
  }

  const fetchClasses = async (setClasses, asTeacher) => {
    if (Number(status) === classStatus.ALLCANCELLED) {
      return fetchCancelledClasses(setClasses, asTeacher);
    }
    const res = await classesService.getUserClasses(asTeacher, status, page);
    if (!res.failure) setPageQty((parseInt(res.headers['x-total-pages'])));
    setClasses(handleService(res, navigate));
    setReloadCards(false);
  }

  const handler = {
    rateClass: handleRate,
    enterClassroom: handleEnterClassroom,
    acceptClass: handleAcceptClass,
    finishClass: handleFinishClass,
    declineClass: handleDeclineClass,
    cancelClassS: handleCancelClassS,
  }

  useEffect(() => {
    setStatus(-1);
    setPage(1);
  }, [tabIndex])

  useEffect(() => {
    if (reloadCards) {
      let asTeacher = tabIndex === 1;
      let setClasses = asTeacher ? setOfferedClasses : setRequestedClasses;
      fetchClasses(setClasses, asTeacher);
    }
  }, [reloadCards]);

  useEffect(async () => {
    let asTeacher = tabIndex === 1;
    let setClasses = asTeacher ? setOfferedClasses : setRequestedClasses;
    fetchClasses(setClasses, asTeacher);
  }, [tabIndex, status, page])


  return (
    <Wrapper>
      <Navbar/>
      <MainContainer>
        <Content>
          <FilterContainer>
            <Tab setIndex={setTabIndex} flexDirection='column'>
              {/* index = 0 */}
              <TabItem style={{ borderRadius: '0.625rem' }} fontSize="1.1rem">{i18next.t('myClasses.requested')}</TabItem>
              {/* index = 1 */}
              {
                currUser.teacher ? <TabItem fontSize="1.1rem">{i18next.t('myClasses.offered')}</TabItem> : <></>
              }
            </Tab>
            <Filter>{i18next.t('myClasses.filter')}</Filter>
            <SelectContainer>
              <SelectDropdown options={options} handler={handleFilter} value={status}/>
            </SelectContainer>
          </FilterContainer>
          <CardContainer>
            {tabIndex === 1 ?
                <>
                  {offeredClasses.length === 0 && <h1>{i18next.t('myClasses.noOffered')}</h1>}
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
            {pageQty > 1 && <StyledPagination>{items}</StyledPagination>}
          </CardContainer>
        </Content>
      </MainContainer>
    </Wrapper>
  );
};

MyClasses.propTypes = {};

export default MyClasses;
