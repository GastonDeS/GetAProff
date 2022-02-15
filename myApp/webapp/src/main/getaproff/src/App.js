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
import RequestSubject from './views/RequestSubject';
import RequestClass from './views/RequestClass';
import EditCertifications from './views/EditCertifications';
import EditProfile from './views/EditProfile';
import Tutors from "./views/Tutors";
import Classrom from './views/Classroom';
import Favorites from './views/Favorites';

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
        <Route path='/edit-subjects' element={ <EditSubjects/> }/>
        <Route path='/my-files' element={ <MyFiles/> } />
        <Route path='/request-subject' element={ <RequestSubject/> }/>
        <Route path='/request-class' element={ <RequestClass/> } />
        <Route path='/edit-certifications' element={ <EditCertifications/> } />
        <Route path='/edit-profile' element={ <EditProfile/> } />
        <Route path='/tutors' element={<Tutors/> } />
        <Route path='/classroom' element={ <Classrom/> }/>
        <Route path='/favorites' element={ <Favorites/> }/>
      </Routes>
    </Router>
  );
}

export default App;
