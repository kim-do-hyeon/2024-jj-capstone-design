import React, { useEffect, useRef, useState } from 'react';
import axios from 'axios';
import * as faceapi from 'face-api.js';

const CameraFeed = ({ onUserDetected }) => {
    const videoRef = useRef(null);
    const [userDetected, setUserDetected] = useState(false);
    const [userLostTimeout, setUserLostTimeout] = useState(null);

    useEffect(() => {
        const loadModels = async () => {
            await faceapi.nets.tinyFaceDetector.loadFromUri('/models');
            console.log('Models loaded');
        };

        const constraints = { video: { width: 1280, height: 720, facingMode: "user" } };
        loadModels().then(() => {
            navigator.mediaDevices.getUserMedia(constraints)
                .then((stream) => {
                    const video = videoRef.current;
                    video.srcObject = stream;
                    video.onloadedmetadata = () => {
                        video.play();
                        setupCanvas(video);
                    };
                })
                .catch(err => console.error("Error accessing media devices.", err));
        });
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
        function detectFaces() {
            context.drawImage(video, 0, 0, canvas.width, canvas.height);
            faceapi.detectAllFaces(video, new faceapi.TinyFaceDetectorOptions()).then(detections => {
                const detected = detections.length > 0;
                if (detected !== previousDetected) {
                    setUserDetected(detected);
                    if (detected) {
                        console.log('Face detected, sending data');
                        canvas.toBlob(blob => {
                            sendFrame(blob);
                        }, 'image/jpeg');
                        if (userLostTimeout) {
                            clearTimeout(userLostTimeout);
                            setUserLostTimeout(null);
                        }
                    } else {
                        console.log('No face detected, setting timeout for black screen');
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
            });
        }
        detectFaces();
    };

    const sendFrame = (blob) => {
        const formData = new FormData();
        formData.append('face_image', blob, 'frame.jpg');
        axios.post('https://jj.system32.kr/face', formData, {
            headers: { 'Content-Type': 'multipart/form-data' }
        })
        .then(response => {
            console.log('Response received:', response);
            if (response.data.face && response.data.username) {
                onUserDetected(true, response.data.face, response.data.username);
                setUserDetected(true);
            }
        })
        .catch(error => {
            console.error('Error sending frame:', error);
        });
    };

    return <video ref={videoRef} style={{ width: "100%", display: 'none' }} autoPlay />;
};

export default CameraFeed;