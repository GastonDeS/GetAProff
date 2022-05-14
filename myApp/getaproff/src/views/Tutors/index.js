import React, { useEffect, useState } from "react";
import { MainContainer, Wrapper, Levels } from "../../GlobalStyle";
import Navbar from "../../components/Navbar";
import SearchBar from "../../components/SearchBar";
import {
  FiltersContainer,
  FilterSection,
  Grid,
  StyledPagination,
  TutorsWrapper,
  SearchBarContainer
} from "./Tutors.styles";
import SelectDropdown from "../../components/SelectDropdown";
import RangeSlider from "../../components/RangeSlider";
import i18next from "i18next";
import { Form, FormCheck, PageItem } from "react-bootstrap";
import RatingStars from "../../components/RatingStars";
import Button from "../../components/Button";
import TutorCard from "../../components/TutorCard";
import { useLocation } from "react-router-dom";
import axios from "axios";
import {useForm, Controller} from "react-hook-form";
import {userService} from "../../services";

const Tutors = () => {
  const [order, setOrder] = useState(0);
  const [level, setLevel] = useState(0);
  const [page, setPage] = useState(1);
  const [subject, setSubject] = useState();
  const [tutors, setTutors] = useState([]);
  const {register, handleSubmit, getValues} = useForm({defaultValues: {"maxPrice": 5000, "level" : 0, "rating" : 0, "order": 1}});
  const orders = [
    {name: "Price Ascending", id: 1},
    {name: "Price Descending", id: 2},
    {name: "Rate Ascending", id: 3},
    {name: "Rate Descending", id: 4},
  ];
  const rating = [0, 1, 2, 3, 4];

  const myURL = window.location.search;

  //TODO: revisar esto de los parametros para filtros
  const haveParams = myURL.indexOf("?") > -1;
  const location = useLocation();

  useEffect(() => {
    setSubject(location.state.subject.name);
  }, []);

  useEffect(() => {
    userService.getUsers()
    subject && axios.get('/teachers/subject/?page=1&search=' + subject)
      .then(res => {
        res.data.forEach(item => {
          setTutors(previous => [...previous, item]);
        })
      });
  }, [subject]);

  //Funciones

  const handlePaging = (event) =>
    setPage(parseInt(event.currentTarget.innerHTML));

  const cleanFilters = () => {
    document.getElementById("filterForm").reset();
  };

  const submitForm = () => {
    document.getElementById("filterForm").submit();
  };

  const onSubmit = (data, e) => {
    console.log(data);
    userService.getUsers(data)
    console.log(e);
  };

  let items = [];
  for (let number = 1; number <= 5; number++) {
    items.push(
      <PageItem
        key={number}
        active={number === page}
        onClick={handlePaging}
      >
        {number}
      </PageItem>
    );
  }

  return (
    <Wrapper>
      <Navbar />
      <MainContainer>
        <FiltersContainer>
          <h3>Filtros</h3>
          <form
            id="filterForm"
            style={{ display: "flex", flexDirection: "column" }}
            onSubmit={handleSubmit(onSubmit)}
          >
            <FilterSection>
              <h4>Ordenar por</h4>
              <SelectDropdown
                  name={"order"}
                  register={register}
                  options={orders}
                  type="Choose an order"
              />
            </FilterSection>
            <FilterSection>
              <h4>Precio Maximo</h4>
              <RangeSlider
                  register={register}
                  name="maxPrice"
                  minValue={1}
                  maxValue={10000}
                  value={9990}
                  getValues = {getValues}
              />
            </FilterSection>
            <FilterSection>
              <h4>Nivel</h4>
              <Form.Group>
                {Levels.map((object, index) => {
                  return (
                     <FormCheck
                         {...register("level")}
                        key={index}
                        type={"radio"}
                        value={index}
                        id={i18next.t(object)}
                        label={i18next.t(object)}
                      />
                  );
                })}
              </Form.Group>
            </FilterSection>
            <FilterSection>
              <h4>Rating</h4>
              {rating.map((rating) => {
                return (
                  <Form.Check
                    key={rating}
                    id={"radio-rating-" + rating}
                    style={{ marginLeft: "-20px" }}
                  >
                    <Form.Check.Input
                        {...register("rating")}
                      type={"radio"}
                      value={rating}
                      style={{
                        position: "fixed",
                        opacity: "0",
                        pointerEvents: "none",
                      }}
                    />
                    <Form.Check.Label>
                      <div style={{ width: "100%", display: "flex" }}>
                        <RatingStars markedStars={rating} key={rating} />
                        <p style={{ marginLeft: "3px" }}>o más</p>
                      </div>
                    </Form.Check.Label>
                  </Form.Check>
                );
              })}
            </FilterSection>
            <hr />
            <div style={{ alignSelf: "center" }}>
              <Button text={"Aplicar filtros"}/>
            </div>
            {haveParams && (
              <div style={{ alignSelf: "center", marginTop: "8px" }}>
                <Button text={"Limpiar filtros"} callback={cleanFilters} />
              </div>
            )}
          </form>
        </FiltersContainer>
        <TutorsWrapper>
          <SearchBarContainer>
            <SearchBar />
          </SearchBarContainer>
          <Grid>
            {tutors && tutors.map(tutor => {
              return <TutorCard key={tutor.id} user={tutor}/>
            })}
          </Grid>
          <StyledPagination>{items}</StyledPagination>
        </TutorsWrapper>
      </MainContainer>
    </Wrapper>
  );
};

export default Tutors;
