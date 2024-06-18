import React, { useState } from 'react';
import CameraFeed from './CameraFeed';
import './PersonalColor.css';

const PersonalColor = () => {
    const [colorTone, setColorTone] = useState(null);
    const [message, setMessage] = useState('');

    const handleColorDetected = (tone) => {
        setColorTone(tone);
        setMessage(` 당신의 퍼스널 컬러는 ${tone} 입니다.`);
    };

    return (
        <div className="personal-color-container">
            <CameraFeed onColorDetected={handleColorDetected} />
            {message && <p className="personal-color-message">{message}</p>}
        </div>
    );
};

export default PersonalColor;
