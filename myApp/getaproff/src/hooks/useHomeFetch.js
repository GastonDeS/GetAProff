import { useState, useEffect } from 'react'
import axios from 'axios'

export const useHomeFetch = () => {
  const [topRated, setTopRated] = useState();
  const [mostRequested, setMostRequested] = useState();
  const [subjects, setSubjects] = useState();

  const fetchTopRated = () => {
    axios.get('/users/top-rated')
      .then(res => {
          setTopRated([...res.data])
      })
      .catch(error => {});
  }

  const fetchMostRequested = () => {
    axios.get('/users/most-requested')
      .then(res => {
          setMostRequested([...res.data])

      })
      .catch(error => {});
  }

  const fetchSubjects = () => {
    axios.get('/subjects/most-requested')
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