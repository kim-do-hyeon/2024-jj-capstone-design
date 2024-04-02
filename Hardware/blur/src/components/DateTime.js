import React, { useState, useEffect } from 'react';
import './DateTime.css'
import Start from './Start';

function DateTime() {
    const [currentDateTime, setCurrentDateTime] = useState(new Date());

    useEffect(() => {
    const interval = setInterval(() => {
        setCurrentDateTime(new Date());
    }, 1000);

    return () => clearInterval(interval);
    }, []);


    return (
    <div className="dateTime">
        <div className="date">
        {currentDateTime.getFullYear()}.{(currentDateTime.getMonth() + 1).toString().padStart(2, '0')}.{currentDateTime.getDate().toString().padStart(2, '0')}.
        </div>
        <div className="time">
        {currentDateTime.toLocaleTimeString()}
        </div>
    </div>
    );
}

export default DateTime;