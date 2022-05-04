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
import {StyledPagination} from "../Tutors/Tutors.styles";
import {PageItem} from "react-bootstrap";

const Favorites = () => {
    const [favoriteUsersList, setFavoriteUsersList] = useState([]);
    const navigate = useNavigate();
    const [currentUser, setCurrentUser] = useState(AuthService.getCurrentUser())
    const [page, setPage] = useState(1)
    const [pageQty, setPageQty] = useState(1)

    let items = [];
    for (let number = 1; number <= pageQty; number++) {
        items.push(
            <PageItem
                key={number}
                active={number === page}
                onClick={() => setPage(number)}
            >
                {number}
            </PageItem>
        );
    }

    useEffect( () => {
        if(currentUser)
            userService.getFavoriteTeachers(currentUser.id)
                .then(
                    res => {
                        setFavoriteUsersList(res.data);
                        setPageQty(res.pageQty)
                    }
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
            {pageQty !== 1 && <StyledPagination>{items}</StyledPagination>}
        </PageContainer>
    </Wrapper>);
}

export default Favorites;