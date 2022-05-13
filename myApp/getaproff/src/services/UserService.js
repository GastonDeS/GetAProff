import {axiosService} from "./index";

const PATH = '/users'


export class UserService {
    async getUserSubjects(uid){
        try {
            let subjects = []
            let config = {}
            await axiosService.axiosWrapper(axiosService.GET, `/users/${uid}/subjects`, config)
                .then(res => res.data.forEach(subject => subjects.push(subject)))
            return subjects;
        }
        catch (err) { console.log(err) }
    }

    async getUserSpecificInfo(info, uid){
        try {
            let data;
            let config = {}
            await axiosService.axiosWrapper(axiosService.GET, `/${info}/${uid}`, config)
                .then(res => {
                    data = res.data
                })
            return data;
        }
        catch (err) {
            console.log(err);
        }
    }

    async getUserReviews(uid) {
      return this.getUserSpecificInfo("ratings", uid)
    }
    async getUserCertifications(uid) {
       return this.getUserSpecificInfo("user-files", uid)
    }
    
    async getUserImg(uid) {
        try {
            let image = "";
            let  config = {}
            await axiosService.axiosWrapper(axiosService.GET, `${PATH}/${uid}/image`, config)
                .then(
                    res => {
                        image = 'data:image/png;base64,' + res.data.image;
                    })
            return image;
        }
        catch (err) {
            console.log(err);
        }
    }

    async getUserInfo(uid) {
        try {
            let data;
            let config = {}
            await axiosService.axiosWrapper(axiosService.GET,`${PATH}/${uid}`, config)
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
            const res = await axiosService.axiosWrapper(axiosService.GET, `${PATH}/${uid}/favorites`, config )
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
            await axiosService.axiosWrapper(axiosService.DELETE, `${PATH}/${uid}/favorites/${teacherId}`, config )
        } catch (err) {
            console.log(err)
        }

    }

    async addTeacherToFavorites(teacherId, uid) {
        try {
            let config = {}
            let data = {'id' : teacherId}
            await axiosService.axiosWrapper(axiosService.POST, `${PATH}/${uid}/favorites`, config, data )
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
            await axiosService.axiosWrapper(axiosService.GET, `${PATH}/${uid}/favorites`, config)
                .then(res => {
                    response['data'] = res.data
                    response['pageQty'] =(parseInt(res.headers['x-total-pages']) + 1)
                })
            return response;
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
            await axiosService.axiosWrapper(axiosService.POST, `${PATH}/${uid}/classes`, {
                studentId: requestData.studentId,
                subjectId: requestData.subject.id,
                level: requestData.level,
                price: requestData.subject.prices[priceIdx],
            }, {Authorization: axiosService.getBearerToken()})
            // await axios.post(`${PATH}/${uid}/classes`, {
            //     studentId: requestData.studentId,
            //     subjectId: requestData.subject.id,
            //     level: requestData.level,
            //     price: requestData.subject.prices[priceIdx],
            // })
                .then(res => response = res);
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
            await axiosService.axiosWrapper(axiosService.GET, '/classes', config)
                .then(r => response = r)
            return response
        }
        catch(err) {
            console.log(err);
        }
    }

    async createReview(uid, teacherId, data) {
        try {
            let params = {
                studentId: uid,
                teacherId: parseInt(teacherId),
                rate: parseFloat(data.rating),
                review: data.review
            };
            return await axiosService.axiosWrapper(axiosService.POST, `${PATH}/${uid}/reviews`, params);
        }
        catch (err) {
            console.log(err);
        }
    }
}

