import {axiosService} from "./index";
import { paths, APPLICATION_V1_JSON_TYPE } from "../assets/constants";

const PATH = paths.USERS

export class UserService {

    //Get most rated and most requested teachers
    //UsersController
    async getHomeTeachers(type) {
        try {
            return await axiosService.axiosWrapper(axiosService.GET, `${PATH}/${type}`, {});
        }  catch (err) { console.log(err) }
    }

    // Get subjects being taugh by teacher
    //UsersController
    async getUserSubjects(uid) {
        try {
            let subjects = []
            await axiosService.authAxiosWrapper(axiosService.GET, `${PATH}/${uid}/subjects`, {})
                .then(res => {
                    res.data.forEach(subject => subjects.push(subject));
                    return subjects;
                })
            return subjects;
        }
        catch (err) { console.log(err) }
    }

    // Get subjects not being taugh by teacher
    //UsersController
    async getUserAvailableSubjects(uid) {
        try {
            let subjects = []
            let config = {}
            await axiosService.authAxiosWrapper(axiosService.GET, `${PATH}/available-subjects/${uid}`, config)
                .then(res => {
                    res.data.forEach(subject => subjects.push(subject));
                    return subjects;
                })
            return subjects;
        }
        catch (err) { console.log(err) }
    }

    // Add new subject to teacher
    //UsersController
    async addSubjectToUser(uid, subjecId, price, level) {
        try {
            let config = {
                headers:  {'Content-Type' : APPLICATION_V1_JSON_TYPE}
            }
            await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}/${uid}`, config, {
                subjectId: subjecId,
                price: price,
                level: level
            });
        } catch(error) {console.log(error)};
    }

    // Delete subject taugh by teacher
    //UsersController
    async deleteSubjectsFromUser(uid, subjects) {
        try {
            for (const subject of subjects) {
                if (subject.checked) {
                    await axiosService.authAxiosWrapper(axiosService.DELETE, `${PATH}/${uid}` + subject.url, {});
                }
            }
        } catch(error) {console.log(error)};
    }

    // Get user's image
    //UsersController
    async getUserImg(uid) {
        try {
            let image;
            let  config = {}
            await axiosService.authAxiosWrapper(axiosService.GET, `${PATH}/${uid}/image`, config)
                .then(
                    res => {
                        if (res.status === 200) {
                            image = 'data:image/png;base64,' + res.data.image;
                        }
                    })
            return image;
        }
        catch (err) {
            console.log(err);
        }
    }

    //Add image to user
    //UsersController
    async addUserImg (uid, imgData) {
        try {
            await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}/${uid}/image`, {}, imgData);
        } catch (err) {
            console.log(err);
        }
    }

    // Get user
    //UsersController
    async getUserInfo(uid) {
        try {
            let data;
            await axiosService.authAxiosWrapper(axiosService.GET,`${PATH}/${uid}`, {})
                .then(res => data = res.data)
            return data;
        } catch (err) {
            console.log(err);
        }
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
          await axiosService.axiosWrapper(axiosService.POST, url, config, formData)
          .then( (res) => {
            localStorage.setItem('token', res.headers.authorization);
            localStorage.setItem('user', JSON.stringify(res.data));
          });
        } catch (err) {
          console.log(err);
        }
    }

    // Edit students and teachers' profile
    //UsersController
    async editProfile (uid, role, form) {
        try {
            let config = {
                headers:  {'Content-Type' : APPLICATION_V1_JSON_TYPE}
            }
            return await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}/${uid}/${role}`, config, form);
        } catch (err) {
            console.log(err);
        }
    }

    // Filter teachers by params
    //UsersController
    async getUsers(queryParams, page) {
        try {
            let config = {};
            let response;
            config['params'] = {
                maxPrice : queryParams.maxPrice,
                level : parseInt(queryParams.level),
                rating : parseInt(queryParams.rating),
                search: queryParams.search,
                page: page,
                pageSize: 9
            };
            await axiosService.axiosWrapper(axiosService.GET, `${PATH}`, config)
                .then(res => response = res)
            return response;
        }
        catch (err) {
            console.log(err)
        }

    }

    //ClassesController
    // Get user's classes filtered by params
    async getUserClasses(uid, asTeacher, status, page) {
        try {
            let config = {}
            config['params'] = {
                status: status,
                asTeacher: asTeacher,
                userId: uid,
                page: page,
                pageSize: 5
            }
            let response;
            await axiosService.authAxiosWrapper(axiosService.GET, '/classes', config)
                .then(r => response = r)
            return response
        }
        catch(err) {
            console.log(err);
        }
    }
}

