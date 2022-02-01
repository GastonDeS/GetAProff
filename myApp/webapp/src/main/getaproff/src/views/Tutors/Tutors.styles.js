import styled from 'styled-components';

export const FiltersContainer = styled.div `
    background: #9fedd7;
    border-radius: 8px;
    width: 70%;
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
        background-color: #026670;
    }
    
    input[type="radio"]:checked + label {
    font-weight: bold;
}
`

