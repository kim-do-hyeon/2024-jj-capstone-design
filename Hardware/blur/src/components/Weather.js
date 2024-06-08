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
    const [showTempDifferenceMessage, setShowTempDifferenceMessage] = useState(false);
    const [showHotMessage, setShowHotMessage] = useState(false);
    const [showRainStopMessage, setShowRainStopMessage] = useState(false);
    const [showContinuousRainMessage, setShowContinuousRainMessage] = useState(false);
    const [showRainStartMessage, setShowRainStartMessage] = useState(false);
    const [showFogMessage, setShowFogMessage] = useState(false);
    const [fogDuration, setFogDuration] = useState(0);
    const [fineDustMessage, setFineDustMessage] = useState("");
    const [showIntermittentRainMessage, setShowIntermittentRainMessage] = useState(false);

    const rainStopMessages = [
        '비가 그치고 있어서 다행이네요.',
        '비가 멈추고 있으니 저녁에 산책하기 좋은 날씨네요.',
        '우산을 접어놓으셔도 될 것 같아요.',
        '비가 그치니 늦은 저녁 산책 어떠세요?',
        '비가 그치고 하늘이 맑아질 거에요.'
    ];

    const continuousRainMessages = [
        '비가 계속 오고 있으니 우산을 챙기세요.',
        '비가 계속 오는데 우산을 꼭 챙기세요.',
        '비가 계속 오는데 감기 조심하세요.'
    ];

    const rainStartMessages = [
        '앞으로 비가 올 것 같아요. 우산을 챙기는 것을 잊지 마세요!',
        '비가 올 것 같습니다! 우산을 꼭 챙기세요.',
        '비가 살짝 내리기 시작했습니다. 우산을 챙기는 걸 잊지 마세요!',
        '비가 살짝씩 올 것 같아요. 우산을 챙기는 게 좋겠어요.',
    ];

    const tempDifferenceMessages = [
        '오늘은 일교차가 크니까 옷을 챙기고 다니세요!',
        '일교차가 크니까 감기 조심하세요!',
        '낮과 밤의 기온 차가 크니까 옷을 적절히 입으세요!',
        '일교차가 크니까 간절기 옷차림을 해주세요.',
        '일교차가 심하니 조심하세요!',
        '낮과 밤의 온도 차이가 크니까 간절기 옷을 준비하세요.'
    ];

    const hotMessages = [
        '오늘은 정말 더워요! 물을 충분히 마시고 햇빛을 피하세요.',
        '더위에 약한 사람들은 실내에 있거나 그늘에서 쉬세요.',
        '날씨가 덥지만 웃음으로 이겨봐요! ^^',
        '햇빛이 강하니까 모자나 선글라스를 착용해주세요.',
    ];

    const fogMessages = [
        '안개가 짙습니다. 운전 시 조심하세요.',
        '안개가 끼어 시야가 좋지 않습니다. 주의하세요.',
        '안개 때문에 가시거리가 짧아졌습니다. 안전에 유의하세요.',
        '안개가 끼어 있는 날씨입니다. 외출 시 조심하세요.',
        '안개가 짙어서 앞이 잘 보이지 않습니다. 조심하세요.'
    ];

    const fineDustMessages = [
        '오늘 미세먼지 농도가 높습니다. 외출을 자제하세요.',
        '미세먼지가 많으니 마스크를 착용하세요.',
        '미세먼지 농도가 높으니 창문을 닫고 실내에 계세요.',
        '미세먼지 때문에 공기가 탁하니 실내 환기를 자제하세요.',
        '미세먼지가 심한 날입니다. 건강에 유의하세요.'
    ];

    const intermittentRainMessages = [
        '비가 오다 말다 해서 날씨가 변덕스럽네요.',
        '비가 오다 말다 해서 외출 시 우산을 잊지 마세요.',
        '비가 잠깐씩 내리니 우산을 가지고 다니세요.'
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

                // 3시간 뒤 날씨 상태
                const nextHourWeather = response.data.hourly[3].weather[0].main.toLowerCase();

                // 안개 지속 시간 계산
                let fogCount = 0;
                for (let i = 0; i < response.data.hourly.length; i++) {
                    if (response.data.hourly[i].weather[0].main.toLowerCase() === 'fog') {
                        fogCount++;
                    } else {
                        break;
                    }
                }
                setFogDuration(fogCount);

                // 미세먼지 농도 API 호출
                const airQualityResponse = await axios.get(`https://api.openweathermap.org/data/2.5/air_pollution?lat=${latitude}&lon=${longitude}&appid=${API_KEY}`);
                const fineDustConcentration = airQualityResponse.data.list[0].components.pm10;

                if (fineDustConcentration > 50) {
                    setFineDustMessage(fineDustMessages[Math.floor(Math.random() * fineDustMessages.length)]);
                } else {
                    setFineDustMessage("");
                }

                // 비가 오다 말다 하는 경우와 계속 오는 경우 구분
                if (!previousWeather) {
                    previousWeather = currentWeather;
                } else {
                    if (currentWeather.includes('rain') && !previousWeather.includes('rain')) {
                        setShowRainStartMessage(true);
                        setShowRainStopMessage(false);
                        setShowContinuousRainMessage(false);
                        setShowIntermittentRainMessage(false);
                    } else if (!currentWeather.includes('rain') && previousWeather.includes('rain')) {
                        setShowRainStopMessage(true);
                        setShowRainStartMessage(false);
                        setShowContinuousRainMessage(false);
                        setShowIntermittentRainMessage(false);
                    } else if (currentWeather.includes('rain') && previousWeather.includes('rain')) {
                        const currentPrecipitation = response.data.current.rain ? response.data.current.rain['1h'] : 0;
                        const nextPrecipitation = response.data.hourly[1].rain ? response.data.hourly[1].rain['1h'] : 0;
                        const secondNextPrecipitation = response.data.hourly[2].rain ? response.data.hourly[2].rain['1h'] : 0;

                        if (currentPrecipitation > 0 && nextPrecipitation === 0 && secondNextPrecipitation > 0) {
                            setShowIntermittentRainMessage(true); // 비가 오다 말다 하는 경우 메시지 표시
                            setShowRainStartMessage(false);
                            setShowRainStopMessage(false);
                            setShowContinuousRainMessage(false);
                        } else {
                            setShowContinuousRainMessage(true); // 비가 계속 오는 경우 메시지 표시
                            setShowRainStartMessage(false);
                            setShowRainStopMessage(false);
                            setShowIntermittentRainMessage(false);
                        }
                    }
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
    const getRandomFogMessage = () => {
        const randomIndex = Math.floor(Math.random() * fogMessages.length);
        return fogMessages[randomIndex];
    };
    const getRandomIntermittentRainMessage = () => {
        const randomIndex = Math.floor(Math.random() * intermittentRainMessages.length);
        return intermittentRainMessages[randomIndex];
    };

    const defaultMessage = "오늘 날씨가 좋습니다! 좋은 하루 보내세요.";

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
                    {showRainStopMessage && (
                        <div className="messageInfo">{getRandomRainStopMessage()}</div>
                    )}
                    {showContinuousRainMessage && (
                        <div className="messageInfo">{getRandomContinuousRainMessage()}</div>
                    )}
                    {showRainStartMessage && (
                        <div className="messageInfo">{getRandomRainStartMessage()}</div>
                    )}
                    {fogDuration > 5 && (
                        <div className="messageInfo">{getRandomFogMessage()}</div>
                    )}
                    {fineDustMessage && (
                        <div className="messageInfo">{fineDustMessage}</div>
                    )}
                    {showIntermittentRainMessage && (
                        <div className="messageInfo">{getRandomIntermittentRainMessage()}</div>
                    )}
                    {!showTempDifferenceMessage && !showHotMessage && !showRainStopMessage && !showContinuousRainMessage && !showRainStartMessage && !showFogMessage && !fineDustMessage && !showIntermittentRainMessage && (
                        <div className="messageInfo">{defaultMessage}</div>
                    )}
                </div>
            )}
        </div>
    );
}

export default Weather;
