import React, { useState, useEffect } from 'react'
import PropTypes from 'prop-types'
import axios from 'axios'

import Profile from '../../assets/img/profile.png'

import { ButtonContainer, Content, MainContainer, SearchBarContainer, SearchContainer, TutorContainer, Wrapper } from './Home.styles'
import Navbar from '../../components/Navbar'
import TutorCard from '../../components/TutorCard'
import SearchBar from '../../components/SearchBar'
import Button from '../../components/Button'

const Home = () => {
  const [topRated, setTopRated] = useState({users: []});
  const [mostRequested, setMostRequested] = useState({users: []});
  
  // const fetchImage = async (userId) => {
  //   const res = await axios.get('api/images/' + userId)
  //     .catch(error => {
  //       console.error('ERROR ' + error.message)
  //     });
  //   return res ? 'data:image/png;base64,' + res.data.image : Profile
  // }

  // axios.get('api/images/87')
    // .then(res => {
    //   setImage('data:image/png;base64,' + res.data.image);
    //   // console.log(res.data.image);
    // });

  const fetchTopRated = () => {
    axios.get('/api/teachers/top-rated').then(res => {
      const data = res.data
      setTopRated({ users: data.map(item => {
        // const ret = await fetchImage(item.userId)
        return {...item, image: Profile}
      })})
    });
  }

  const fetchMostRequested = () => {
    axios.get('/api/teachers/most-requested').then(res => {
      const data = res.data
      setMostRequested({ users: data.map(item => {
        return {...item, image: Profile}
      })})
    });
  }

  useEffect(() => {
    fetchTopRated();
    fetchMostRequested();
  }, []);

  return (
    <Wrapper>
      <Navbar/>
      <MainContainer>
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
            {topRated.users.map(item => {
              return <TutorCard key={item.userId}
                name={item.name} 
                description={item.description} 
                rating={item.rate}
                maxPrice={item.maxPrice}
                minPrice={item.minPrice}
                image={item.image}/>
            })}
          </TutorContainer>
        </Content>
        <Content>
          <h2>Most requested teachers</h2>
          <TutorContainer>
            {mostRequested.users.map(item => {
              return <TutorCard key={item.userId}
                name={item.name} 
                description={item.description} 
                rating={item.rate}
                maxPrice={item.maxPrice}
                minPrice={item.minPrice}
                image={item.image}/>
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
