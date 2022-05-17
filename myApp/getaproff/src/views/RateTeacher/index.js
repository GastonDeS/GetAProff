import Navbar from "../../components/Navbar";
import React, {useEffect, useState} from "react";
import {MainContainer, Wrapper} from "../../GlobalStyle";
import SelectDropdown from "../../components/SelectDropdown";
import {FormContainer, FormInput, PageContainer} from "./RateTeacher.styles";
import Textarea from "../../components/Textarea";
import Button from "../../components/Button";
import {useForm} from "react-hook-form";
import { userService, ratingService } from "../../services";
import {useNavigate, useParams} from "react-router-dom";
import AuthService from "../../services/authService";
import {Error} from "../Login/Login.styles";
import i18next from "i18next";
import { handleService } from "../../handlers/serviceHandler";

const RateTeacher = () => {
    const navigate = useNavigate()
    const {register, handleSubmit, formState: {errors}} = useForm()
    const [teacherInfo, setTeacherInfo] = useState();
    const currUser = AuthService.getCurrentUser();
    const teacher = useParams()

    const onSubmit = async (data) => {
        await ratingService.rateTeacher(teacher.id, data)
            .then(r => {
                navigate(`/users/${currUser.id}/classes`);
            })
            .catch(err => console.log(err))

    }

    useEffect(async () => {
        const res = await userService.getUserInfo(teacher.id);
        const data = handleService(res, navigate);
        setTeacherInfo(data);
    }, [])

    return (
        <Wrapper>
            <Navbar/>
            <MainContainer>
                <PageContainer>
                    {teacherInfo && <h1>{i18next.t('rateTeacher.title')} {teacherInfo.name}?</h1>}
                    <FormContainer  onSubmit={handleSubmit(onSubmit)}>
                        <FormInput>
                            <label style={{alignSelf:'flex-start'}}>{i18next.t('rateTeacher.rate')}</label>
                            <SelectDropdown register={register} registerOptions = {{required: {value:true, message: "This field is required"}}} name="rating" type={i18next.t('rateTeacher.starRating.title')}  options={[{name: i18next.t('rateTeacher.starRating.one'), id: 1},{name: i18next.t('rateTeacher.starRating.two'), id: 2}, {name: i18next.t('rateTeacher.starRating.three'), id: 3},
                                {name: i18next.t('rateTeacher.starRating.four'), id: 4}, {name: i18next.t('rateTeacher.starRating.five'), id: 5}]} />
                            {errors.rating && <Error>{errors.rating.message}</Error>}
                        </FormInput>
                        <FormInput>
                            <label style={{alignSelf:'flex-start'}}>{i18next.t('rateTeacher.review')} </label>
                            <Textarea name = "review" register={register} placeholder={i18next.t('rateTeacher.reviewPlaceholder')} />
                        </FormInput>
                        <Button text={i18next.t('rateTeacher.uploadRating')} />
                    </FormContainer>
                </PageContainer>
            </MainContainer>
        </Wrapper>
    )
}

export default RateTeacher;