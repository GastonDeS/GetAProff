import { useEffect, useState } from "react";
import i18next from "i18next";
import AuthService from "../services/authService";
import { userService } from "../services";
import { useNavigate } from "react-router-dom";
import { handleService } from "../handlers/serviceHandler";

export const useEditSubjectsFetch = () => {
  const [subject, setSubject] = useState();
  const [price, setPrice] = useState("");
  const [level, setLevel] = useState();
  const [availableSubjects, setAvailableSubjects] = useState([]);
  const [subjectsTaught, setSubjectsTaught] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);
  const [checkAll, setCheckAll] = useState(false);
  const [currentUser, setCurrentUser] = useState();

  const navigate = useNavigate();


  const fetchSubjectsTaught = async () => {
    const res = await userService.getUserSubjects(currentUser.id);
    const data = await handleService(res, navigate);
    data.map((item) => {
      item.levels.forEach((level, index) => {
        setSubjectsTaught((prev) => [...prev, { 
          name: item.subject,
          price: '$' + item.prices[index] + '/' + i18next.t('subjects.hour'),
          level: i18next.t('subjects.levels.' + level),
          url: '/' + item.id + '/' + level,
          checked: false
        }])
      })
    });
  }

  const fetchAvailableSubjects = async () => {
    const res = await userService.getUserAvailableSubjects(currentUser.id);
    const data = handleService(res, navigate);
    setAvailableSubjects(data.map((item, index) => {
      var levels = []
      item.levels.forEach(level => {
        levels.push({
          id: level,
          name: i18next.t('subjects.levels.' + level)
        })
      })
      var ans = {
        name: item.name,
        id: item.subjectId,
        levels: levels
      }
      if (index === 0) {
        setSubject(ans);
        setLevel(levels[0]);
        setLoading(false);
      }
      return ans;
    }))
  }

  useEffect(() => {
    subject && setLevel(subject.levels[0]);
  }, [subject])

  useEffect(() => {
    if (loading && currentUser) {
      fetchAvailableSubjects();
      fetchSubjectsTaught();
    }
  }, [loading, currentUser])

  useEffect(() => {
    if (subjectsTaught && subjectsTaught.length === 0) setCheckAll(false);
  }, [subjectsTaught])

  useEffect(() => {
    setCurrentUser(AuthService.getCurrentUser());
  }, [])

  return {
    error,
    loading,
    availableSubjects,
    price,
    subjectsTaught,
    level,
    subject,
    checkAll,
    currentUser,
    setPrice,
    setError,
    setSubjectsTaught,
    setLoading,
    setCheckAll,
    setLevel,
    setSubject
  };
};