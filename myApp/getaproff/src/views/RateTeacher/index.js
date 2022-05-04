import Navbar from "../../components/Navbar";
import React, {useEffect, useState} from "react";
import {MainContainer, Wrapper} from "../../GlobalStyle";
import SelectDropdown from "../../components/SelectDropdown";
import {FormContainer, FormInput, PageContainer} from "./RateTeacher.styles";
import Textarea from "../../components/Textarea";
import Button from "../../components/Button";
import {useForm} from "react-hook-form";
import {userService} from "../../services";
import {useNavigate, useParams} from "react-router-dom";
import AuthService from "../../services/authService";
import {Error} from "../Login/Login.styles";

const RateTeacher = () => {
    const navigate = useNavigate()
    const {register, handleSubmit, formState: {errors}} = useForm()
    const currUser = AuthService.getCurrentUser();
    const teacher = useParams()

    const onSubmit = (data) => {
        userService.createReview(currUser.id, teacher.id, data)
            .then(r => {
                console.log(r);
                navigate(`/users/${currUser.id}/classes`)
            })
            .catch(err => console.log(err))

    }

    return (
        <Wrapper>
            <Navbar/>
            <MainContainer>
                <PageContainer>
                    <h1>Calificar la clase de Messi</h1>
                    <FormContainer  onSubmit={handleSubmit(onSubmit)}>
                        <FormInput>
                            <label style={{alignSelf:'flex-start'}}>Rate</label>
                            <SelectDropdown register={register} registerOptions = {{required: {value:true, message: "This field is required"}}} name="rating" type="Choose a rate" options={[{name: "1 star", id: 1},{name: "2 stars", id: 2}, {name: "3 stars", id: 3},
                                {name: "4 stars", id: 4}, {name: "5 stars", id: 5}]} />
                            {errors.rating && <Error>{errors.rating.message}</Error>}
                        </FormInput>
                        <FormInput>
                            <label style={{alignSelf:'flex-start'}}>Review</label>
                            <Textarea name = "review" register={register} placeholder="Un groso el profe"/>
                        </FormInput>
                        <Button text="Calificar"/>
                    </FormContainer>
                </PageContainer>
            </MainContainer>
        </Wrapper>
    )
}

export default RateTeacher;