import React, { useEffect, useRef, useState } from 'react';
import axios from 'axios';
import * as faceapi from 'face-api.js';

const CameraFeed = ({ onUserDetected }) => {
    const videoRef = useRef(null);

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
                    console.log('Stream set to video srcObject');

                    video.onloadedmetadata = () => {
                        console.log('Metadata loaded, video will play now');
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
        startProcessing(video, canvas, context);
    };

    const startProcessing = (video, canvas, context) => {
        const interval = setInterval(async () => {
            context.drawImage(video, 0, 0, canvas.width, canvas.height);
            const detections = await faceapi.detectAllFaces(video, new faceapi.TinyFaceDetectorOptions());
            if (detections.length > 0) {
                console.log('Face detected');
                canvas.toBlob(blob => {
                    sendFrame(blob);
                }, 'image/jpeg');
            }else {
                onUserDetected(false, "Guest", "Guest");
            }
        }, 10000);

        return () => clearInterval(interval);
    };

    const sendFrame = (blob) => {
        const formData = new FormData();
        formData.append('face_image', blob, 'frame.jpg');
        axios.post('https://jj.system32.kr/face', formData, {
            headers: { 'Content-Type': 'multipart/form-data' }
        })
        .then(response => {
            console.log('Response received:', response);
            const detectedFace = response.data.face;
            const detectedUsername = response.data.username;
            if (detectedFace && detectedFace !== "Unknown" && detectedUsername) {
                onUserDetected(true, detectedFace, detectedUsername);
            } else {
                onUserDetected(false, "Guest", "Guest");
            }
        })
        .catch(error => {
            console.error('Error sending frame:', error);
        });
    };

    return <video ref={videoRef} style={{ width: "100%", display: 'none'}} autoPlay />;
};

export default CameraFeed;
