import Navbar from "../../components/Navbar";
import React, {useEffect, useState} from "react";
import AuthService from '../../services/authService'
import {
    Wrapper,
    PageContainer
} from "./Favorites.styles";
import TutorCard from "../../components/TutorCard";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {TutorContainer} from "../Home/Home.styles";

const Favorites = () => {
    const [favoriteUsersList, setFavoriteUsersList] = useState([]);
    const navigate = useNavigate();
    useEffect( () =>
    {
        let currUser = AuthService.getCurrentUser();
        axios.get('/users' + '/' + currUser.id + '/favorites' )
            .then(res => {
                if(res.data && res.data.length !== 0)
                    setFavoriteUsersList(res.data);

            })
            .catch( err => {
                console.log(err);
                navigate('/error');
            })

    })

    return (
        <Wrapper>
        <Navbar/>
        <PageContainer>
            <TutorContainer>
            { (favoriteUsersList.length === 0)?
                <h1>No tutors added to favourites yet!</h1>
            :
                favoriteUsersList.map(item => {
                        return <TutorCard key={item.id} user={item} ima/>
                })
            }
            </TutorContainer>
        </PageContainer>
    </Wrapper>);
}

export default Favorites;