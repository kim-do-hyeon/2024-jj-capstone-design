import React, { useState, useEffect } from 'react';
import './App.css';
import axios from 'axios';

import DateTime from './components/DateTime';
import Weather from './components/Weather';
import CheerUp from './components/CheerUp';
import Login from './components/Login';


function App() {
  const [widgets, setWidgets] = useState(null);

  useEffect(() => {
    const fetchWidgets = async () => {
      try {
        const response = await axios.get('https://jj.system32.kr/widgets');
        console.log(response.data);
        const messageOnly = response.data.message; 
        console.log(messageOnly);
        setWidgets(messageOnly);
      } catch (error) {
        console.error('Error fetching widgets:', error);
      }
    };

    fetchWidgets();
  }, []);

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