import { useState, useEffect } from 'react'
import axios from 'axios'

export const useHomeFetch = () => {
  const [topRated, setTopRated] = useState([]);
  const [mostRequested, setMostRequested] = useState([]);
  const [subjects, setSubjects] = useState([]);

  const fetchTopRated = () => {
    axios.get('/users/top-rated')
      .then(res => {
        res.data.forEach(item => {
          setTopRated(previous => [...previous, item])
        })
      })
      .catch(error => {});
  }

  const fetchMostRequested = () => {
    axios.get('/users/most-requested')
      .then(res => {
        res.data.forEach(item => {
          setMostRequested(previous => [...previous, item])
        })
      })
      .catch(error => {});
  }

  const fetchSubjects = () => {
    axios.get('/subjects/most-requested')
      .then(res => {
        res.data.forEach(item => {
          setSubjects(previous => [...previous, item])
        })
      })
      .catch(error => {});
  }

  useEffect(() => {
    fetchTopRated();
    fetchMostRequested();
    fetchSubjects();
  }, []);

  return { topRated,  mostRequested, subjects };
};