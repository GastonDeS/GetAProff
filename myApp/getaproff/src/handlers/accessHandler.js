import authService from "../services/authService";
import { status } from "../assets/constants";
import {classStatus} from "../assets/constants";

export const handleAuthentication = (navigate) => {
  if (!authService.getCurrentUser()) navigate(`/users/login?code=${status.UNAUTHORIZED}`);
}

export const handleTeacherRole = (navigate) => {
  const user = authService.getCurrentUser();
  if (!user) {
    navigate(`/users/login?code=${status.UNAUTHORIZED}`);
    return;
  }
  if (!user.teacher) {navigate(`/error?code=${status.UNAUTHORIZED}`)};
}

export const handleTeacherAndIdentity = (id, navigate) => {
  const user = authService.getCurrentUser();
  if (!user) {
    navigate(`/users/login?code=${status.UNAUTHORIZED}`);
    return;
  }
  if (!user.teacher || user.id !== parseInt(id)) {navigate(`/error?code=${status.UNAUTHORIZED}`)};
}

export const handleIdentity = (id, navigate) => {
  const user = authService.getCurrentUser();
  if (!user) {
    navigate(`/users/login?code=${status.UNAUTHORIZED}`);
    return;
  }
  if (user.id !== parseInt(id)) {navigate(`/error?code=${status.UNAUTHORIZED}`)};
}

export const handleClassroomStatus = (classroomStatus ,navigate) => {
  if (classroomStatus === classStatus.DECLINED || classroomStatus === classStatus.CANCELLEDT || classroomStatus === classStatus.CANCELLEDS) {
    navigate(`/error?code=${status.UNAUTHORIZED}`);
  }
}