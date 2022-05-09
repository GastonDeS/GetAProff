import axios from "axios"

export const GET = 0
export const PUT = 1
export const POST = 2
export const DELETE = 3



export const axiosWrapper = async (action, path, options, headers) => {
    switch (action) {
        case GET:
            return await axios.get(path, {headers: headers});
            break;
        case PUT:
            return await axios.put(path, {headers: headers})
            break;
        case POST:
            return await axios.post(path, options,{headers: headers});
            break;
        case DELETE:
            return await axios.delete(path, {headers: headers});
            break;
        default:
            break;
    }
}

export const getBearerToken = () => {
    return "Bearer " + localStorage.getItem("token");
}

export const getBasicToken = (mail, password) => {
    const credentials = mail+":"+password;
    const hash = btoa(credentials);
    return "Basic "+hash;
}