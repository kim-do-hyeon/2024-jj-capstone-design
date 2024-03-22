import React, { useState } from 'react';
import './App.css';

import DateTime from './components/DateTime';
import Weather from './components/Weather';
import CheerUp from './components/CheerUp';
<<<<<<< HEAD
import Login from './components/Login';

=======
>>>>>>> cad0b92843ac02c6e500e5f9b444e4057ecc5154

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