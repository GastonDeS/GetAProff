import React, {useHistory} from 'react'
import PropTypes from 'prop-types'
import { ButtonContainer, Content, MainContainer, SearchBarContainer, SearchContainer, TutorContainer, Wrapper } from './Home.styles'
import Navbar from '../../components/Navbar'
import TutorCard from '../../components/TutorCard'
import SearchBar from '../../components/SearchBar'
import Button from '../../components/Button'

const Home = () => {

  return (
    <Wrapper>
      <Navbar/>
      <MainContainer>
        <SearchContainer>
          <SearchBarContainer>
            <SearchBar></SearchBar>
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
            <TutorCard 
              name="John Doe" 
              description="Some quick example text to build on the card title and make up the bulk of
              the card's content. Some more text to test."
              minPrice="1000"
              maxPrice="1000"
              rating="2"
            />
            <TutorCard 
              name="John Doe" 
              description="Hola que tal"
              rating="3.75" 
              minPrice="1000" 
              maxPrice="2000"/>
            <TutorCard name="John Doe"/>
            <TutorCard name="John Doe"/>
          </TutorContainer>
        </Content>
        <Content>
          <h2>Most requested teachers</h2>
          <TutorContainer>
            <TutorCard 
              name="John Doe" 
              description="Some quick example text to build on the card title and make up the bulk of
              the card's content. Some more text to test."
              minPrice="1000"
              maxPrice="1000"
              rating="2"
            />
            <TutorCard 
              name="John Doe" 
              description="Hola que tal"
              rating="3.75" 
              minPrice="1000" 
              maxPrice="2000"/>
            <TutorCard name="John Doe"/>
            <TutorCard name="John Doe"/>
          </TutorContainer>
        </Content>
      </MainContainer>
    </Wrapper>
  )
}

Home.propTypes = {

}

export default Home
