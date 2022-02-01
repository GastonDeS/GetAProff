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

  a {
    color: var(--secondary);
    cursor: pointer;

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