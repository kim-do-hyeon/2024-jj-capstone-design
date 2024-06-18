import React, { useEffect, useRef, useState } from 'react';
import axios from 'axios';
import * as faceapi from 'face-api.js';

const CameraFeed = ({ onUserDetected = () => {}, onColorDetected = () => {} }) => {
    const videoRef = useRef(null);
    const [userDetected, setUserDetected] = useState(false);
    const [userLostTimeout, setUserLostTimeout] = useState(null);

    useEffect(() => {
        const loadModels = async () => {
            await faceapi.nets.tinyFaceDetector.loadFromUri('/models');
            console.log('모델이 로드되었습니다.');
        };

        const startVideo = async () => {
            try {
                const constraints = { video: { width: 1280, height: 720, facingMode: "user" } };
                const stream = await navigator.mediaDevices.getUserMedia(constraints);
                const video = videoRef.current;
                video.srcObject = stream;
                console.log('스트림이 비디오 소스 객체에 설정되었습니다.');

                video.onloadedmetadata = () => {
                    console.log('메타데이터가 로드되었습니다. 이제 비디오가 재생됩니다.');
                    video.play();
                    setupCanvas(video);
                };
            } catch (err) {
                console.error("미디어 장치 접근 오류:", err);
            }
        };

        loadModels().then(startVideo);

        return () => {
            const video = videoRef.current;
            if (video && video.srcObject) {
                video.srcObject.getTracks().forEach(track => track.stop());
            }
        };
    }, []);

    const setupCanvas = (video) => {
        const canvas = document.createElement('canvas');
        canvas.width = video.videoWidth;
        canvas.height = video.videoHeight;
        const context = canvas.getContext('2d');
        processVideo(video, canvas, context);
    };

    const processVideo = (video, canvas, context) => {
        let previousDetected = userDetected;
        const detectFaces = async () => {
            context.drawImage(video, 0, 0, canvas.width, canvas.height);
            const detections = await faceapi.detectAllFaces(video, new faceapi.TinyFaceDetectorOptions());
            const detected = detections.length > 0;
            if (detected !== previousDetected) {
                setUserDetected(detected);
                if (detected) {
                    console.log('얼굴이 감지되었습니다. 데이터를 전송합니다.');
                    canvas.toBlob(blob => {
                        sendFrameForUser(blob);
                        sendFrameForColor(blob);
                    }, 'image/jpeg');
                    if (userLostTimeout) {
                        clearTimeout(userLostTimeout);
                        setUserLostTimeout(null);
                    }
                } else {
                    console.log('얼굴이 감지되지 않았습니다. 블랙 스크린 타임아웃을 설정합니다.');
                    if (!userLostTimeout) {
                        const timeout = setTimeout(() => {
                            setUserDetected(false);
                            onUserDetected(false, "Guest", "Guest");
                            setUserLostTimeout(null);
                        }, 5000);
                        setUserLostTimeout(timeout);
                    }
                }
                previousDetected = detected;
            } else if (detected) {
                if (userLostTimeout) {
                    clearTimeout(userLostTimeout);
                    setUserLostTimeout(null);
                }
            }
            requestAnimationFrame(detectFaces);
        };
        detectFaces();
    };

    const sendFrameForUser = (blob) => {
        const formData = new FormData();
        formData.append('face_image', blob, 'frame.jpg');
        axios.post('https://jj.system32.kr/face', formData, {
            headers: { 'Content-Type': 'multipart/form-data' }
        })
        .then(response => {
            console.log('응답을 받았습니다:', response);
            const detectedFace = response.data.face;
            const detectedUsername = response.data.username;
            if (detectedFace && detectedFace !== "Unknown" && detectedUsername) {
                onUserDetected(true, detectedFace, detectedUsername);
            } else {
                onUserDetected(false, "Guest", "Guest");
            }
        })
        .catch(error => {
            console.error('프레임 전송 오류:', error);
        });
    };

    const sendFrameForColor = (blob) => {
        const formData = new FormData();
        formData.append('face_image', blob, 'frame.jpg');
        axios.post('https://jj.system32.kr/personal_color', formData, {
            headers: { 'Content-Type': 'multipart/form-data' }
        })
        .then(response => {
            console.log('응답을 받았습니다:', response);
            if (response.data.result === 'success') {
                onColorDetected(response.data.tone);
            }
        })
        .catch(error => {
            console.error('프레임 전송 오류:', error);
        });
    };

    return <video ref={videoRef} style={{ width: "100%", display: 'none' }} autoPlay />;
};

export default CameraFeed;
