import { status } from "../assets/constants";

export const handleResponse = (response) => {
  if (response) {
    if (response.status >= status.OK && 
      response.status <= status.SUCCESS_LIMIT &&
      response.status !== status.NO_CONTENT) {
      return {
          headers: response.headers,
          status: response.status,
          failure: false,
          data: response.data
      }
    }
    return {
      headers: response.headers,
      status: response.status,
      failure: true
    };
  }
}
