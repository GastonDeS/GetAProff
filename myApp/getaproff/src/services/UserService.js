
import axios from "axios";
import i18next from "i18next";
const PATH = '/users'


export class UserService {
    async getUserSubjects(uid){
        try {
            let subjects = []
            await axios.get(`/users/${uid}/subjects`)
                .then(res => res.data.forEach(subject => subjects.push(subject)))
            console.log(subjects)
            return subjects;
        }
        catch (err) { console.log(err) }
    }

    async getUserSpecificInfo(info, uid){
        try {
            let data;
            await axios.get(`/${info}/${uid}`)
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
            await axios.get(`${PATH}/${uid}/image`)
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
            await axios.get(`${PATH}/${uid}`)
                .then(res => data = res.data);
            return data;
        } catch (err) {
            console.log(err);
        }
    }

    async checkIfTeacherIsFaved(uid, teacherId) {
        try {
            let retVal = false
            const token = localStorage.getItem("token")
            const res = await axios.get(`${PATH}/${uid}/favorites`,
                {headers: { Authorization: "Bearer "+token }}
            );
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
            await axios.delete(`${PATH}/${uid}/favorites/${teacherId}`);
        } catch (err) {
            console.log(err)
        }

    }

    async addTeacherToFavorites(teacherId, uid) {
        try {
            await axios.post(`${PATH}/${uid}/favorites`, {
                'id': teacherId,
            })
        } catch (err) {
            console.log(err);
        }
    }

    async getFavoriteTeachers(uid, page) {
        try {
            let response = {}
            await axios.get(`${PATH}/${uid}/favorites`, {
                params: {
                    page: page,
                    pageSize: 5,
                }})
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
            let response;
            let level = parseInt(requestData.level)
            let priceIdx = requestData.subject.levels.indexOf(level)
            await axios.post(`${PATH}/${uid}/classes`, {
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

    async getUserClasses(uid, asTeacher, status, page) {
        try {
            let params = {
                status : status,
                asTeacher: asTeacher,
                userId: uid,
                page: page,
                pageSize: 5
            };
            const token = localStorage.getItem("token")
            const headers = { Authorization: "Bearer " +  token}
            return await axios.get('/classes', {
                   params,
                   headers: {Authorization: "Bearer " + token}
                })
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
            return await axios.post(`${PATH}/${uid}/reviews`, params);
        }
        catch (err) {
            console.log(err);
        }
    }
}

