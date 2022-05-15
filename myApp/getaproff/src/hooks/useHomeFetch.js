import { useState, useEffect } from 'react'
import axios from 'axios'
import { userService } from '../services';

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
    // axios.get('/users/most-requested')
    //   .then(res => {
    //       setMostRequested([...res.data])

    //   })
    //   .catch(error => {});
  }

  const fetchSubjects = () => {
    axios.get('/api/subjects/most-requested')
      .then(res => {
          setSubjects([...res.data])
      })
      .catch(error => {});
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