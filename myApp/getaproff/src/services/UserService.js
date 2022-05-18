import {axiosService} from "./index";
import { paths, APPLICATION_V1_JSON_TYPE } from "../assets/constants";
import { handleResponse } from "../handlers/responseHandler";

const PATH = paths.USERS

export class UserService {

    //Get most rated and most requested teachers
    //UsersController
    async getHomeTeachers(type) {
        try {
            const res = await axiosService.axiosWrapper(axiosService.GET, `${PATH}/${type}`, {});
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    // Get subjects being taugh by teacher
    //UsersController
    async getUserSubjects(uid) {
        try {
            const res = await axiosService.axiosWrapper(axiosService.GET, `${PATH}/${uid}/subjects`, {});
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    // Get subjects not being taugh by teacher
    //UsersController
    async getUserAvailableSubjects(uid) {
        try {
            const res = await axiosService.authAxiosWrapper(axiosService.GET, `${PATH}/available-subjects/${uid}`, {});
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    // Add new subject to teacher
    //UsersController
    async addSubjectToUser(uid, subjecId, price, level) {
        try {
            let config = {
                headers:  {'Content-Type' : APPLICATION_V1_JSON_TYPE}
            }
            const res = await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}/${uid}`, config, {
                subjectId: subjecId,
                price: price,
                level: level
            });
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    // Delete subject taugh by teacher
    //UsersController
    async deleteSubjectsFromUser(uid, subject) {
        try {
            const res = await axiosService.authAxiosWrapper(axiosService.DELETE, `${PATH}/${uid}` + subject.url, {});
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    // Get user's image
    //UsersController
    async getUserImg(uid) {
        try {
            const res = await axiosService.axiosWrapper(axiosService.GET, `${PATH}/${uid}/image`, {});
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    //Add image to user
    //UsersController
    async addUserImg (uid, imgData) {
        try {
            const res = await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}/${uid}/image`, {}, imgData);
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    // Get user
    //UsersController
    async getUserInfo(uid) {
        try {
            const res = await axiosService.authAxiosWrapper(axiosService.GET,`${PATH}/${uid}`, {});
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    // Register students and teachers
    //UsersController
    async register(formData) {
        try {
          let config = {
            headers:  {'Content-Type' : APPLICATION_V1_JSON_TYPE}
          }
          var url = PATH + '/student';
          if (formData.role === 1) {
            url = PATH + '/teacher';
          }
          const res = await axiosService.axiosWrapper(axiosService.POST, url, config, formData);
          return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    // Edit students and teachers' profile
    //UsersController
    async editProfile (uid, role, form) {
        try {
            let config = {
                headers:  {'Content-Type' : APPLICATION_V1_JSON_TYPE}
            }
            const res =  await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}/${uid}/${role}`, config, form);
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    // Filter teachers by params
    //UsersController
    async getUsers(queryParams, page) {
        try {
            let config = {};
            config['params'] = {
                maxPrice : queryParams.maxPrice,
                level : parseInt(queryParams.level),
                rating : parseInt(queryParams.rating),
                search: queryParams.search,
                page: page,
                pageSize: 9
            };
            const res = await axiosService.axiosWrapper(axiosService.GET, `${PATH}`, config)
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }
}

