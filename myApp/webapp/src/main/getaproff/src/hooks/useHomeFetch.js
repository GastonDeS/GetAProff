import { useState, useEffect } from 'react'
import axios from 'axios'

export const useHomeFetch = () => {
  const [topRated, setTopRated] = useState([]);
  const [mostRequested, setMostRequested] = useState([]);

  const fetchTopRated = () => {
    axios.get('/teachers/top-rated').then(res => {
      res.data.map(item => {
        setTopRated(previous => [...previous, item])
      })
    });
  }

  const fetchMostRequested = () => {
    axios.get('/teachers/most-requested').then(res => {
      res.data.map(item => {
        setMostRequested(previous => [...previous, item])
      })
    });
  }

  useEffect(() => {
    fetchTopRated();
    fetchMostRequested();
  }, []);

  return { topRated,  mostRequested };
};