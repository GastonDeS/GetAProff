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

const MyClasses = () => {
  const navigate = useNavigate();
  const [tabIndex, setTabIndex] = useState(0);
  const [filterIndex, setFilterIndex] = useState(0);
  const [requestedClasses, setRequestedClasses] = useState([]);
  const [offeredClasses, setOfferedClasses] = useState([]);
  const currUser = AuthService.getCurrentUser();
  const options = ['Any', 'Pending', 'Active', 'Finished'];

  const handleFilter = e => {
    setFilterIndex(e.target.value);
  }
  const handleRate = e => {
    console.log("rate");
  }
  const handleEnterClassroom = classId => {
    navigate(`/classroom/${classId}`);
  }
  const handleFinishClass= e => {
    console.log("finish");
  }

  const handler = {
    rateClass: handleRate,
    enterClassroom: handleEnterClassroom,
    finishClass: handleFinishClass
  }

  useEffect( () => {
    let asTeacher = tabIndex === 1;
    let setClasses = asTeacher ? setOfferedClasses : setRequestedClasses;
    userService.getUserClasses(currUser.id, asTeacher, filterIndex - 1)
        .then(res => setClasses([...res.data]))
  }, [tabIndex, filterIndex]);


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
              <TabItem fontSize="1.1rem">Offered</TabItem>
            </Tab>
            <Filter>Filter by:</Filter>
            <SelectContainer>
              <SelectDropdown options={options} handler={handleFilter} type='Status' usingIndexAsValue={true}/>
            </SelectContainer>
          </FilterContainer>
          <CardContainer>
            {tabIndex === 1 ?
                offeredClasses.map((Class, index) => {
                  return <ClassCard key={index} classId={Class.classId} subject={Class.subjectName} user={Class.student}
                    price={Class.price} level={Class.level} statusCode={Class.status} canRate={false} handlers={handler}/>
                })
                :
                requestedClasses.map((Class, index) => {
                  return <ClassCard key={index} classId={Class.classId} subject={Class.subjectName} user={Class.teacher}
                                    price={Class.price} level={Class.level} statusCode={Class.status} canRate={true} handlers={handler}/>
                })}
            {/*<ClassCard subject="Programación Orientada a Objetos"/>*/}
            {/*<ClassCard subject="Programación Imperativa"/>*/}
          </CardContainer>
        </Content>
      </MainContainer>
    </Wrapper>
  );
};

MyClasses.propTypes = {};

export default MyClasses;
