import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Weather.css';
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
    const [iconCode, setIconCode] = useState("");
    const [showTempDifferenceMessage, setShowTempDifferenceMessage] = useState("");
    const [showHotMessage, setShowHotMessage] = useState("");
    const [showRainStopMessage, setShowRainStopMessage] = useState("");
    const [showContinuousRainMessage, setShowContinuousRainMessage] = useState("");
    const [showRainStartMessage, setShowRainStartMessage] = useState("");
    const  rainStopMessages= [
        '비가 그치고 있어서 다행이네요.',
        '비가 멈추고 있으니 산책하기 좋은 날씨네요.',
        '우산을 접어놓으셔도 될 것 같아요.',
        '비가 그치니 늦은 저녁 산책 어떠세요?',
        '비가 그치고 하늘이 맑아질 거에요.'
    ];

    const  continuousRainMessages= [
        '비가 계속 오고 있으니 우산을 챙기세요.',
        '비가 계속 내리고 있어서 실내에서 따뜻한 차 한 잔 어떠세요?',
        '비가 계속 오는데 우산을 꼭 챙기세요.',
        '비가 계속 오는데 감기 조심하세요.'
    ];

    const  rainStartMessages= [
        '비가 올 것 같습니다! 우산을 꼭 챙기세요.',
        '비가 살짝 내리기 시작했습니다. 우산을 챙기는 걸 잊지 마세요!',
        '비가 살짝씩 올 것 같아요. 우산을 챙기는 게 좋겠어요.',
    ];

    const tempDifferenceMessages = [
        '오늘은 일교차가 크니까 옷을 적당히 입고 다니세요!',
        '일교차가 커서 감기 조심하세요!',
        '낮과 밤의 기온 차가 크니까 옷을 적절히 입으세요!',
        '일교차가 크니까 간절기 옷차림을 해주세요.',
        '기온 변화에 주의하세요. 체감 온도가 달라질 수 있습니다.',
        '일교차가 심하면 몸에 부담이 될 수 있으니 조심하세요!',
        '낮과 밤의 온도 차이가 크니까 간절기 옷을 준비하세요.'
    ];

    const hotMessages = [
        '오늘은 정말 더워요! 물을 충분히 마시고 햇빛을 피하세요.',
        '더위에 약한 사람들은 실내에 있거나 그늘에서 쉬세요.',
        '오늘은 더위가 특히 무더워요. 외출 시에는 모자와 선크림을 꼭 착용하세요!',
        '더워도 웃음으로 이겨봐요! ^^',
        '오늘은 물이 필요한 날입니다. 자주 마시세요!',
        '햇빛이 강하니까 모자나 선글라스를 착용해주세요.',
        '더운 날씨에는 가벼운 음료수를 즐겨보세요.',
        '날씨가 더워도 에너지는 열정적으로 유지하세요!'
    ];

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
            default: return null;
        }
    };

    useEffect(() => {
        const fetchWeather = async () => {
            try {
                const locationResponse = await axios.get('https://ipapi.co/json/');
                const { latitude, longitude, city } = locationResponse.data;
                setLocation({ latitude, longitude });
                setCity(city);

                const API_KEY = 'e4699688bda8af6d121b61b33727cbe4';
                const response = await axios.get(`https://api.openweathermap.org/data/2.5/onecall?lat=${latitude}&lon=${longitude}&exclude=alerts&appid=${API_KEY}&units=metric`);

                setWeather(response.data.current.weather[0].description);
                setTemperature(response.data.current.temp);
                setIconCode(response.data.current.weather[0].icon);

                const dailyData = response.data.daily;
                const maxTemp = dailyData[0].temp.max;
                const minTemp = dailyData[0].temp.min;
                const tempDifference = maxTemp - minTemp;
            
                if (tempDifference >= 10) {
                    setShowTempDifferenceMessage(true);
                } else {
                    setShowTempDifferenceMessage(false);
                }

                if (response.data.current.temp >= 30) {
                    setShowHotMessage(true);
                } else {
                    setShowHotMessage(false);
                }

                // 이전 날씨 상태를 저장할 변수
                let previousWeather = "";

                // 현재 날씨 상태
                const currentWeather = response.data.current.weather[0].main.toLowerCase();

                // 1시간 뒤 날씨 상태
                const nextHourWeather = response.data.hourly[1].weather[0].main.toLowerCase();

                // 이전 날씨 상태가 없는 경우 현재 날씨 상태를 이전 상태로 설정
                if (!previousWeather) {
                    previousWeather = currentWeather;
                } else {
                    // 이전 날씨 상태와 현재 날씨 상태가 다른 경우에 메시지 표시
                    if (currentWeather.includes('rain') && !previousWeather.includes('rain')) {
                        setShowRainStartMessage(true); // 비가 오다가 시작하는 경우 메시지 표시
                    } else if (!currentWeather.includes('rain') && previousWeather.includes('rain')) {
                        setShowRainStopMessage(true); // 비가 오다가 멈추는 경우 메시지 표시
                    } else if (currentWeather.includes('rain') && previousWeather.includes('rain')) {
                        setShowContinuousRainMessage(true); // 비가 계속 오는 경우 메시지 표시
                    }
                    // 현재 날씨 상태를 이전 상태로 설정
                    previousWeather = currentWeather;
                }

                setLoading(false);
            } catch (error) {
                console.error('Error fetching weather:', error);
                setLoading(false);
            }
        };
    
        fetchWeather();
    }, [setLocation]);

    const getRandomRainStopMessage = () => {
        const randomIndex = Math.floor(Math.random() * rainStopMessages.length);
        return rainStopMessages[randomIndex];
    };
    const getRandomContinuousRainMessage = () => {
        const randomIndex = Math.floor(Math.random() * continuousRainMessages.length);
        return continuousRainMessages[randomIndex];
    };
    const getRandomRainStartMessage = () => {
        const randomIndex = Math.floor(Math.random() * rainStartMessages.length);
        return rainStartMessages[randomIndex];
    };
    const getRandomTempDifferenceMessage = () => {
        const randomIndex = Math.floor(Math.random() * tempDifferenceMessages.length);
        return tempDifferenceMessages[randomIndex];
    };
    const getRandomHotMessage = () => {
        const randomIndex = Math.floor(Math.random() * hotMessages.length);
        return hotMessages[randomIndex];
    };

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
                    {showTempDifferenceMessage && (
                        <div className="messageInfo">{getRandomTempDifferenceMessage()}</div>
                    )}
                    {showHotMessage && (
                        <div className="messageInfo">{getRandomHotMessage()}</div>
                    )}
                    {showRainStartMessage && (
                        <div className="messageInfo">{getRandomRainStopMessage()}</div>
                    )}
                    {showContinuousRainMessage && (
                        <div className="messageInfo">{getRandomContinuousRainMessage()}</div>
                    )}
                    {showRainStartMessage && (
                        <div className="messageInfo">{getRandomRainStartMessage()}</div>
                    )}
                </div>
            )}
        </div>
    );
}

export default Weather;
