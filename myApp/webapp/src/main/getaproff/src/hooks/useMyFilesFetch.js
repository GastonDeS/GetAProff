import { useState, useEffect } from "react";
import axios from "axios";
import i18next from "i18next";

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
  const [deleted, setDeleted] = useState([]);
  const [initialState, setInitialState] = useState();

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

  const addAllLevels = (all, levels) => {
    if (all.length === 0) {
      levels.forEach((level) => all.push(level));
    } else {
      levels.forEach((level) => {
        if (all.filter((item) => item === level).length === 0) {
          all.push(level);
        }
      });
    }
  };

  const fetchFiles = async () => {
    const files = await axios.get("/subject-files/145");
    files &&
      files.data.forEach((item) => {
        setAllFiles((previous) => [
          ...previous,
          {
            first: item.name,
            second: item.subject.name,
            third: i18next.t("subjects.levels." + item.level),
            subjectId: item.subject.subjectId,
            levelId: item.level,
            id: item.fileId,
            selected: false
          },
        ]);
      });
  };

  const fetchSubjects = async () => {
    const subjects = await axios.get("/teachers/subjects/levels/145");
    var allLevels = [];
    subjects.data.forEach((item) => {
      addAllLevels(allLevels, item.levels);
      setCurrentSubjects((previous) => [
        ...previous,
        {
          name: item.subject.name,
          id: item.subject.subjectId,
          levels: item.levels,
        },
      ]);
    });
    setInitialState({
      id: 0,
      name: i18next.t("subjects.all"),
      levels: allLevels,
    })
  };

  useEffect(() => {
    fetchFiles();
  }, []);

  useEffect(() => {
    initialState && setCurrentSubjects([initialState, ...currentSubjects]);
  }, [initialState]);

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
    console.log(allFiles)
    if (!show) {
      setFilteredFiles([]);
      filterFiles();
    }
  }, [subject, level, allFiles]);

  useEffect(() => {
    deleted.forEach((id) => {
      setAllFiles(allFiles.filter((item) => item.id !== id));
    });
  }, [deleted]);

  useEffect(() => {
    if (show) {
      setCurrentSubjects(
        currentSubjects.filter((item) => item.id !== ALL_SUBJECTS)
      );
    } else {
      currentSubjects.length === 0 ? fetchSubjects() : setCurrentSubjects([initialState, ...currentSubjects]);
      setLevel(ALL_LEVELS);
      setNewFiles([]);
    }
  }, [show])

  return {
    subject,
    level,
    currentSubjects,
    currentLevels,
    filteredFiles,
    show,
    newFiles,
    setShow,
    setNewFiles, 
    setAllFiles,
    setDeleted,
    setLevel,
    setSubject,
    setFilteredFiles
  };
};
