import axios from 'axios'

export default axios.create({
    baseURL: process.env.REACT_APP_API_BASEURL,
    timeout: 30000,
})