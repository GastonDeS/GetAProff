import React from 'react';
import { ButtonContainer, Content, SearchBarContainer, SearchContainer, TutorContainer } from './Home.styles';
import Navbar from '../../components/Navbar';
import TutorCard from '../../components/TutorCard';
import SearchBar from '../../components/SearchBar';
import Button from '../../components/Button';
import { Wrapper, MainContainer } from "../../GlobalStyle";
import { useHomeFetch } from '../../hooks/useHomeFetch';
import { useNavigate } from 'react-router-dom';
import i18next from "i18next";

const Home = () => {
  const { topRated, mostRequested, subjects } = useHomeFetch();

  const navigate = useNavigate();

  return (
    <Wrapper>
      <Navbar/>
      <MainContainer style={{ flexDirection: 'column', alignItems: 'center', justifyContent: 'flex-start' }}>
        <SearchContainer>
          <SearchBarContainer>
            <SearchBar/>
          </SearchBarContainer>
          <ButtonContainer>
            {
              subjects && subjects.map((item, index) => {
                return <Button key={index} text={item.name} fontSize='1rem' callback={() => navigate(`tutors?search=${item.name}`)}/>
              })
            }
          </ButtonContainer>
        </SearchContainer>
        <Content>
          <h2>{i18next.t('home.topRatedTeachers')}</h2>
          <TutorContainer>
            {topRated && topRated.map(item => {
              return <TutorCard key={item.id} user={item}/>
            })}
          </TutorContainer>
        </Content>
        <Content>
          <h2>{i18next.t('home.mostRequestedTeachers')}</h2>
          <TutorContainer>
          {mostRequested && mostRequested.map(item => {
              return <TutorCard key={item.id} user={item}/>
            })}
          </TutorContainer>
        </Content>
      </MainContainer>
    </Wrapper>
  )
}

export default Home
