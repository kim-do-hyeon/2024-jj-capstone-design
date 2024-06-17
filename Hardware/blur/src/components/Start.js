import React, { useEffect, useState } from 'react';
import './Start.css';

function Start({ userName }) {
    const [fadeOut, setFadeOut] = useState(false);
    const [currentDateTime, setCurrentDateTime] = useState(new Date());

    useEffect(() => {
        const timeout = setTimeout(() => {
            setFadeOut(true);
        }, 1000);

        return () => clearTimeout(timeout);
    }, []);

    let greeting = ''; // 인사말 저장할 변수 초기화

    const hour = currentDateTime.getHours();

    // 시간에 따른 다른 시작 텍스트 출력
    if (hour >= 5 && hour < 11) {
        greeting = '활기찬 아침 보내세요!';
    } else if (hour >= 12 && hour < 18) {
        greeting = '좋은 오후네요!';
    } else {
        greeting = '느긋한 밤이네요 :)';
    }

    return (
        <div className={`startContainer ${fadeOut ? 'fadeOut' : ''}`}>
            <div className='centeredText'>{greeting}</div>
        </div>
    );
}

export default Start;