import { useState, useEffect } from 'react'
import axios from 'axios'

export const useHomeFetch = () => {
  const [topRated, setTopRated] = useState([]);
  const [mostRequested, setMostRequested] = useState([]);

  const fetchTopRated = () => {
    axios.get('/teachers/top-rated')
      .then(res => {
        res.data.forEach(item => {
          setTopRated(previous => [...previous, item])
        })
      })
      .catch(error => {});
  }

  const fetchMostRequested = () => {
    axios.get('/teachers/most-requested')
      .then(res => {
        res.data.forEach(item => {
          setMostRequested(previous => [...previous, item])
        })
      })
      .catch(error => {});;
  }

  useEffect(() => {
    fetchTopRated();
    fetchMostRequested();
  }, []);

  return { topRated,  mostRequested };
};