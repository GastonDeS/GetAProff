import { status } from "../assets/constants";

export const handleService = (response, navigate, defaultValue=undefined) => {
  if (response) {
    if (response.failure) {
      if (response.status === status.NO_CONTENT) {
        return defaultValue;
      } else {
        if (response.status === status.UNAUTHORIZED) {
          navigate(`/users/login?code=${status.UNAUTHORIZED}`);
        } else if (response.status === status.PAGE_NOT_FOUND) {
          navigate("/404");
        } else {
          navigate(`/error?code=${response.status}`);
        }
      }
    } else {
      return response.data;
    }
  }
}