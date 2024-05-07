import React, { useState, useEffect } from 'react';
import './CheerUp.css';

function CheerUp({ userName }) {
    const [cheerUp, setCheerUp] = useState('');

    const cheerUps = [
        '멋진 날이 될 거예요!', 
        '좋은 하루 보내세요!', 
        '오늘은 어땠나요?', 
        '다 잘 될 거예요!'
    ];

    const getRandomIndex = (length) => {
        return Math.floor(Math.random() * length);
    };

    useEffect(() => {
        setCheerUp(cheerUps[getRandomIndex(cheerUps.length)]);
    }, []);

    return (
        <div className="welcomeSign">
            반가워요,
            <div className='userName'>{userName}</div>
            {cheerUp}
        </div>
    );
}

export default CheerUp;