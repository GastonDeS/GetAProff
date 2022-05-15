import Navbar from "../../components/Navbar";
import React, {useEffect, useState} from "react";
import AuthService from '../../services/authService'
import {
    Wrapper,
    PageContainer,
    TextContainer
} from "./Favorites.styles";
import TutorCard from "../../components/TutorCard";
import {TutorContainer} from "../Home/Home.styles";
import { userService } from "../../services";
import {StyledPagination} from "../Tutors/Tutors.styles";
import {PageItem} from "react-bootstrap";
import i18next from "i18next";

const Favorites = () => {
    const [favoriteUsersList, setFavoriteUsersList] = useState([]);
    const [currentUser, setCurrentUser] = useState()
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

    useEffect(() => {
        setCurrentUser(AuthService.getCurrentUser());
    }, []);

    useEffect(async () => {
        if(currentUser)
            await userService.getFavoriteTeachers(currentUser.id, page)
                .then(
                    res => {
                        setFavoriteUsersList(res.data);
                        setPageQty(res.pageQty)
                    }
                );
    },[page, currentUser]);

    return (
        <Wrapper>
        <Navbar/>
        <PageContainer>
            <h1>{i18next.t('favourites.title')}</h1>
            { (favoriteUsersList.length === 0) ?
                <TextContainer>
                    <h2>{i18next.t('favourites.empty')}</h2>
                </TextContainer>
            :
                <TutorContainer>
                    {favoriteUsersList.map(item => {
                            return <TutorCard key={item.id} user={item} ima/>
                    })}
                </TutorContainer>
            }
            {pageQty !== 1 && <StyledPagination>{items}</StyledPagination>}
        </PageContainer>
    </Wrapper>);
}

export default Favorites;