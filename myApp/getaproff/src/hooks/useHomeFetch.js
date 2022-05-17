import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { userService, subjectService } from '../services';

export const useHomeFetch = () => {
  const [topRated, setTopRated] = useState();
  const [mostRequested, setMostRequested] = useState();
  const [subjects, setSubjects] = useState();
  const navigate = useNavigate();

  const fetchTopRated = async () => {
    await userService.getHomeTeachers('top-rated')
      .then(res => {
          setTopRated([...res.data])
      });
  }

  const fetchMostRequested = async () => {
    await userService.getHomeTeachers('most-requested')
      .then(res => {
        setMostRequested([...res.data])
      });
  }

  const fetchSubjects = () => {
    subjectService.getMostRequestedSubjects(navigate, setSubjects);
  }

  useEffect(() => {
    fetchTopRated();
    fetchMostRequested();
    fetchSubjects();
  }, []);

  return { topRated,  mostRequested, subjects };
};