import { createGlobalStyle } from "styled-components";

export const GlobalStyle = createGlobalStyle `
  :root {
    --background: #edeae5;
    --primary: #9fedd7;
    --secondary: #026670;
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

    h2 {
      font-size: 1.45rem;
      font-weight: bold;
    }
  }

`