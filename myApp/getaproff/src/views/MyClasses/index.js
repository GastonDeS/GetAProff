import React, {useEffect, useState} from 'react';

import { Content, FilterContainer, CardContainer, Filter, SelectContainer } from './MyClasses.styles';
import Navbar from '../../components/Navbar';
import ClassCard from '../../components/ClassCard';
import Tab from '../../components/Tab';
import TabItem from '../../components/TabItem'
import SelectDropdown from '../../components/SelectDropdown';
import { Wrapper, MainContainer } from "../../GlobalStyle";
import AuthService from "../../services/authService";
import {useNavigate} from "react-router-dom";
import axios from "axios";

const MyClasses = () => {
  const navigate = useNavigate();
  const [tabIndex, setTabIndex] = useState(0);
  const [filterIndex, setFilterIndex] = useState(0);
  const [favUsers, setFavUsers] = useState([])
  const options = ['Any', 'Pending', 'Active', 'Finished'];

  useEffect(() => {
    let currUser = AuthService.getCurrentUser();
    axios.get('/users/' + currUser.id + '/classes')
        .then(ans => {
          console.log(ans.data)
        })
        .catch(err => {
          console.log(err);
          navigate('/error');
        })

  });

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
              <SelectDropdown options={options} setIndex={setFilterIndex} type='Status'/>
            </SelectContainer>
          </FilterContainer>
          <CardContainer>
            <ClassCard subject="Programación Orientada a Objetos"/>
            <ClassCard subject="Programación Imperativa"/>
          </CardContainer>
        </Content>
      </MainContainer>
    </Wrapper>
  );
};

MyClasses.propTypes = {};

export default MyClasses;
