import React from "react";
import {Wrapper} from "../../GlobalStyle";
import Navbar from "../../components/Navbar";
import Button from "../../components/Button";
import {CustomH2, Img, MainContainer} from "./Error404.styles";
import {useNavigate} from "react-router-dom";
import i18next from "i18next";

const Error404 = () => {
    let navigate = useNavigate();
    return(
        <Wrapper>
            <Navbar/>
            <MainContainer>
                <h1 style={{fontSize: '4rem',margin: '20px 0 -40px 0'}}>{i18next.t('error.title')}</h1>
                <Img src={require("../../assets/img/error404.png")} alt="Error 404"/>
                <CustomH2>{i18next.t('error.description')}</CustomH2>
                <Button text={i18next.t('error.btnText')} callback={() => navigate('/')}/>
            </MainContainer>
        </Wrapper>
    )
}

export default Error404;