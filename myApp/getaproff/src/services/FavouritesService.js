import { axiosService } from ".";
import { paths } from "../assets/constants";
import { handleResponse } from "../handlers/responseHandler";

const PATH = paths.FAVOURITES

export class FavouritesService {

  async checkIfTeacherIsFaved(teacherId) {
    try {
        const res = await axiosService.authAxiosWrapper(axiosService.GET, `${PATH}/${teacherId}`, {});
        return handleResponse(res);
    } catch (error) {return handleResponse(error.response)}
  }

  async removeTeacherFromFavorites(teacherId) {
      try {
          const res = await axiosService.authAxiosWrapper(axiosService.DELETE, `${PATH}/${teacherId}`, {})
          return handleResponse(res);
      } catch (error) {return handleResponse(error.response)}
  }

  async addTeacherToFavorites(teacherId) {
      try {
          const res = await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}/${teacherId}`, {});
          return handleResponse(res);
      } catch (error) {return handleResponse(error.response)}
  }

  async getFavoriteTeachers(page) {
      try {
          let config = {};
          config['params'] = {
              page: page,
              pageSize: 5,
          };
          const res = await axiosService.authAxiosWrapper(axiosService.GET, `${PATH}`, config)
          return handleResponse(res);
      } catch (error) {return handleResponse(error.response)}
  }
}