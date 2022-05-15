import {axiosService} from "./index";

const PATH = '/users'
const APPLICATION_V1_JSON_TYPE = 'application/vnd.getaproff.api.v1+json'

export class UserService {
    async getHomeTeachers(type) {
        try {
            return await axiosService.axiosWrapper(axiosService.GET, `${PATH}/${type}`, {});
        }  catch (err) { console.log(err) }
    }

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

    async deleteSubjectsFromUser(uid, subjects) {
        try {
            for (const subject of subjects) {
                if (subject.checked) {
                    await axiosService.authAxiosWrapper(axiosService.DELETE, `${PATH}/${uid}` + subject.url, {});
                }
            }
        } catch(error) {console.log(error)};
    }

    async getUserReviews(uid) {
        try {
            let data;
            let config = {}
            await axiosService.authAxiosWrapper(axiosService.GET, `/ratings/${uid}`, config)
                .then(res => {
                    data = res.data
                })
            return data;
        }
        catch (err) {
            console.log(err);
        }
    }
    
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

    async addUserImg (uid, imgData) {
        try {
            await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}/${uid}/image`, {}, imgData);
        } catch (err) {
            console.log(err);
        }
    }

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

    async checkIfTeacherIsFaved(uid, teacherId) {
        try {
            let retVal = false;
            const res = await axiosService.authAxiosWrapper(axiosService.GET, `${PATH}/${uid}/favorites`, {})
            res.data && res.data.forEach(
                user => {
                    if (user.id === Number(teacherId)) {
                        retVal = true;
                    }
                }
            )
            return retVal;
        } catch (err) {
            console.log(err);
        }
    }

    async removeTeacherFromFavorites(teacherId, uid) {
        try {
            let config = {}
            await axiosService.authAxiosWrapper(axiosService.DELETE, `${PATH}/${uid}/favorites/${teacherId}`, config )
        } catch (err) {
            console.log(err)
        }
    }

    async addTeacherToFavorites(teacherId, uid) {
        try {
            let config = {
                headers:  {'Content-Type' : APPLICATION_V1_JSON_TYPE}
            };
            let data = {'id' : teacherId};
            await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}/${uid}/favorites`, config, data);
        } catch (err) {
            console.log(err);
        }
    }

    async getFavoriteTeachers(uid, page) {
        try {
            let response = {}
            let config = {};
            config['params'] = {
                page: page,
                pageSize: 5,
            };
            await axiosService.authAxiosWrapper(axiosService.GET, `${PATH}/${uid}/favorites`, config)
                .then(res => {
                    response['data'] = res.data
                    response['pageQty'] =(parseInt(res.headers['x-total-pages']))
                })
            return response;
        } catch (err) {
            console.log(err);
        }
    }

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

    async requestClass(uid, requestData) {
        try {
            let level = parseInt(requestData.level)
            let priceIdx = requestData.subject.levels.indexOf(level)
            let response;
            let config = {}
            await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}/${uid}/classes`,config, {
                studentId: requestData.studentId,
                subjectId: requestData.subject.id,
                level: requestData.level,
                price: requestData.subject.prices[priceIdx],
            })
                .then(res => response = res);
            return response;
        }
        catch (err) {
            console.log(err)
        }
    }

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

    async createReview(uid, teacherId, data) {
        try {
            let data = {
                studentId: uid,
                teacherId: parseInt(teacherId),
                rate: parseFloat(data.rating),
                review: data.review
            };
            let config = {}
            return await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}/${uid}/reviews`,config, data);
        }
        catch (err) {
            console.log(err);
        }
    }
}

