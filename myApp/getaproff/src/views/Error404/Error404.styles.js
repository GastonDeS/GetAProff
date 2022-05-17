import styled from 'styled-components';

export const MainContainer = styled.div`
    background: rgb(160,236,212);
    background: radial-gradient(circle, rgba(160,236,212,1) 0%, rgba(240,236,228,1) 50%);
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100vw;
    height: 90%;
`;

export const Img = styled.img`
    max-width: 800px;
    max-height: 660px;
    width: 80%;
`

export const CustomH2 = styled.h2`
    font-size: max(1.3vw, 10px);
    margin-bottom: 2vh;
    margin-top: -2vh;
`