import Navbar from "../../components/Navbar";
import React, {useEffect, useState} from "react";
import AuthService from '../../services/authService'
import {
    Wrapper,
    PageContainer
} from "./Favorites.styles";
import TutorCard from "../../components/TutorCard";
import {useNavigate} from "react-router-dom";
import {TutorContainer} from "../Home/Home.styles";
import { userService } from "../../services";

const Favorites = () => {
    const [favoriteUsersList, setFavoriteUsersList] = useState([]);
    const navigate = useNavigate();
    const [currentUser, setCurrentUser] = useState(AuthService.getCurrentUser())

    useEffect( () => {
        if(currentUser)
            userService.getFavoriteTeachers(currentUser.id)
                .then(
                    teachersList => setFavoriteUsersList(teachersList)
                )
                .catch(error => {
                    console.log(error);
                    navigate("/error");
                })
        else navigate("/login");
    },[])

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