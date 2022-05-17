import { useState, useEffect } from 'react'
import AuthService from "../services/authService";
import i18next from "i18next";
import { useNavigate } from 'react-router-dom';

import ProfileImg from '../assets/img/no_profile_pic.jpeg';
import { userService, filesService, ratingService, favouritesService } from '../services';
import { handleService } from '../handlers/serviceHandler';

export const useProfileFetch = (id) => {
  const [index, setIndex] = useState(0);
  const [subjects, setSubjects] = useState([]);
  const [user, setUser] = useState();
  const [loading, setLoading] = useState(true);
  const [reviews, setReviews] = useState([]);
  const [image, setImage] = useState(ProfileImg);
  const [certifications, setCertifications] = useState([]);
  const [currentUser, setCurrentUser] = useState();
  const [isTeacher, setIsTeacher] = useState(true);
  const [isFaved, setIsFaved] = useState(false);
  const [pageQty, setPageQty] = useState(1);
  const [page, setPage] = useState(1);

  const navigate = useNavigate();

  useEffect(() => {
    if (currentUser) {
      if (Number(currentUser.id) === Number(id) && !currentUser.teacher) {
        setIsTeacher(false);
      }
    }
  }, [currentUser])

  useEffect(async () => {
    setCurrentUser(AuthService.getCurrentUser());
    const userRes = await userService.getUserInfo(id);
    const userData = handleService(userRes, navigate);
    setUser(userData);
    const res = await favouritesService.checkIfTeacherIsFaved(id);
    const data = handleService(res, navigate);
    if(data) setIsFaved(true);
  }, [id])
  
  useEffect(async () => {
    if (user) {
      const res = await userService.getUserImg(user.id);
      const data = handleService(res, navigate);
      if (data) setImage('data:image/png;base64,' + data.image);
      
      if (isTeacher) {
        const res = await userService.getUserSubjects(user.id);
        const data = await handleService(res, navigate);
        data.map((item) => {
          item.levels.forEach((level, index) => {
            setSubjects((prev) => [...prev, {
              name: item.subject,
              price: '$' + item.prices[index] + '/' + i18next.t('subjects.hour'),
              level: i18next.t('subjects.levels.' + level),
            }])
          })
        })

        const certificacionsRes = await filesService.getUserCertifications(user.id);
        setCertifications(handleService(certificacionsRes, navigate));
      }
      setLoading(false);
    }
  },[user])

  useEffect(async () => {
    if (user) {
      const res = await ratingService.getUserReviews(user.id, page);
      if (!res.failure) setPageQty((parseInt(res.headers['x-total-pages'])));
      setReviews(handleService(res, navigate));
    }
  }, [user, page])

  return {
    currentUser,
    isFaved,
    image,
    user,
    isTeacher,
    reviews,
    certifications,
    index,
    loading,
    subjects,
    pageQty,
    page, 
    setPage,
    setIndex,
    setIsFaved
  };
}