import React from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import NavbarCust from './components/navbarCust/navbarCust';
import TutorCard from './components/tutorCard/tutorCard';

function App() {
  return (
    <div className="App">
      <NavbarCust/>
      <TutorCard/>
    </div>
  );
}

export default App;
