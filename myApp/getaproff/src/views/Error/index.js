import React, { useEffect, useState } from "react";
import {Wrapper} from "../../GlobalStyle";
import Navbar from "../../components/Navbar";
import Button from "../../components/Button";
import {useNavigate} from "react-router-dom";
import i18next from "i18next";
import { getQuery, useQuery } from "../../hooks/useQuery";
import { status } from "../../assets/constants";
import styled from 'styled-components';

const MainContainer = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 4rem;
    width: 100vw;
    height: 90%;

    h1 {
      font-size: max(1.3vw, 10px);
      margin-bottom: 4vh;
      color: var(--secondary);
    }
`;

const Error = () => {
    const [loading, setLoading] = useState(true);
    let navigate = useNavigate();
    let query = useQuery();
    let errorStatus = getQuery(query, "code", status.PAGE_NOT_FOUND);

    useEffect(() => {
        if (errorStatus === status.PAGE_NOT_FOUND) navigate('/404');
        setLoading(false);
    }, []);

    return(
        <Wrapper>
            <Navbar/>
            {
                !loading &&
                <MainContainer>
                    <h1 style={{fontSize: '4rem',margin: '20px 0 -40px 0'}}>{i18next.t('error.title')} {errorStatus}</h1>
                    <Button text={i18next.t('error.btnText')} callback={() => navigate('/')}/>
                </MainContainer>
            }
        </Wrapper>
    )
}

export default Error;