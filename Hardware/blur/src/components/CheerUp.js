import React, { useState, useEffect } from 'react';
import './CheerUp.css';

function CheerUp() {
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
        const timer = setTimeout ( () => {
            setCheerUp(cheerUps[getRandomIndex(cheerUps.length)]);    
        }, 4000); //cheerUp 텍스트 시간 차 두고 렌더링

        return () => clearTimeout(timer);
    }, []);

    return (
        <div className="welcomeSign">
        <span className='textAnimation'>반가워요,</span>
        <div className='userName'>
            Guest.
        </div>
        <div>
        {cheerUp}
        </div>
        </div>
    );
}

export default CheerUp;