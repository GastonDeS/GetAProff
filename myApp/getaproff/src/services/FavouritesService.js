import { axiosService } from ".";
import { paths, APPLICATION_V1_JSON_TYPE } from "../assets/constants";

const PATH = paths.FAVOURITES

export class FavouritesService {

  //FavouritesController
  async checkIfTeacherIsFaved(teacherId) {
    try {
        const res = await axiosService.authAxiosWrapper(axiosService.GET, `${PATH}/${teacherId}`, {});
        return res.data;
    } catch (err) {
        console.log(err);
    }
  }

  //FavouritesController
  async removeTeacherFromFavorites(teacherId) {
      try {
          await axiosService.authAxiosWrapper(axiosService.DELETE, `${PATH}/${teacherId}`, {})
      } catch (err) {
          console.log(err)
      }
  }

  //FavouritesController
  async addTeacherToFavorites(teacherId) {
      try {
          await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}/${teacherId}`, {});
      } catch (err) {
          console.log(err);
      }
  }

  //FavouritesController
  async getFavoriteTeachers(page) {
      try {
          let response = {}
          let config = {};
          config['params'] = {
              page: page,
              pageSize: 5,
          };
          await axiosService.authAxiosWrapper(axiosService.GET, `${PATH}`, config)
              .then(res => {
                  response['data'] = res.data
                  response['pageQty'] =(parseInt(res.headers['x-total-pages']))
              })
          return response;
      } catch (err) {
          console.log(err);
      }
  }
}