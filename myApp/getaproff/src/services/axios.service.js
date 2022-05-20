 // import axios from "../api"
import axios from "axios";
import {APPLICATION_V1_JSON_TYPE} from "../assets/constants";

export class AxiosService {

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
        const aux = + "api/" + path
        switch (action) {
            case this.GET:
                return await axios.get(aux, config);
            case this.PUT:
                return await axios.put(aux, data, config)
            case this.POST:
                config.headers['Content-Type'] =  APPLICATION_V1_JSON_TYPE
                return await axios.post(aux, data, config);
            case this.DELETE:
                return await axios.delete(aux, config);
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