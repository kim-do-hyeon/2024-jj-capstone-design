import React, { useState, useEffect } from 'react';
import './App.css';
import axios from 'axios';

import DateTime from './components/DateTime';
import Weather from './components/Weather';
import CheerUp from './components/CheerUp';
import Login from './components/Login';


function App() {
  const [widgets, setWidgets] = useState([]);

  useEffect(() => {
    const fetchWidgets = async () => {
      try {
        const response = await axios.get('https://jj.system32.kr/widgets_index');
        const messageOnly = response.data.message; 
        console.log(messageOnly);

        const mappedWidgets = Object.entries(messageOnly).map(([type, position]) => ({
          type,
          row: position[0],
          col: position[1]
        }));

        console.log(mappedWidgets)
        setWidgets(mappedWidgets);
      } catch (error) {
        console.error('Error fetching widgets:', error);
      }
    };

    fetchWidgets();
  }, []);

  const renderWidget = (row, col) => {
    const widget = widgets.find(widget => widget.row === row && widget.col === col); // 해당 위치에 해당하는 위젯 찾기
    if (widget) {
      // 해당 위치에 위젯이 있는 경우
      switch (widget.type) {
        case 'DateTime':
          return <DateTime key={`${row}-${col}`} />;
        case 'Weather':
          return <Weather key={`${row}-${col}`} />;
        case 'CheerUp':
          return <CheerUp key={`${row}-${col}`} />;
        case 'Login':
          return <Login key={`${row}-${col}`} />;
        default:
          return null;
      }
    } else {
      // 해당 위치에 위젯이 없는 경우
      return null;
    }
  };

  return (
    <div className="container">
      {[1, 2, 3].map(row => ( // 행 반복
        <div className="row" key={`row-${row}`} style={{ height: "330px" }}>
          {[1, 2, 3, 4].map(col => ( // 열 반복
            <div className="col" key={`col-${col}`}>
              {renderWidget(row, col)} {/* 해당 위치에 위젯 렌더링 */}
            </div>
          ))}
        </div>
      ))}
    </div>
  );
}

export default App;