import { useState, useEffect } from "react";
import i18next from "i18next";
import { filesService, userService } from '../services'
import AuthService from '../services/authService'
import { useNavigate } from "react-router-dom";
import { handleService } from "../handlers/serviceHandler";
import { handleTeacherRole } from "../handlers/accessHandler";

export const ALL_LEVELS = 4;
export const ALL_SUBJECTS = 0;

export const useMyFilesFetch = () => {
  const initialLevel = {
    name: i18next.t("subjects.all"),
    id: ALL_LEVELS,
  };
  const [show, setShow] = useState(false);
  const [subject, setSubject] = useState();
  const [level, setLevel] = useState();
  const [allFiles, setAllFiles] = useState([]);
  const [newFiles, setNewFiles] = useState([]);
  const [currentLevels, setCurrentLevels] = useState([]);
  const [currentSubjects, setCurrentSubjects] = useState([]);
  const [filteredFiles, setFilteredFiles] = useState([]);
  const [checkAll, setCheckAll] = useState(false);
  const [currentUser, setCurrentUser] = useState();
  const [reload, setReload] = useState(true);
  const allLevels = [0, 1, 2, 3]
  const initialState = {
    id: 0,
    name: i18next.t("subjects.all"),
    levels: allLevels,
  }

  const navigate = useNavigate();

  const changeLevels = (levels) => {
    var auxLevels = [];
    if (!show) auxLevels.push(initialLevel);
    levels.forEach((item, index) => {
      if (show && index === 0) setLevel(item);
      auxLevels.push({
          name: i18next.t("subjects.levels." + item),
          id: item,
      })
    });
    setCurrentLevels(auxLevels);
  };

  const filterFiles = () => {
    setCheckAll(false);
    allFiles.forEach((item) => {
      if (
        subject &&
        (Number(subject.id) === 0 ||
          Number(subject.id) === Number(item.subjectId))
      ) {
        if (
          Number(level) === ALL_LEVELS ||
          Number(level) === Number(item.levelId)
        ) {
          setFilteredFiles((previous) => [...previous, item]);
        }
      }
    });
  };

  const fetchFiles = async () => {
    const res = await filesService.getSubjectFiles();
    const data = handleService(res, navigate);
    data.forEach((item) => {
        setAllFiles((previous) => [
          ...previous,
          {
            first: item.name,
            second: item.subject.name,
            third: i18next.t("subjects.levels." + item.level),
            subjectId: item.subject.subjectId,
            levelId: item.level,
            id: item.id,
            selected: false
          },
        ]);
    });
    setReload(false);
  };

  const fetchSubjects = async () => {
    const res = await userService.getUserSubjects(currentUser.id);
    const data = await handleService(res, navigate);
    data.forEach(item => {
      setCurrentSubjects((previous) => [
        ...previous,
        {
          name: item.subject,
          id: item.id,
          levels: item.levels,
        }
      ]);
    })
    setCurrentSubjects(prev => [initialState, ...prev]);
  };

  useEffect(() => {
    if (currentUser && reload) {
      fetchFiles();
    }
  }, [currentUser, reload]);

  useEffect(() => {
    handleTeacherRole(navigate);
    setCurrentUser(AuthService.getCurrentUser());
  }, []);

  useEffect(() => {
    if (currentSubjects) {
      setSubject(currentSubjects[0]);
      currentSubjects[0] && changeLevels(currentSubjects[0].levels);
    }
  }, [currentSubjects]);

  useEffect(() => {
    subject && changeLevels(subject.levels);
  }, [subject]);

  useEffect(() => {
    if (!show) {
      setFilteredFiles([]);
      filterFiles();
    }
  }, [subject, level, allFiles]);

  useEffect(() => {
    if (show) {
      setCurrentSubjects(
        currentSubjects.filter((item) => item.id !== ALL_SUBJECTS)
      );
    } else {
      if (currentSubjects.length === 0) {
        currentUser && fetchSubjects();
      } else {
        setCurrentSubjects(prev => [initialState, ...prev]);
      }
      setLevel(ALL_LEVELS);
    }
  }, [show, currentUser]);

  useEffect(() => {
    if (filteredFiles && filteredFiles.length === 0) setCheckAll(false);
  }, [filteredFiles]);

  return {
    subject,
    level,
    currentSubjects,
    currentLevels,
    filteredFiles,
    show,
    newFiles,
    checkAll,
    currentUser,
    setReload,
    setShow,
    setNewFiles, 
    setAllFiles,
    setLevel,
    setSubject,
    setFilteredFiles,
    setCheckAll
  };
};
