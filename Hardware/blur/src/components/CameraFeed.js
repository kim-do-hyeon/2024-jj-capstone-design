import React, { useEffect, useRef } from 'react';
import axios from 'axios';

const CameraFeed = ({ onUserDetected }) => {
    const videoRef = useRef(null);

    useEffect(() => {
        const constraints = { video: { width: 1280, height: 720, facingMode: "user" } };

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
    }, []);

    const setupCanvas = (video) => {
        const canvas = document.createElement('canvas');
        canvas.width = video.videoWidth;
        canvas.height = video.videoHeight;

        const context = canvas.getContext('2d');
        startProcessing(video, canvas, context);
    };

    const startProcessing = (video, canvas, context) => {
        const interval = setInterval(() => {
            context.drawImage(video, 0, 0, canvas.width, canvas.height);
            canvas.toBlob(blob => {
                if (blob) {
                    sendFrame(blob);
                } else {
                    console.error("Failed to create Blob from canvas.");
                }
            }, 'image/jpeg');
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

    return <video ref={videoRef} style={{ width: "100%", display: "none" }} />;
};

export default CameraFeed;