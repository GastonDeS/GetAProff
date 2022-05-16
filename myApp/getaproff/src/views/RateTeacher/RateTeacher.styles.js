import styled from 'styled-components';


export const PageContainer = styled.div `
    display: flex;
    flex-direction: column;
    text-align: center;
    
    h1 {
      font-size: 2rem;
      color: var(--secondary);
      font-weight: bold;
    }
`

export const FormContainer = styled.form `
    min-width: fit-content;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 50vw;
    height: fit-content;
    background-color: #9fedd7;
    padding: 10px 10px 10px 10px;
    border-radius: 20px;
    gap: 10px;
  `

export const FormInput = styled.div `
    display: flex;
    flex-direction: column;
    width: 50%;
`