import React from 'react';
import './App.css';

import DateTime from './components/DateTime';
import Weather from './components/Weather';
import CheerUp from './components/CheerUp';

function App() {
  return (
    <div className="App">
    <CheerUp />
    <DateTime />
    <Weather />
  </div>
  );
}

export default App;