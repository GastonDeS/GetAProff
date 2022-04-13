import styled from 'styled-components';

export const Content = styled.div`
  width: 80vw;
  height: 100%;
  display: flex;
  align-items: flex-start;
  justify-content: space-evenly;
  column-gap: 1rem;
`;

export const FilterContainer = styled.div`
  width: fit-content;
  height: fit-content;
  background-color: var(--primary);
  border-radius: 0.625rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  gap: 0.5rem;
  padding: 0 0.5rem 1rem 0.5rem;
`;

export const Filter = styled.p`
  font-size: var(--fontSmall);
  margin-bottom: 0.5rem;
`;

export const SelectContainer = styled.div`
  width: 88%;
`;

export const CardContainer = styled.div`
  width: 65%;
  height: fit-content;
`;