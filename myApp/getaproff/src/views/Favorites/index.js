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
import { favouritesService } from "../../services";
import {StyledPagination} from "../Tutors/Tutors.styles";
import {PageItem} from "react-bootstrap";
import i18next from "i18next";
import { useNavigate } from "react-router-dom";
import { handleService } from "../../handlers/serviceHandler";

const Favorites = () => {
    const [favoriteUsersList, setFavoriteUsersList] = useState([]);
    const [currentUser, setCurrentUser] = useState();
    const [page, setPage] = useState(1);
    const [pageQty, setPageQty] = useState(1);
    const navigate = useNavigate();

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
        if(currentUser) {
            let res = await favouritesService.getFavoriteTeachers(page);
            if (!res.failure) setPageQty(parseInt(res.headers['x-total-pages']));
            const data = handleService(res, navigate);
            setFavoriteUsersList(data);
        }
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