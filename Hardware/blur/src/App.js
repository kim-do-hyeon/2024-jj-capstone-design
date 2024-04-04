import React, { useState, useEffect } from 'react';
import './App.css';
import axios from 'axios';

import DateTime from './components/DateTime';
import Weather from './components/Weather';
import CheerUp from './components/CheerUp';
import Login from './components/Login';


function App() {
  const [widgets, setWidgets] = useState([]);
  const [showText, setShowText] = useState(false);
  const [face, setFace] = useState(""); // face 상태 추가


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

    
    // setShowText(true)를 1초 후에 호출하여 텍스트를 서서히 렌더링
    const timer = setTimeout( () => {
      setShowText(true);
    }, 1000);

    // 컴포넌트가 unmount되면 타이머 해제
    return () => clearTimeout(timer);
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
          return <CheerUp key={`${row}-${col}`} face={face} />;
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

  const takePhoto = async () => {
    const video = document.createElement('video');
    const canvas = document.createElement('canvas');
    const context = canvas.getContext('2d');

    navigator.mediaDevices.getUserMedia({ video: true })
      .then(stream => {
        video.srcObject = stream;
        video.play();
      });

    video.addEventListener('canplay', () => {
      canvas.width = video.videoWidth;
      canvas.height = video.videoHeight;
      
      // 일정한 시간 간격으로 사진을 찍고 서버로 전송하는 작업을 반복
      const interval = setInterval(() => {
        context.drawImage(video, 0, 0, canvas.width, canvas.height);
        canvas.toBlob(async blob => {
          const formData = new FormData();
          formData.append('face_image', blob, 'photo.jpg');
  
          try {
            const response = await axios.post('https://jj.system32.kr/face', formData, {
              headers: {
                'Content-Type': 'multipart/form-data'
              }
            });
            if (response.data.face !== "Unknown") {
              setFace(response.data.face);
              console.log("1분 기다림.")
              setTimeout(takePhoto, 60000); // 1분(60초) 후에 takePhoto 함수 호출
            }
          } catch (error) {
            console.error('Error sending photo to server:', error);
          }
        });
      }, 10000); // 10초 간격으로 사진을 찍음
    });
  };

  useEffect(() => {
    takePhoto(); // 페이지가 로드될 때 사진을 찍도록 호출
  }, []); // 빈 배열을 전달하여 페이지가 로드될 때 한 번만 실행되도록 함



  return (
    <div className={`container ${showText ? 'show' : ''}`}> {/*서버 시작 시 텍스트 서서히 렌더링*/}
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