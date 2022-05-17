import { status } from "../assets/constants";

export const handleService = (response, navigate) => {
  if (response.failure) {
    if (response.status === status.NO_CONTENT) {
      return undefined;
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