import styled from "styled-components";
import Modal from "react-bootstrap/Modal";

export const MainDiv = styled.div`
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

  h1 {
    font-size: 32px;
    font-weight: bold;
    color: var(--secondary);
  }

  h5 {
    margin: 0.625rem 0 0.625rem 0;
    color: var(--secondary);
  }
`;

export const SelectContainer = styled.div`
  display: flex;
  width: 90%;
  margin: 15px 0 25px 0;
  column-gap: 1rem;
`;

export const FilterContainer = styled.div`
  display: flex;
  column-gap: 0.5rem;
  justify-content: center;
  align-items: center;
  width: 50%;

  p {
    color: var(--secondary);
  }
`;

export const Title = styled.h1`
  font-size: 32px;
  font-weight: bold;
  color: var(--secondary);
`;

export const ModalBody = styled(Modal.Body)`
  background-color: var(--primary);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 1rem;

  h3 {
    margin-bottom: 15px;
    font-weight: 400;
    color: var(--secondary);
  }
`;

export const ButtonContainer = styled.div`
  display: flex;
  width: 100%;
  padding: 1rem 3rem 0 3rem;
  justify-content: space-between;
  align-items: center;

  div {
    gap: 0.5rem;
    display: flex;
  }
`;
