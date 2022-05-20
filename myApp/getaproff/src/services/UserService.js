import {axiosService} from "./index";
import { paths, APPLICATION_V1_JSON_TYPE } from "../assets/constants";
import { handleResponse } from "../handlers/responseHandler";
import authService from "./authService";

const PATH = paths.USERS

export class UserService {

    //Get most rated and most requested teachers
    async getHomeTeachers(type) {
        try {
            const res = await axiosService.axiosWrapper(axiosService.GET, `${PATH}/${type}`, {});
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    // Get subjects being taught by teacher
    async getUserSubjects(uid) {
        try {
            const res = await axiosService.axiosWrapper(axiosService.GET, `${PATH}/${uid}/subjects`, {});
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    // Get subjects not being taught by teacher
    async getUserAvailableSubjects(uid) {
        try {
            const res = await axiosService.authAxiosWrapper(axiosService.GET, `${PATH}/available-subjects/${uid}`, {});
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    // Add new subject to teacher
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
    async deleteSubjectsFromUser(uid, subject) {
        try {
            const res = await axiosService.authAxiosWrapper(axiosService.DELETE, `${PATH}/${uid}` + subject.url, {});
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    // Get user's image
    async getUserImg(uid) {
        try {
            const res = await axiosService.axiosWrapper(axiosService.GET, `${PATH}/${uid}/image`, {});
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    //Add image to user
    async addUserImg (uid, imgData) {
        try {
            const res = await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}/${uid}/image`, {}, imgData);
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    // Get user
    async getUserInfo(uid) {
        try {
            const res = await axiosService.authAxiosWrapper(axiosService.GET,`${PATH}/${uid}`, {});
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    // Register students and teachers
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

    async getMostExpensiveUserTeaching(search) {
        try {
            const config = {
                params : {
                    forSearch: search
                }
            }
            const res = await axiosService.axiosWrapper(axiosService.GET, `${PATH}/maxPrice`, config);
            return res;
        }
        catch (err) { return handleResponse(err.response)}
    }


    // Edit students and teachers' profile
    async editProfile (uid, role, form) {
        try {
            let config = {
                headers:  {'Content-Type' : APPLICATION_V1_JSON_TYPE}
            }
            await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}/${uid}/${role}`, config, form);
            await authService.refreshAuthedUser();
        } catch (error) {return handleResponse(error.response)}
    }

    // Filter teachers by params
    async getUsers(queryParams, page) {
        try {
            let config = {};
            config.params = {}
            config.params.maxPrice = parseInt(queryParams.maxPrice ?? 1000000);
            config.params.level = parseInt(queryParams.level ?? '0') ;
            config.params.rating = parseInt(queryParams.rating?? '0') ;
            config.params.search = queryParams.search;
            config.params.order = parseInt(queryParams.order ?? '1') ;
            config.params.page = page;
            config.params.pageSize = 9;

            const res = await axiosService.axiosWrapper(axiosService.GET, `${PATH}`, config)
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }
}

