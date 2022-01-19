import React, {useHistory} from 'react'
import PropTypes from 'prop-types'
import { ButtonContainer, MainContainer, SearchBarContainer, SearchContainer, TutorContainer, Wrapper } from './Home.styles'
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
            <Button text='Matematicas'/>
            <Button text='Fisica'/>
            <Button text='Ingles'/>
            <Button text='Piano'/>
            <Button text='Cocina'/>
          </ButtonContainer>
        </SearchContainer>
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
      </MainContainer>
    </Wrapper>
  )
}

Home.propTypes = {

}

export default Home
