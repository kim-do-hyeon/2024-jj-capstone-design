import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Weather.css'
import { useWeather } from './WeatherContext';

import { 
    WiDaySunny, 
    WiDayCloudyHigh, 
    WiCloudy, 
    WiCloud,
    WiShowers,
    WiRain,
    WiThunderstorm,
    WiSnowflakeCold,
    WiFog
} from "react-icons/wi"; 

function Weather() {
    const { location, setLocation } = useWeather();
    const [weather, setWeather] = useState("");
    const [temperature, setTemperature] = useState("");
    const [city, setCity] = useState("");
    const [loading, setLoading] = useState(true);
    const [iconCode, setIconCode] = useState(""); // 아이콘 상태 추가
    const [showUmbrellaMessage, setShowUmbrellaMessage] = useState("");
    const [showClothesMessage, setShowClothesMessage] = useState(""); 
    const [showThinClothesMessage, setShowThinClothesMessage] = useState("");
    const [showHotMessage, setShowHotMessage] = useState(""); // 30도 이상일 때 "오늘 너무 덥습니다" 메시지를 표시하기 위한 상태 추가

    // 날씨 아이콘 반환 함수
    const getWeatherIcon = (iconCode) => {
        switch (iconCode) {
            case '01n': return <WiDaySunny className="weatherIcon" />;
            case '01d': return <WiDaySunny className="weatherIcon" />;
            case '02n': return <WiDayCloudyHigh className="weatherIcon" />;
            case '03n': return <WiCloudy className="weatherIcon" />;
            case '04n': return <WiCloud className="weatherIcon" />;
            case '04d': return <WiCloud className="weatherIcon" />;
            case '09n': return <WiShowers className="weatherIcon" />;
            case '10n': return <WiRain className="weatherIcon" />;
            case '10d': return <WiRain className="weatherIcon" />;
            case '11n': return <WiThunderstorm className="weatherIcon" />;
            case '13n': return <WiSnowflakeCold className="weatherIcon" />;
            case '50n': return <WiFog className="weatherIcon" />;
            default: return null; // 혹은 기본 날씨 아이콘
        }
    };

    useEffect(() => {
        const fetchWeather = async () => {
            try {
                navigator.geolocation.getCurrentPosition(async (position) => {
                    const { latitude, longitude } = position.coords;
                    setLocation({ latitude: position.coords.latitude, longitude: position.coords.longitude });
                    const API_KEY = 'e4699688bda8af6d121b61b33727cbe4';
                    //const API_KEY = process.env.BLUR_APP_OPEN_WEATHER_API_KEY; API KEY 로딩 오류로 인한 주석처리
                    const response = await axios.get(`https://api.openweathermap.org/data/2.5/onecall?lat=${latitude}&lon=${longitude}&exclude=alerts&appid=${API_KEY}&units=metric`);
                    //const response1 = await axios.get("https://jj.system32.kr/widgets");
                    //console.log(response1);
                    console.log(response.data); // 전체 응답 로깅
                    console.log("Icon code from API:", response.data.current.weather[0].icon); // 아이콘 코드 로깅
        
                    setWeather(response.data.current.weather[0].description);
                    setTemperature(response.data.current.temp);
                    setIconCode(response.data.current.weather[0].icon); // 아이콘 코드 상태 설정

                    // 현재 위치의 도시명 가져오기
                    const cityResponse = await axios.get(`https://nominatim.openstreetmap.org/reverse?format=json&lat=${latitude}&lon=${longitude}`);
                    setCity(cityResponse.data.address.city);

                    // 최고 기온과 최저 기온을 가져와서 일교차를 계산
                    const dailyData = response.data.daily;
                    const maxTemp = dailyData[0].temp.max;
                    const minTemp = dailyData[0].temp.min;
                    const tempDifference = maxTemp - minTemp;

                    // 일교차가 10도 이상인 경우에는 옷을 챙기라는 메시지를 표시
                    if (tempDifference >= 10) {
                        setShowClothesMessage(true);
                    }
                    
                    // 비가 오는 경우에는 우산을 챙기라는 메시지를 표시
                    if (response.data.current.weather[0].main.toLowerCase().includes('rain')) {
                        setShowUmbrellaMessage(true);
                    }

                    // 온도가 30도 이상일 때 얇게 입으세요 메시지를 표시
                    if (response.data.current.temp >= 30) {
                        setShowThinClothesMessage(true);
                    }

                    // 온도가 30도 이상일 때 "오늘 너무 덥습니다" 메시지를 표시
                    if (response.data.current.temp >= 30) {
                        setShowHotMessage(true);
                    }
            
                    setLoading(false);
                });
            } catch (error) {
                console.error('Error fetching weather:', error);
                setLoading(false);
            }
        };
    
        fetchWeather();
    }, [setLocation]);

    return (
        <div className="weather">
            {loading ? (
                "날씨 로드 중..."
            ) : (
                <div>
                <div>
                  {getWeatherIcon(iconCode)}
                </div>
                <div className="tempInfo">
                    {parseFloat(temperature).toFixed(1)}°C
                    </div>
                <div className="weatherInfo">{weather}</div>
                <div className="cityInfo">{city}</div>
                {showUmbrellaMessage && (
                        <div className="messageInfo">비가 오면 우산을 챙기세요!</div>
                    )}
                    {showClothesMessage && (
                        <div className="messageInfo">일교차가 클 때 옷을 챙기세요!</div>
                    )}
                    {showThinClothesMessage && (
                        <div className="messageInfo">30도 이상일 때 얇게 입으세요!</div>
                    )}
                    {showHotMessage && (
                        <div className="messageInfo">너무 덥습니다!</div>
                    )}
                </div>
            )}
        </div>
    );
}
    
    export default Weather;