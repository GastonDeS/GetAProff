import React from 'react';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

//Styles
import { GlobalStyle } from './GlobalStyle';
import Login from './views/Login';
import Register from './views/Register';
import Home from './views/Home';
import MyClasses from './views/MyClasses';
import EditSubjects from './views/EditSubjects';

const App = () => {

  return (
    <Router>
      <GlobalStyle/>
      <Routes>
        <Route path='/' element={ <Home/> }/> 
        <Route path='/login' element={ <Login/> }/>
        <Route path='/register' element={ <Register/> }/>
        <Route path='/my-classes' element={ <MyClasses/> }/>
        <Route path='/edit-subjects' element={ <EditSubjects/> }/>
      </Routes>
    </Router>
  );
}

export default App;
