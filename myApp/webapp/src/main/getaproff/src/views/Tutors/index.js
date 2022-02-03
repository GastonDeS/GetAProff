import React, {useState} from 'react';
import {MainContainer, Wrapper, Levels} from "../../GlobalStyle";
import Navbar from "../../components/Navbar";
import {SearchBarContainer} from "../Home/Home.styles";
import SearchBar from "../../components/SearchBar";
import {FiltersContainer, FilterSection, Grid, StyledPagination} from "./Tutors.styles";
import SelectDropdown from "../../components/SelectDropdown";
import RangeSlider from "../../components/RangeSlider";
import i18next from "i18next";
import {Form, FormCheck, PageItem} from "react-bootstrap";
import RatingStars from "../../components/RatingStars";
import Button from "../../components/Button";
import TutorCard from "../../components/TutorCard";


const Tutors = () => {

    const [currOrder, setOrder] = useState(0);
    const [currPrice, setPrice] = useState(1000);
    const [currLevel, setLevel] = useState(0);
    const [currPage, setPage] = useState(1);

    const order = ["Price Ascending", "Price Descending", "Rate Ascending", "Rate Descending"];
    const rating = [0, 1, 2, 3, 4];

    const myURL = window.location.search;

    //TODO: revisar esto de los parametros para filtros
    const haveParams = myURL.indexOf("?") > -1;


    //Funciones

    const handlePaging = event =>  setPage(parseInt(event.currentTarget.innerHTML));

    const cleanFilters = () => {
        document.getElementById("filterForm").reset();
    }

    const submitForm = () => {
        document.getElementById("filterForm").submit();
    }

    const s = () => {
        console.log("submiteed");
    }

    let items = [];
    for (let number = 1; number <= 5; number++) {
        items.push(
            <PageItem key={number} active={number === currPage} onClick={handlePaging}>
                {number}
            </PageItem>,
        );
    }


    return (
        <Wrapper>
            <Navbar/>
            <MainContainer>
                <div style={{width: '30%'}}>
                    <FiltersContainer>
                        <h3>Filtros</h3>
                        <Form id="filterForm" style={{display: "flex", flexDirection: "column"}} onSubmit={s}>
                            <FilterSection>
                                <h4>Ordenar por</h4>
                                <SelectDropdown options={order} setIndex={setOrder} type="Choose an order"/>
                            </FilterSection>
                            <FilterSection>
                                <h4>Precio Maximo</h4>
                                <RangeSlider maxValue={10000} value={currPrice} onChange={setPrice} name={"price"}/>
                            </FilterSection>
                            <FilterSection>
                                <h4>Nivel</h4>
                                {Levels.map((object, index) => {
                                    return <FormCheck key={index} type={"radio"} name="level" value={index}
                                                      id={i18next.t(object)} label={i18next.t(object)}/>
                                })}
                            </FilterSection>
                            <FilterSection>
                                <h4>Rating</h4>
                                {rating.map(rating => {
                                    return <Form.Check key={rating} id={"radio-rating-" + rating}
                                                       style={{marginLeft: "-20px"}}>
                                        <Form.Check.Input type={"radio"} name="rating" value={rating}
                                                          style={{
                                                              position: "fixed",
                                                              opacity: "0",
                                                              pointerEvents: 'none'
                                                          }}/>
                                        <Form.Check.Label>
                                            <div style={{width: "100%", display: "flex"}}>
                                                <RatingStars markedStars={rating} key={rating}/>
                                                <p style={{marginLeft: "3px"}}>o más</p>
                                            </div>
                                        </Form.Check.Label>
                                    </Form.Check>
                                })}
                            </FilterSection>
                            <hr/>
                            <div style={{alignSelf: "center"}}>
                                <Button text={"Aplicar filtros"} callback={submitForm}/>
                            </div>
                            {haveParams &&
                                <div style={{alignSelf: "center", marginTop: "8px"}}>
                                    <Button text={"Limpiar filtros"} callback={cleanFilters}/>
                                </div>
                            }
                        </Form>
                    </FiltersContainer>
                </div>
                <div style={{width: '70%', display: "flex", flexDirection: "column"}}>
                    <div style={{alignSelf: "center"}}>
                        <SearchBarContainer>
                            <SearchBar/>
                        </SearchBarContainer>
                    </div>
                    <Grid>
                        <TutorCard/>
                        <TutorCard/>
                        <TutorCard/>
                        <TutorCard/>
                        <TutorCard/>
                        <TutorCard/>
                        <TutorCard/>
                        <TutorCard/>
                    </Grid>
                        <StyledPagination>{items}</StyledPagination>
                </div>
            </MainContainer>
        </Wrapper>
    );
}

export default Tutors

