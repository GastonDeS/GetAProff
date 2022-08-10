import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { userService, subjectService } from '../services';
import { handleService } from '../handlers/serviceHandler';


export const useHomeFetch = () => {
  const [topRated, setTopRated] = useState();
  const [mostRequested, setMostRequested] = useState();
  const [subjects, setSubjects] = useState();
  const navigate = useNavigate();

  const fetchTopRated = async () => {
    const res = await userService.getHomeTeachers('top-rated');
    setTopRated(handleService(res, navigate));
  }

  const fetchMostRequested = async () => {
    const res = await userService.getHomeTeachers('most-requested');
    setMostRequested(handleService(res, navigate));
  }

  const fetchSubjects = async () => {
    const res = await subjectService.getMostRequestedSubjects();
    setSubjects(handleService(res, navigate));
  }

  useEffect(() => {
    fetchTopRated();
    fetchMostRequested();
    fetchSubjects();
  }, []);

  return { topRated,  mostRequested, subjects };
};