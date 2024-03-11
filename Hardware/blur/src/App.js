import React, { useState, useEffect } from 'react';
import './App.css';
import axios from 'axios';

/*import { WiDaySunny } from "react-icons/wi";
import { WiDayCloudyHigh } from "react-icons/wi";
import { WiCloudy } from "react-icons/wi";
import { WiCloud } from "react-icons/wi";
import { WiShowers } from "react-icons/wi";
import { WiRain } from "react-icons/wi";
import { WiThunderstorm } from "react-icons/wi";
import { WiSnowflakeCold } from "react-icons/wi";
import { WiFog } from "react-icons/wi";*/

function App() {
  const [currentDateTime, setCurrentDateTime] = useState(new Date());
  const year = currentDateTime.getFullYear();
  const month = (currentDateTime.getMonth() + 1).toString().padStart(2, '0');
  const day = currentDateTime.getDate().toString().padStart(2, '0');

  const [cheerUp, setCheerUp] = useState("");
  const [city, setCity] = useState("");
  const [weather, setWeather] = useState("");
  const [Icon, setIcon] = useState("");
  const [temperature, setTemprature] = useState("");
  const [loading, setLoading] = useState(true);

  const cheerUps = ["멋진 날이 될 거예요!", "좋은 하루 보내세요!", "오늘은 어땠나요?", "다 잘 될 거예요!"];
  const getRandomIndex = function (length) {
    return parseInt(Math.random() * length)
  };

  useEffect(() => {
    setCheerUp(cheerUps[getRandomIndex(cheerUps.length)]);

    const interval = setInterval(() => {
      setCurrentDateTime(new Date());
    }, 1000);

    return () => clearInterval(interval);
  }, []);

  useEffect(() => {
    const fetchWeather = async () => {
      try {
        navigator.geolocation.getCurrentPosition(async (position) => {
          const { latitude, longitude } = position.coords;
          const API_KEY = `e4699688bda8af6d121b61b33727cbe4`;
          const response = await axios.get(`https://api.openweathermap.org/data/2.5/onecall?lat=${latitude}&lon=${longitude}&exclude=alerts&appid=${API_KEY}&units=metric`);
          setWeather(response.data.current.weather[0].description);
          setTemprature(response.data.current.temp);

          // 현재 위치의 도시명 가져오기
          const cityResponse = await axios.get(`https://nominatim.openstreetmap.org/reverse?format=json&lat=${latitude}&lon=${longitude}`);
          setCity(cityResponse.data.address.city);

          setLoading(false);
        });
      } catch (error) {
        console.error('Error fetching weather:', error);
        setLoading(false);
      }
    };

    fetchWeather();
  }, []);

  /*const weatherIcon = (Icon) => {
    switch (Icon) {
      case 'clear sky':
        return <WiDaySunny />;
      case 'few clouds':
        return <WiDayCloudyHigh />;
      case 'scattered clouds':
        return <WiCloudy />;
      case 'broken clouds':
        return <WiCloud />;
      case 'shower rain':
        return <WiShowers />;
      case 'rain':
        return <WiRain />;
      case 'thunderstorm':
        return <WiThunderstorm />;
      case 'snow':
        return <WiSnowflakeCold />;
      case 'mist':
        return <WiFog />;
      default :
        return null;
    }
  };*/

  return (
    <div className="App">

      <div>
        <div className="welcomeSign">
          반가워요,
          <div className='userName'>
            Guest.
          </div>
          {cheerUp}
        </div>
      </div>

      <div className="mirror-space mirror"></div>

      <div>
        <div className="dateTime">
          <div className="date">
            <div className='year'>
              {year}
            </div>
            <div className='monthDay'>
              {month}.{day}.
            </div>
          </div>
          <div className="time">
            {currentDateTime.toLocaleTimeString()}
          </div>
        </div>
      </div>

      <div className="mirror-space mirror-4">widget1</div>
      <div className="mirror-space mirror"></div>
      

      <div>
      <div className="weather">
        {loading ? (
            "날씨 정보 가져오는 중..."
            ) : ( 
              <div>
                {/*<div> {weatherIcon(Icon)}</div>*/}
                <div className="tempInfo">{parseFloat(temperature).toFixed(1)}°C</div>
                <div className="weatherInfo">{weather}</div>
                <div className="cityInfo">{city}</div>
              </div>
            )}
      </div>
      </div>

      <div>
        <div className="register">
          Blur 앱에 다음 번호를 입력해 로그인
          <div className='registerNum'>
            0985
          </div>
        </div>
        <div>
        </div>
      </div>

      <div className="mirror-space mirror"></div>
      <div className="mirror-space mirror-9">widget4</div>
    </div>
  );
}

export default App;
