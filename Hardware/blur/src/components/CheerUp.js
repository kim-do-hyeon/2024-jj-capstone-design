import React, { useState, useEffect } from 'react';
import './CheerUp.css';

function CheerUp({ userName }) {
    const [cheerUp, setCheerUp] = useState('');

    const morningMessages = [
        "오늘도 힘찬 하루를 시작해볼까요?",
        "새로운 날, 새로운 시작!",
        "반가워요! 오늘도 멋진 하루 되세요.",
        "오늘 하루도 당신의 웃음으로 가득 채우세요.",
        "오늘은 어제보다 더 나은 하루가 될 거예요.",
        "오늘도 당신을 위한 멋진 기회가 기다리고 있어요!",
        "반짝이는 아침! 오늘을 만끽하세요.",
        "당신의 멋진 하루를 응원합니다!"
    ];

    const afternoonMessages = [
        "멋진 오후네요, 활기찬 시간 되세요!",
        "오후도 파이팅! 계속 집중하세요.",
        "좋은 오후, 잠시 휴식을 취하는 시간은 어떨까요?",
        "오후의 여유를 즐기며 나머지 일과도 화이팅!",
        "오후 시간, 새로운 활력을 얻으세요!",
        "이제 절반을 지났어요! 남은 하루도 파워풀하게 보내세요.",
        "에너지를 보충할 시간! 간단한 간식을 즐기는 건 어떨까요?",
        "아직도 하루는 젊어요! 무엇을 시작하기에 완벽한 시간이죠."
    ];

    const eveningMessages = [
        "느긋한 밤이네요 :) 좋은 저녁 되세요.",
        "하루의 마무리도 행복하게!",
        "밤이 되었네요, 오늘 하루 수고 많으셨습니다.",
        "조용한 저녁, 오늘 하루를 되돌아보세요.",
        "편안한 밤 되세요. 당신의 하루는 어땠나요?",
        "오늘 하루도 잘 마무리하셨나요? 잘 했어요!",
        "좋은 책 한 권으로 하루를 마감하는 건 어떨까요?",
        "평화로운 밤, 달콤한 꿈을 꾸세요."
    ];

    const specialMessage = "오늘은 캡스톤디자인 최종 발표 날이에요. 한 학기 수고 많으셨어요.";

    const getRandomMessage = (messages) => {
        return messages[Math.floor(Math.random() * messages.length)];
    };

    useEffect(() => {
        const today = new Date();
        const currentHour = today.getHours();
        let selectedMessages;
        
        // 특별한 날짜 체크
        if (today.getMonth() === 5 && today.getDate() === 19) {
            setCheerUp(specialMessage);
            return;
        }

        if (currentHour >= 5 && currentHour < 12) {
            selectedMessages = morningMessages;
        } else if (currentHour >= 12 && currentHour < 18) {
            selectedMessages = afternoonMessages;
        } else {
            selectedMessages = eveningMessages;
        }
        setCheerUp(getRandomMessage(selectedMessages));
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