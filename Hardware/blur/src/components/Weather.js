import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Weather.css';
import { useWeather } from './WeatherContext';

import { 
    WiDaySunny,
    WiNightClear,
    WiDayCloudy,
    WiNightAltCloudy,
    WiCloud,
    WiCloudy,
    WiDayShowers,
    WiNightAltShowers,
    WiDayRain,
    WiNightAltRain,
    WiDayThunderstorm,
    WiNightAltThunderstorm,
    WiSnow,
    WiDaySnow,
    WiNightAltSnow,
    WiFog,
    WiDayFog,
    WiNightFog,
    WiHail,
    WiSleet,
    WiStrongWind,
    WiTornado,
    WiHurricane,
    WiDust,
    WiSandstorm,
    WiMeteor,
    WiHot,
    WiSnowflakeCold,
    WiDayHaze,
    WiDaySleet,
    WiNightAltPartlyCloudy,
    WiNightAltHail,
    WiNightAltSleet,
    WiNightAltSnowThunderstorm,
} from "react-icons/wi"; 

function Weather() {
    const { location, setLocation } = useWeather();
    const [weather, setWeather] = useState("");
    const [temperature, setTemperature] = useState("");
    const [city, setCity] = useState("");
    const [loading, setLoading] = useState(true);
    const [iconCode, setIconCode] = useState("");
    const [message, setMessage] = useState("");

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

    const uvMessages = [
        '오늘은 자외선이 강합니다. 선크림을 꼭 바르세요.',
        '자외선 지수가 높으니 모자와 선글라스를 챙기세요.',
        '자외선이 강한 날씨입니다. 실외 활동 시 주의하세요.',
        '햇볕이 강하니 그늘을 찾아 다니세요.',
        '자외선 차단제를 잊지 마세요!'
    ];

    const coldMessages = [
        '오늘은 매우 춥습니다. 따뜻하게 입고 나가세요!',
        '기온이 0도 이하로 내려갔습니다. 보온에 신경쓰세요.',
        '추운 날씨입니다. 외출 시 따뜻한 옷을 입으세요.',
        '매우 추운 날씨입니다. 감기 조심하세요!',
        '날씨가 많이 춥습니다. 실내에서 따뜻하게 지내세요.'
    ];

    const windMessages = [
        '오늘은 바람이 강하게 불어요. 외출 시 주의하세요!',
        '강풍이 부는 날씨입니다. 바람에 날리는 물건에 주의하세요.',
        '강풍 주의보가 발령됐습니다. 실외 활동을 피하세요.',
        '바람이 강하니 모자나 가벼운 물건은 챙기세요.',
    ];

    const humidityMessages = [
        '오늘은 습도가 높습니다. 실내 환기를 자주 해주세요.',
        '습도가 높아 불쾌지수가 높습니다. 쾌적하게 지낼 방법을 찾으세요.',
        '습한 날씨에 땀이 많이 날 수 있으니 가벼운 옷차림을 하세요.',
        '습도가 높아 불쾌감을 줄 수 있으니 시원한 음료를 즐기세요.',
        '습한 날씨가 계속되니 실내 습도를 조절하세요.'
    ];

    const getWeatherIcon = (iconCode) => {
        switch (iconCode) {
            case '01d': return <WiDaySunny className="weatherIcon" />;
            case '01n': return <WiNightClear className="weatherIcon" />;
            case '02d': return <WiDayCloudy className="weatherIcon" />;
            case '02n': return <WiNightAltCloudy className="weatherIcon" />;
            case '03d': return <WiCloud className="weatherIcon" />;
            case '03n': return <WiCloud className="weatherIcon" />;
            case '04d': return <WiCloudy className="weatherIcon" />;
            case '04n': return <WiCloudy className="weatherIcon" />;
            case '09d': return <WiDayShowers className="weatherIcon" />;
            case '09n': return <WiNightAltShowers className="weatherIcon" />;
            case '10d': return <WiDayRain className="weatherIcon" />;
            case '10n': return <WiNightAltRain className="weatherIcon" />;
            case '11d': return <WiDayThunderstorm className="weatherIcon" />;
            case '11n': return <WiNightAltThunderstorm className="weatherIcon" />;
            case '13d': return <WiDaySnow className="weatherIcon" />;
            case '13n': return <WiNightAltSnow className="weatherIcon" />;
            case '50d': return <WiDayFog className="weatherIcon" />;
            case '50n': return <WiNightFog className="weatherIcon" />;
            case 'hail': return <WiHail className="weatherIcon" />;
            case 'sleet': return <WiSleet className="weatherIcon" />;
            case 'strong-wind': return <WiStrongWind className="weatherIcon" />;
            case 'tornado': return <WiTornado className="weatherIcon" />;
            case 'hurricane': return <WiHurricane className="weatherIcon" />;
            case 'dust': return <WiDust className="weatherIcon" />;
            case 'sandstorm': return <WiSandstorm className="weatherIcon" />;
            case 'meteor': return <WiMeteor className="weatherIcon" />;
            case 'hot': return <WiHot className="weatherIcon" />;
            case 'cold': return <WiSnowflakeCold className="weatherIcon" />;
            case 'day-haze': return <WiDayHaze className="weatherIcon" />;
            case 'day-sleet': return <WiDaySleet className="weatherIcon" />;
            case 'night-partly-cloudy': return <WiNightAltPartlyCloudy className="weatherIcon" />;
            case 'night-hail': return <WiNightAltHail className="weatherIcon" />;
            case 'night-sleet': return <WiNightAltSleet className="weatherIcon" />;
            case 'night-snow-thunderstorm': return <WiNightAltSnowThunderstorm className="weatherIcon" />;
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

                const newMessages = [];

                if (tempDifference >= 10) {
                    newMessages.push(tempDifferenceMessages[Math.floor(Math.random() * tempDifferenceMessages.length)]);
                }

                if (response.data.current.temp >= 30) {
                    newMessages.push(hotMessages[Math.floor(Math.random() * hotMessages.length)]);
                }

                // 자외선 지수에 따른 메시지 설정
                const uvIndex = response.data.current.uvi;
                if (uvIndex > 5) {
                    newMessages.push(uvMessages[Math.floor(Math.random() * uvMessages.length)]);
                }

                // 0도 이하 기온 메시지 설정
                if (response.data.current.temp < 0) {
                    newMessages.push(coldMessages[Math.floor(Math.random() * coldMessages.length)]);
                }

                // 강풍 메시지 설정
                if (response.data.current.wind_speed > 10) {
                    newMessages.push(windMessages[Math.floor(Math.random() * windMessages.length)]);
                }

                // 습도 메시지 설정
                if (response.data.current.humidity > 80) {
                    newMessages.push(humidityMessages[Math.floor(Math.random() * humidityMessages.length)]);
                }

                // 안개 메시지 추가
                if (response.data.current.weather[0].main.toLowerCase() === 'fog') {
                    newMessages.push(fogMessages[Math.floor(Math.random() * fogMessages.length)]);
                }

                // 미세먼지 농도 API 호출
                const airQualityResponse = await axios.get(`https://api.openweathermap.org/data/2.5/air_pollution?lat=${latitude}&lon=${longitude}&appid=${API_KEY}`);
                const fineDustConcentration = airQualityResponse.data.list[0].components.pm10;

                if (fineDustConcentration > 50) {
                    newMessages.push(fineDustMessages[Math.floor(Math.random() * fineDustMessages.length)]);
                }

                // 비가 오다 말다 하는 경우와 계속 오는 경우 구분
                const currentWeather = response.data.current.weather[0].main.toLowerCase();
                const previousWeather = response.data.hourly[1].weather[0].main.toLowerCase();

                if (currentWeather.includes('rain') && !previousWeather.includes('rain')) {
                    newMessages.push(rainStartMessages[Math.floor(Math.random() * rainStartMessages.length)]);
                } else if (!currentWeather.includes('rain') && previousWeather.includes('rain')) {
                    newMessages.push(rainStopMessages[Math.floor(Math.random() * rainStopMessages.length)]);
                } else if (currentWeather.includes('rain') && previousWeather.includes('rain')) {
                    const currentPrecipitation = response.data.current.rain ? response.data.current.rain['1h'] : 0;
                    const nextPrecipitation = response.data.hourly[1].rain ? response.data.hourly[1].rain['1h'] : 0;
                    const secondNextPrecipitation = response.data.hourly[2].rain ? response.data.hourly[2].rain['1h'] : 0;

                    if (currentPrecipitation > 0 && nextPrecipitation === 0 && secondNextPrecipitation > 0) {
                        newMessages.push(intermittentRainMessages[Math.floor(Math.random() * intermittentRainMessages.length)]);
                    } else {
                        newMessages.push(continuousRainMessages[Math.floor(Math.random() * continuousRainMessages.length)]);
                    }
                }

                if (newMessages.length === 0) {
                    newMessages.push("오늘 날씨가 좋습니다! 좋은 하루 보내세요.");
                }

                setMessage(newMessages[Math.floor(Math.random() * newMessages.length)]);
                setLoading(false);
            } catch (error) {
                console.error('Error fetching weather:', error);
                setLoading(false);
            }
        };

        fetchWeather();
    }, [setLocation]);

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
                    <div className="messageInfo">{message || defaultMessage}</div>
                </div>
            )}
        </div>
    );
}

export default Weather;
