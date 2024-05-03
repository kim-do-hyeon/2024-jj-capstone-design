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
        const timer = setTimeout(() => {
            if (face === "Unknown") {
                setCheerUp("반가워요, Guest.");
            } else {
                setCheerUp(cheerUps[getRandomIndex(cheerUps.length)]);
            }
        }, 4000); // cheerUp 텍스트 시간 차 두고 렌더링

        return () => clearTimeout(timer);
    }, [face]); // face 값이 변할 때마다 useEffect 다시 실행

    const takePhoto = async () => {
        const video = document.createElement('video');
        const canvas = document.createElement('canvas');
        const context = canvas.getContext('2d');

        navigator.mediaDevices.getUserMedia({ video: true })
            .then(stream => {
                video.srcObject = stream;
                video.play();
            });

            video.addEventListener('canplay', () => {
                canvas.width = video.videoWidth;
                canvas.height = video.videoHeight;
                
                // 일정한 시간 간격으로 사진을 찍고 서버로 전송하는 작업을 반복
                const interval = setInterval(() => {
                    context.drawImage(video, 0, 0, canvas.width, canvas.height);
                    canvas.toBlob(async blob => {
                        const formData = new FormData();
                        formData.append('face_image', blob, 'photo.jpg');
            
                        try {
                        const response = await axios.post('https://jj.system32.kr/face', formData, {
                            headers: {
                            'Content-Type': 'multipart/form-data'
                            }
                        });
                        if (response.data.face !== "None") {
                            setFace(response.data.face);
                            console.log("1분 기다림.")
                            setTimeout(takePhoto, 60000); // 1분(60초) 후에 takePhoto 함수 호출
                        }
                    } catch (error) {
                    console.error('Error sending photo to server:', error);
                    }
                });
            }, 10000); // 10초 간격으로 사진을 찍음
        });
    };

    return (
        <div className="welcomeSign">
            반가워요,
            <div className='userName'>{userName}</div>
            {cheerUp}
        </div>
    );
}

export default CheerUp;