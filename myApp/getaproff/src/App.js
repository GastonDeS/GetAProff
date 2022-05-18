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
import Error404 from './views/Error404'
import Favorites from './views/Favorites';
import Classroom from "./views/Classroom";
import RateTeacher from "./views/RateTeacher";
import Error from "./views/Error";

const App = () => {
  return (
    <Router basename={process.env.PUBLIC_URL}>
      <GlobalStyle/>
      <Routes>
        <Route path='/' element={ <Home/> }/> 
        <Route path='/users/login' element={ <Login/> }/>
        <Route path='/users/new' element={ <Register/> }/>
        <Route path='/users/:id/favorites' element={ <Favorites/> }/>
        <Route path='/users/:id/classes' element={ <MyClasses/> }/>
        <Route path='/users/:id' element={ <Profile/> }/>
        <Route path='/edit-subjects' element={ <EditSubjects/> }/>
        <Route path='/my-files' element={ <MyFiles/> } />
        <Route path='/request-subject' element={ <RequestSubject/> }/>
        <Route path='/users/:id/class-request' element={ <RequestClass/> } />
        <Route path='/edit-certifications' element={ <EditCertifications/> } />
        <Route path='/edit-profile' element={ <EditProfile/> } />
        <Route path='/tutors' element={<Tutors/> } />
        <Route path='/classroom/:id' element={ <Classroom/> }/>
        <Route path='/users/:id/reviews/:classId' element={<RateTeacher/>}/>
        <Route path='/error' element ={<Error/>} />
        <Route path='*' element ={<Error404/>} />
      </Routes>
    </Router>
  );
}

export default App;
