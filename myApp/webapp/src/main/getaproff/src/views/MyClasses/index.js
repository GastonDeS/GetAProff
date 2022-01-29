import React, { useState } from 'react';
import PropTypes from 'prop-types';

import { Wrapper, MainContainer, Content, FilterContainer, CardContainer, Filter, SelectContainer } from './MyClasses.styles';
import Navbar from '../../components/Navbar';
import ClassCard from '../../components/ClassCard';
import Tab from '../../components/Tab';
import TabItem from '../../components/TabItem'
import SelectDropdown from '../../components/SelectDropdown';

const MyClasses = () => {
  const [tabIndex, setTabIndex] = useState(0);
  const [filterIndex, setFilterIndex] = useState(0);
  const options = ['Any', 'Pending', 'Active', 'Finished'];

  return (
    <Wrapper>
      <Navbar/>
      <MainContainer>
        <Content>
          <FilterContainer>
            <Tab setIndex={setTabIndex} flexDirection='column'>
              {/* index = 0 */}
              <TabItem style={{ borderRadius: '0.625rem' }}>Requested</TabItem> 
              {/* index = 1 */}
              <TabItem>Offered</TabItem>
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
