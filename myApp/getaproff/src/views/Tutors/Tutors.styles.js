import styled from "styled-components";
import { Pagination } from "react-bootstrap";

export const FiltersContainer = styled.div`
  background: var(--primary);
  border-radius: 0.625rem;
  width: 16vw;
  height: fit-content;
  min-width: 200px;
  padding: 15px;
  max-width: 280px;
  min-height: 620px;
  max-height: 700px;

  h3 {
    font-size: 1.75rem;
  }
`;

export const FilterSection = styled.div`
  margin: 5px 0;

  input[type="radio"]:checked {
    background-color: var(--secondary);
  }

  input[type="radio"]:checked + label {
    font-weight: bold;
  }
`;

export const CustomH1 = styled.h2 `
    margin-top: 10px;
    font-size: 2rem;
`;

export const TutorsWrapper = styled.div`
  width: 70%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
`;

export const SearchBarContainer = styled.div`
  height: fit-content;
  width: 55vw;
  padding: 1rem;
  background-color: var(--primary);
  border-radius: 0.625rem;
`;

export const Grid = styled.div`
  margin: 20px 0;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 10px;
  grid-auto-rows: minmax(100px, auto);
`;

export const StyledPagination = styled(Pagination)`
  align-self: center;
  margin-top: 10px;
  span {
    background-color: var(--secondary) !important;
    border-color: var(--secondary) !important;
  }
  a {
    color: var(--secondary) !important;
  }
`;
