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
  SearchBarContainer, CustomH1
} from "./Tutors.styles";
import SelectDropdown from "../../components/SelectDropdown";
import RangeSlider from "../../components/RangeSlider";
import i18next from "i18next";
import { Form, FormCheck, PageItem } from "react-bootstrap";
import RatingStars from "../../components/RatingStars";
import Button from "../../components/Button";
import TutorCard from "../../components/TutorCard";
import { useLocation, useNavigate } from "react-router-dom";
import {useForm, useFormState} from "react-hook-form";
import { userService } from "../../services";
import { handleService } from "../../handlers/serviceHandler";

const Tutors = () => {
  const [page, setPage] = useState(1);
  const [pageQty, setPageQty] = useState(0)
  const [isFormReseted, setIsFormReseted] = useState(false)
  const [formSubmitted, setFormSubmitted] = useState(false)
  const [tutors, setTutors] = useState([]);
  const [firstLoad, setFirstLoad] = useState(true);
  const [mostExpensivePrice, setMostExpensivePrice] = useState();

  const navigate = useNavigate();
  const search = useLocation().search;
  const searchQuery = new URLSearchParams(search).get('search');

  const {register, handleSubmit, getValues, reset, control, setValue} = useForm(
      {defaultValues: {"level" : 0, "rating" : 0, "order": 1, "search": searchQuery}}
  );
  const {dirtyFields} = useFormState({control})
  const orders = [
    {name: i18next.t('tutors.priceAsc'), id: 1},
    {name: i18next.t('tutors.priceDesc'), id: 2},
    {name: i18next.t('tutors.rateAsc'), id: 3},
    {name: i18next.t('tutors.rateDesc'), id: 4},
  ];
  const rating = [0, 1, 2, 3, 4];

  const resetForm = () => {
    reset(
        {
          search: getValues("search"),
          rating: 0,
          level: 0,
          maxPrice: mostExpensivePrice,
          order: 1
        }
    );
    setIsFormReseted(!isFormReseted);
  }

  useEffect(async () => {
    const resExpensive  = await userService.getMostExpensiveUserTeaching(getValues("search"));
    if(firstLoad) {
      setMostExpensivePrice(resExpensive.data.price);
      reset({maxPrice: resExpensive.data.price});
      setFirstLoad(false)
    }
    const res = await userService.getUsers(getValues(), page);
    if (!res.failure) setPageQty((parseInt(res.headers['x-total-pages'])));
    setTutors(handleService(res, navigate));
  }, [page, formSubmitted, isFormReseted])

  const onSubmit = () => {
    setFormSubmitted(!formSubmitted);
    setPage(1);
  }

  const checkIfDirty = () =>{
    return dirtyFields.level || dirtyFields.maxPrice || dirtyFields.rating || dirtyFields.order;
  }

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

  return (
    <Wrapper>
      <Navbar />
      <MainContainer>
        <FiltersContainer>
          <h3>{i18next.t('tutors.filters')}</h3>
          <form
            id="filterForm"
            style={{ display: "flex", flexDirection: "column" }}
            onSubmit={handleSubmit(onSubmit)}
          >
            <FilterSection>
              <h4>{i18next.t('tutors.orderBy')}</h4>
              <SelectDropdown
                  name={"order"}
                  register={register}
                  options={orders}
                  type="Choose an order"
              />
            </FilterSection>
            <FilterSection>
              <h4>{i18next.t('tutors.maxPrice')}</h4>
              <RangeSlider
                  register={register}
                  name="maxPrice"
                  minValue={1}
                  maxValue={mostExpensivePrice}
              />
            </FilterSection>
            <FilterSection>
              <h4>{i18next.t('tutors.level')}</h4>
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
              <h4>{i18next.t('tutors.rating')}</h4>
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
                        <p style={{ marginLeft: "3px" }}>{i18next.t('tutors.orMore')}</p>
                      </div>
                    </Form.Check.Label>
                  </Form.Check>
                );
              })}
            </FilterSection>
            <hr />
            <div style={{ alignSelf: "center" }}>
              <Button text={i18next.t('tutors.applyFilters')}/>
            </div>
            {checkIfDirty() && (
              <div style={{ alignSelf: "center", marginTop: "8px" }}>
                <Button  text={i18next.t('tutors.clearFilters')} callback={resetForm} />
              </div>
            )}
          </form>
        </FiltersContainer>
        <TutorsWrapper>
          <SearchBarContainer>
            <SearchBar register={register} name={"search"} value={ getValues("search") } handleSubmit={() => handleSubmit(onSubmit)()}/>
          </SearchBarContainer>
          {tutors.length !== 0 ?
              <CustomH1>{i18next.t('tutors.available')} {getValues('search')}</CustomH1> :
              <CustomH1>{i18next.t('tutors.noTeachers')} {getValues('search')}</CustomH1>}
          <Grid>
            {tutors && tutors.map(tutor => {
              return <TutorCard key={tutor.id} user={tutor}/>
            })}
          </Grid>
          {pageQty > 1 && <StyledPagination>{items}</StyledPagination>}
        </TutorsWrapper>
      </MainContainer>
    </Wrapper>
  );
};

export default Tutors;
