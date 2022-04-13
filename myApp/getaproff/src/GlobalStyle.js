import styled, { createGlobalStyle } from "styled-components";

export const GlobalStyle = createGlobalStyle `
  :root {
    --background: #edeae5;
    --primary: #9fedd7;
    --secondary: #026670;
    --tertiary: hsla(185, 96%, 22%, 0.35);
    --fontExtraLarge: 2.5rem;
    --fontLarge: 1.45rem;
    --fontMed: 1.2rem;
    --fontSmall: 1rem;
    --maxWidth: 1280px;
  }

  * {
    box-sizing: border-box;
    font-family: 'Roboto Light', sans-serif;
  }

  body {
    margin: 0;
    padding: 0;

    p {
      font-size: 1rem;
      margin: 0;
    }

    h3 {
      font-size: 1.2rem;
      font-weight: bold;
      margin: 0;
    }

    h2 {
      font-size: 1.45rem;
      font-weight: bold;
      margin: 0;
    }
  }
`;

export const Title = styled.h1`
  font-size: 2rem;
  margin: 0;
  font-weight: bold;
  color: var(--secondary);
`;

export const Levels = [
  "subjects.levels.0",
  "subjects.levels.1",
  "subjects.levels.2",
  "subjects.levels.3",
];

export const Request = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: fit-content;
  gap: 0.25rem;

  p {
    color: var(--secondary);
  }

  button {
    background: transparent;
    border: none;
    color: var(--secondary);
    text-decoration: underline;

    &:hover {
      color: black;
    }
  }
`;

export const MainContainer = styled.div`
  width: 100%;
  height: 100%;
  padding: 3em;
  display: flex;
  align-items: flex-start;
  justify-content: center;
`;

export const Wrapper = styled.div``;

export const Row = styled.tr`
  background: var(--tertiary);
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

export const Table = styled.table`
  width: 100%;
  margin: 12px 0 8px 0;
  border: none;
`;