import styled from 'styled-components';
import {Pagination} from "react-bootstrap";

export const FiltersContainer = styled.div `
    background: var(--primary);
    border-radius: 8px;
    width: 60%;
    height: fit-content;
    min-width: 200px;
    padding: 15px;
    max-width: 280px;
    min-height: 620px;
    max-height: 700px;
    
    h3 {
        font-size: 1.75rem;
    }
`

export const FilterSection = styled.div `
    margin: 5px 0;
    
    input[type=radio]:checked {
        background-color: var(--secondary);
    }
    
    input[type="radio"]:checked + label {
    font-weight: bold;
}
`

export const Grid = styled.div `
  margin: 30px 0;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 10px;
  grid-auto-rows: minmax(100px, auto);
`

export const StyledPagination = styled(Pagination) `
    align-self: center;
    span {
        background-color: var(--secondary) !important;
        border-color: var(--secondary) !important;
        }
    a {
        color: var(--secondary) !important;
       }
`

