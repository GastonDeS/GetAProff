import { useLocation } from "react-router-dom";
import React from "react";

export function useQuery() {
  const { search } = useLocation();
  return React.useMemo(() => new URLSearchParams(search), [search]);
}

export const getQuery = (query, param, defaultValue = undefined) => {
  const value = query.get(param);
  if (value === null) return defaultValue;
  return value;
}