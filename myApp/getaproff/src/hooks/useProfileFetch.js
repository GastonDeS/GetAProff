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
    await userService.getUserInfo(id)
      .then(data => {{
        setUser(data);
      }});
    await favouritesService.checkIfTeacherIsFaved(id)
      .then(res => {
        if(res) setIsFaved(true);
      })
  }, [id])
  
  useEffect(async () => {
    if (user) {
      await userService.getUserImg(user.id).then(img => {
        if(img) {
          setImage(img);
        }
      });
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

        await filesService.getUserCertifications(user.id)
            .then(data => setCertifications(data));
      }
      setLoading(false);
    }
  },[user])

  useEffect(async () => {
    if (user) {
      await ratingService.getUserReviews(user.id, page)
            .then(res => {
              setPageQty((parseInt(res.headers['x-total-pages'])));
              setReviews(res.data);
            });
    }
  }, [user, page]);

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