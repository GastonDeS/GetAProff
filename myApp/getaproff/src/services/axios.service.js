import axios from "axios"


export class AxiosService {

    //Constantes para el wrapper
    GET = 0;
    PUT = 1
    POST = 2
    DELETE = 3

     async axiosWrapper(action, path, options, headers) {
        switch (action) {
            case this.GET:
                return await axios.get(path, {headers: headers});
            case this.PUT:
                return await axios.put(path, {headers: headers})
            case this.POST:
                return await axios.post(path, options, {headers: headers});
            case this.DELETE:
                return await axios.delete(path, {headers: headers});
            default:
                break;
        }
    }

    getBearerToken() {
        return "Bearer " + localStorage.getItem("token");
    }


    getBasicToken(mail, password) {
        const credentials = mail + ":" + password;
        const hash = btoa(credentials);
        return "Basic " + hash;
    }
}