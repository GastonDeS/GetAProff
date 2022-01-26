import styled from 'styled-components';

export const Wrapper = styled.div ``;

export const MainContainer = styled.div`
  width: 100%;
  height: 100%;
  padding: 3em;
  display: flex;
  align-items: flex-start;
  justify-content: center;
`;

export const Subjects = styled.div`
  width: 50vw;
  height: fit-content;
  padding: 1rem;
  background-color: var(--primary);
  border-radius: 0.625rem;
`;

export const Table = styled.table`
  width: 100%;
  margin: 12px 0 8px 0;
  border: none;
`;

export const Row = styled.tr`
  background: hsla(185, 96%, 22%, 0.35);
  box-shadow: 0 0 9px 0 rgb(0 0 0 / 10%);
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-radius: 0.625rem;
  padding: 0.65rem;
  margin-bottom: 8px;
  width: 100%;
`;

export const Headers = styled.th`
  text-align: start;
  font-size: var(--fontMed);
`;