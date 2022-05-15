import { useState, useEffect } from 'react'
import axios from 'axios'
import { userService, subjectService } from '../services';

export const useHomeFetch = () => {
  const [topRated, setTopRated] = useState();
  const [mostRequested, setMostRequested] = useState();
  const [subjects, setSubjects] = useState();

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

  const fetchSubjects = async () => {
    await subjectService.getMostRequestedSubjects()
      .then(res => {
          setSubjects([...res.data])
      });
  }

  useEffect(() => {
    let abortController = new AbortController();
    fetchTopRated();
    fetchMostRequested();
    fetchSubjects();
    return () => {
      abortController.abort();
    }
  }, []);

  return { topRated,  mostRequested, subjects };
};