import React from 'react';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

//Styles
import { GlobalStyle } from './GlobalStyle';
import Login from './views/Login';
import Register from './views/Register';
import Home from './views/Home';
import MyClasses from './views/MyClasses';
import Profile from './views/Profile';

const App = () => {

  return (
    <Router>
      <GlobalStyle/>
      <Routes>
        <Route path='/' element={ <Home/> }/> 
        <Route path='/login' element={ <Login/> }/>
        <Route path='/register' element={ <Register/> }/>
        <Route path='/my-classes' element={ <MyClasses/> }/>
        <Route path='/profile/:id' element={ <Profile/> }/>
      </Routes>
    </Router>
  );
}

export default App;
