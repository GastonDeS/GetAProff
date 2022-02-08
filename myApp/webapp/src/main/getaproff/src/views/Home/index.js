import React, { useState, useEffect } from 'react'
import PropTypes from 'prop-types'
import axios from 'axios'

import Profile from '../../assets/img/profile.png'

import { ButtonContainer, Content, SearchBarContainer, SearchContainer, TutorContainer } from './Home.styles'
import Navbar from '../../components/Navbar'
import TutorCard from '../../components/TutorCard'
import SearchBar from '../../components/SearchBar'
import Button from '../../components/Button'
import { Wrapper, MainContainer } from "../../GlobalStyle";

const Home = () => {
  const [topRated, setTopRated] = useState([]);
  const [mostRequested, setMostRequested] = useState([]);

  const fetchTopRated = () => {
    axios.get('/teachers/top-rated').then(res => {
      res.data.map(item => {
        setTopRated(previous => [...previous, item])
      })
    });
  }

  const fetchMostRequested = () => {
    axios.get('/teachers/most-requested').then(res => {
      res.data.map(item => {
        setMostRequested(previous => [...previous, item])
      })
    });
  }

  useEffect(() => {
    fetchTopRated();
    fetchMostRequested();
  }, []);


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
              return <TutorCard key={item.userId} user={item}/>
            })}
          </TutorContainer>
        </Content>
        <Content>
          <h2>Most requested teachers</h2>
          <TutorContainer>
          {mostRequested.map(item => {
              return <TutorCard key={item.userId} user={item}/>
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
