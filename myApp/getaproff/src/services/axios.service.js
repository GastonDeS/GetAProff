import axios from "axios"


export class AxiosService {

    //Constantes para el wrapper
    GET = 0;
    PUT = 1
    POST = 2
    DELETE = 3


    async authAxiosWrapper(action, path, config, data = {}) {
        if(!config.hasOwnProperty('headers'))
            config.headers = {}
        if (!config.headers.hasOwnProperty('Authorization')) {
            config.headers['Authorization'] = this.getBearerToken()
        }
        return await this.axiosWrapper(action, path, config, data)
    }

     async axiosWrapper(action, path, config, data = {}) {
        switch (action) {
            case this.GET:
                return await axios.get(path, config);
            case this.PUT:
                return await axios.put(path, data, config)
            case this.POST:
                return await axios.post(path, data, config);
            case this.DELETE:
                return await axios.delete(path, config);
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