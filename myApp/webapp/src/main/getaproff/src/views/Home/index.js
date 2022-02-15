import React from 'react';

import { ButtonContainer, Content, SearchBarContainer, SearchContainer, TutorContainer } from './Home.styles';
import Navbar from '../../components/Navbar';
import TutorCard from '../../components/TutorCard';
import SearchBar from '../../components/SearchBar';
import Button from '../../components/Button';
import { Wrapper, MainContainer } from "../../GlobalStyle";
import { useHomeFetch } from '../../hooks/useHomeFetch';

const Home = () => {
  const { topRated, mostRequested } = useHomeFetch();

  return (
    <Wrapper>
      <Navbar/>
      <MainContainer style={{ flexDirection: 'column', alignItems: 'center', justifyContent: 'flex-start' }}>
        <SearchContainer>
          <SearchBarContainer>
            <SearchBar/>
          </SearchBarContainer>
          <ButtonContainer>
            <Button text='Matematicas' fontSize='1rem'/>
            <Button text='Fisica' fontSize='1rem'/>
            <Button text='Ingles' fontSize='1rem'/>
            <Button text='Piano' fontSize='1rem'/>
            <Button text='Cocina' fontSize='1rem'/>
          </ButtonContainer>
        </SearchContainer>
        <Content>
          <h2>Top Rated Teachers</h2>
          <TutorContainer>
            {topRated.map(item => {
              return <TutorCard key={item.id} user={item}/>
            })}
          </TutorContainer>
        </Content>
        <Content>
          <h2>Most requested teachers</h2>
          <TutorContainer>
          {mostRequested.map(item => {
              return <TutorCard key={item.id} user={item}/>
            })}
          </TutorContainer>
        </Content>
      </MainContainer>
    </Wrapper>
  )
}

Home.propTypes = {
}

export default Home
