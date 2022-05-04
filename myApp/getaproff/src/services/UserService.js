
import axios from "axios";
import i18next from "i18next";
const PATH = '/users'


export class UserService {
    async getUserSubjects(uid){
        try {
            let subjects = []
            await axios.get(`/users/${uid}/subjects`)
                .then(res => {
                    subjects = res.data
                    })
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
            const res = await axios.get(`${PATH}/${uid}/favorites`);
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

    async getFavoriteTeachers(uid) {
        try {
            let favoritesList = []
            await axios.get(`${PATH}/${uid}/favorites`)
                .then(res => {
                    if (res.data && res.data.length !== 0)
                        favoritesList = res.data;

                })
            return favoritesList;
        } catch (err) {
            console.log(err);
        }
    }



    async requestClass(uid, requestData) {
        try {
            let response;
            let priceAndLevelIdx = parseInt(requestData.levelIdx);
            await axios.post(`${PATH}/${uid}/classes`, {
                studentId: requestData.studentId,
                subjectId: requestData.subject.subjectId,
                level: requestData.subject.levels[priceAndLevelIdx],
                price: requestData.subject.prices[priceAndLevelIdx],
            })
                .then(res => response = res);
            return response;
        }
        catch (err) {
            console.log(err)
        }
    }

    async getUserClasses(uid, asTeacher, status) {
        try {
            let params = {
                status : status,
                asTeacher: asTeacher,
                userId: uid
            };
            return await axios.get('/classes', {
                   params
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
                rating: data.rating,
                review: data.review
            };
            return await axios.post(`${PATH}/${uid}/reviews`, params);
        }
        catch (err) {
            console.log(err);
        }
    }
}

