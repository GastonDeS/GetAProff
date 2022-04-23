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

  useEffect( () => {
    let asTeacher = tabIndex === 1;
    let setClasses = asTeacher ? setOfferedClasses : setRequestedClasses;
    userService.getUserClasses(currUser.id, asTeacher, filterIndex)
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
              <SelectDropdown options={options} handler={handleFilter} type='Status'/>
            </SelectContainer>
          </FilterContainer>
          <CardContainer>
            {tabIndex === 1 ?
                offeredClasses.map((Class, index) => {
                  return <ClassCard key={index} subject={Class.subjectName} teacherId={Class.teacherId}
                    price={Class.price} level={Class.level} status={Class.status}/>
                })
                :
                requestedClasses.map((Class, index) => {
                  return <ClassCard key={index} subject={Class.subjectName} teacherId={Class.teacherId}
                                    price={Class.price} level={Class.level} status={Class.status}/>
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
