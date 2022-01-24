import React from 'react';
import PropTypes from 'prop-types';

import { Wrapper, MainContainer, Content, FilterContainer, CardContainer } from './MyClasses.styles';
import Navbar from '../../components/Navbar';
import ClassCard from '../../components/ClassCard';

const MyClasses = () => {
  return (
    <Wrapper>
      <Navbar/>
      <MainContainer>
        <Content>
          <FilterContainer>
            <p>Hola</p>
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
