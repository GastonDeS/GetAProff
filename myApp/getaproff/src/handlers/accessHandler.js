import authService from "../services/authService";
import { status } from "../assets/constants";

export const handleAuthentication = (navigate) => {
  if (!authService.getCurrentUser()) navigate(`/users/login?code=${status.UNAUTHORIZED}`);
}

export const handleTeacherRole = (navigate) => {
  const user = authService.getCurrentUser();
  if (!user) {
    navigate(`/users/login?code=${status.UNAUTHORIZED}`);
    return;
  }
  if (!user.teacher)navigate(`/error?code=${status.UNAUTHORIZED}`);
}