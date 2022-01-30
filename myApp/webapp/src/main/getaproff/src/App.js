import React from 'react';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';


//Styles
import { GlobalStyle } from './GlobalStyle';
import Login from './views/Login';
import Register from './views/Register';
import Home from './views/Home';
import MyClasses from './views/MyClasses';
import Profile from './views/Profile';
import EditSubjects from './views/EditSubjects';
import MyFiles from './views/MyFiles';

const App = () => {

  return (
    <Router>
      <GlobalStyle/>
      <Routes>
        <Route path='/' element={ <Home/> }/> 
        <Route path='/login' element={ <Login/> }/>
        <Route path='/register' element={ <Register/> }/>
        <Route path='/my-classes' element={ <MyClasses/> }/>
        <Route path='/profile' element={ <Profile/> }/>
        <Route path='/edit-subjects' element={ <EditSubjects/> }/>
        <Route path='/my-files' element={ <MyFiles/> } />
      </Routes>
    </Router>
  );
}

export default App;
