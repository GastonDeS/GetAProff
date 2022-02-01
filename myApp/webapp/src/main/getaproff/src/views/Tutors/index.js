import React, {useState} from 'react';
import {MainContainer, Wrapper, Levels} from "../../GlobalStyle";
import Navbar from "../../components/Navbar";
import {SearchBarContainer} from "../Home/Home.styles";
import SearchBar from "../../components/SearchBar";
import {FiltersContainer, FilterSection} from "./Tutors.styles";
import SelectDropdown from "../../components/SelectDropdown";
import RangeSlider from "../../components/RangeSlider";
import i18next from "i18next";
import {Form, FormCheck} from "react-bootstrap";
import RatingStars from "../../components/RatingStars";
import Button from "../../components/Button";


const Tutors = () => {

    const [currOrder, setOrder] = useState(null);
    const [currPrice, setPrice] = useState(1000);

    const order = ["Price Ascending", "Price Descending", "Rate Ascending", "Rate Descending"];
    const rating = [0, 1, 2, 3, 4, 5];

    return (
        <Wrapper>
            <Navbar/>
            <MainContainer>
                <div style={{width: '30%'}}>
                    <FiltersContainer>
                        <h3>Filtros</h3>
                        <Form style={{display: "flex", flexDirection: "column"}}>
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
                                    return <FormCheck key={rating} type={"radio"} name="rating" value={rating}
                                                      label={<RatingStars markedStars={rating} key={rating}/>}/>

                                })}
                            </FilterSection>
                            <div style={{alignSelf: "center"}}>
                                <Button text={"Aplicar filtros"}/>
                            </div>
                        </Form>
                    </FiltersContainer>
                </div>
                <div style={{width: '70%'}}>
                    <SearchBarContainer>
                        <SearchBar/>
                    </SearchBarContainer>
                </div>
            </MainContainer>
        </Wrapper>
    );
}

export default Tutors

