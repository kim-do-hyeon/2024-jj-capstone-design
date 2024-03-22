import React, { useState } from 'react';
import './App.css';

import DateTime from './components/DateTime';
import Weather from './components/Weather';
import CheerUp from './components/CheerUp';
import Login from './components/Login';


function App() {

  return (
    <div className="App">
      <div className='CheerUp'>
        <CheerUp />
      </div>
      <div>
      </div>
      <div className='DateTime'>
        <DateTime />
      </div>
      <div>
      </div>
      <div>
        aa
      </div>
      <div>
      </div>
      <div className='Login'>
        <Login/>
      </div>
      <div>
      </div>
      <div className='Weather'>
        <Weather /> 
      </div>
   
  </div>
  );
}

export default App;