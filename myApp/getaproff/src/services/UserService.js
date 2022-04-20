
import axios from "axios";
import i18next from "i18next";
const PATH = '/users'


export class UserService {

    // async f() {
    //     if (isTeacher) {
    //         //TODO: esto seria users/id/subjects y me retorna lista con ref a subjects
    //         axios.get("/users/subjects/" + user.id).then(res => {
    //             res.data.forEach(item => {
    //                 setSubjects([...subjects, {
    //                     first: item.subject,
    //                     second: '$' + item.price + '/' + i18next.t('subjects.hour'),
    //                     third: i18next.t('subjects.levels.' + item.level)
    //                 }]);
    //             });
    //         });
    //         axios.get("/ratings/" + user.id).then(res => setReviews(res.data));
    //         axios.get("/user-files/" + user.id).then(res => setCertifications(res.data));
    //     }
    // }

    async getUserSubjects(uid){
        try {
            let subjects = []
            await axios.get(`/users/subjects/${uid}`)
                .then(res => {
                    res.data.forEach(item => {
                        subjects.push( {
                            first: item.subject,
                            second: '$' + item.price + '/' + i18next.t('subjects.hour'),
                            third: i18next.t('subjects.levels.' + item.level)
                        });
                    });
            });
            return subjects;
        }
        catch (err) { console.log(err) }
    }

    async getUserSpecificInfo(info, uid){
        try {
            let data;
            await axios.get(`/${info}/${uid}`)
                .then(res => {
                    console.log(res.data);
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
                    console.log(res);
                    if (res.data && res.data.length !== 0)
                        favoritesList = res.data;

                })
            return favoritesList;
        } catch (err) {
            console.log(err);
        }
    }
}

