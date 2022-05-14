import { useEffect, useState } from "react";
import i18next from "i18next";
import AuthService from "../services/authService";
import { userService } from "../services";

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

  const fetchSubjectsTaught = async (id) => {
    await userService.getUserSubjects(id)
    .then(data => {
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
    });
  }

  const fetchAvailableSubjects = async (id) => {
    await userService.getUserAvailableSubjects(id)
    .then(data => {
      setAvailableSubjects(data.map((item) => {
        var levels = []
        item.levels.forEach(level => {
          levels.push({
            id: level,
            name: i18next.t('subjects.levels.' + level)
          })
        })
        return {
          name: item.name,
          id: item.subjectId,
          levels: levels
        }
      }))
    });
  }

  useEffect(() => {
    level && setLoading(false);
  }, [level])

  useEffect(() => {
    subject && setLevel(subject.levels[0]);
  }, [subject]);

  useEffect(() => {
    availableSubjects && setSubject(availableSubjects[0]);
  }, [availableSubjects]);

  useEffect(() => {
    if (loading && currentUser) {
      fetchAvailableSubjects(currentUser.id);
      fetchSubjectsTaught(currentUser.id);
    }
  }, [loading, currentUser]);

  useEffect(() => {
    if (subjectsTaught && subjectsTaught.length === 0) setCheckAll(false);
  }, [subjectsTaught]);

  useEffect(() => {
    setCurrentUser(AuthService.getCurrentUser());
  }, []);

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