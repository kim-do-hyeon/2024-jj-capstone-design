import React, { useState, useEffect } from 'react';
import './CheerUp.css';

function CheerUp({ face }) {
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
        const timer = setTimeout(() => {
            if (face === "Unknown") {
                setCheerUp("반가워요, Guest.");
            } else {
                setCheerUp(cheerUps[getRandomIndex(cheerUps.length)]);
            }
        }, 4000); // cheerUp 텍스트 시간 차 두고 렌더링

        return () => clearTimeout(timer);
    }, [face]); // face 값이 변할 때마다 useEffect 다시 실행

    return (
        <div className="welcomeSign">
            <span className='textAnimation'>반가워요,</span>
            <div className='userName'>
                {face === "Unknown" ? "Guest" : face}.
            </div>
            <div>
                {cheerUp}
            </div>
        </div>
    );
}

export default CheerUp;
