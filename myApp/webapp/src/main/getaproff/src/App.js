import React from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

//Styles
import { GlobalStyle } from './GlobalStyle';
import Login from './views/Login';
import Register from './views/Register';
import Home from './views/Home';

const App = () => {

  return (
    <Router>
      <GlobalStyle/>
      <Routes>
        <Route path='/' element={ <Home/> }/> 
        <Route path='/login' element={ <Login/> }/>
        <Route path='/register' element={ <Register/> }/>
      </Routes>
    </Router>
  );
}

export default App;
