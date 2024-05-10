import React from 'react';
import { useWeather } from './WeatherContext';

function Traffic() {
    const { location } = useWeather();
    console.log("Traffic component: ", location.latitude, location.longitude);

    // 올바른 변수 값 사용을 위해 템플릿 리터럴과 ${} 문법 사용
    const iframeSrc = `http://127.0.0.1:5000/api/location?lat=${location.latitude}&log=${location.longitude}`;

    return (
        <div className="traffic">
            <iframe src={iframeSrc} title="Traffic Info" width={350} height={350}></iframe>
        </div>
    );
}

export default Traffic;
