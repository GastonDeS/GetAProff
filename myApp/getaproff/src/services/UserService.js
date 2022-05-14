import {axiosService} from "./index";

const PATH = '/users'
const APPLICATION_V1_JSON_TYPE = 'application/vnd.getaproff.api.v1+json'

export class UserService {
    async getUserSubjects(uid) {
        try {
            let subjects = []
            let config = {}
            await axiosService.authAxiosWrapper(axiosService.GET, `/users/${uid}/subjects`, config)
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
            await axiosService.authAxiosWrapper(axiosService.GET, `/users/available-subjects/${uid}`, config)
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
            await axiosService.authAxiosWrapper(axiosService.POST, `/users/${uid}`, config, {
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
                    await axiosService.authAxiosWrapper(axiosService.DELETE, "/users/" + uid + subject.url, {});
                }
            }
        } catch(error) {console.log(error)};
    }

    async getUserSpecificInfo(info, uid) {
        try {
            let data;
            let config = {}
            await axiosService.authAxiosWrapper(axiosService.GET, `/${info}/${uid}`, config)
                .then(res => {
                    data = res.data
                })
            return data;
        }
        catch (err) {
            console.log(err);
        }
    }

    async getUserReviews (uid) {
      return this.getUserSpecificInfo("ratings", uid)
    }

    async getUserCertifications (uid) {
       return this.getUserSpecificInfo("user-files", uid)
    }

    async addCertification (uid, form) {
        try {
            await axiosService.authAxiosWrapper(axiosService.POST, `/user-files/${uid}`, {}, form);
            return;
        } catch (err) {
            console.log(err);
        }
    }

    async removeCertification (fileId) {
        try {
            await axiosService.authAxiosWrapper(axiosService.DELETE, `/user-files/${fileId}`, {});
            return;
        } catch (err) {
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
            await axiosService.authAxiosWrapper(axiosService.POST, `/users/${uid}/image`, {}, imgData);
        } catch (err) {
            console.log(err);
        }
    }

    async getUserInfo(uid) {
        try {
            let data;
            let config = {}
            await axiosService.authAxiosWrapper(axiosService.GET,`${PATH}/${uid}`, config)
                .then(res => data = res.data)
            return data;
        } catch (err) {
            console.log(err);
        }
    }

    async checkIfTeacherIsFaved(uid, teacherId) {
        try {
            let retVal = false
            let config = {}
            const res = await axiosService.authAxiosWrapper(axiosService.GET, `${PATH}/${uid}/favorites`, config )
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
            let config = {}
            let data = {'id' : teacherId}
            await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}/${uid}/favorites`, config, data )
        } catch (err) {
            console.log(err);
        }
    }

    async getFavoriteTeachers(uid, page) {
        try {
            let response = {}
            let config = {
                params: {
                    page: page,
                    pageSize: 5,
                }}
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


    async requestClass(uid, requestData) {
        try {
            // let response;
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
            await axiosService.axiosWrapper(axiosService.GET, '/users', config)
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

