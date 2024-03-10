import React, { useState, useEffect } from 'react';
import './DateTime.css'

function DateTime() {
    const [currentDateTime, setCurrentDateTime] = useState(new Date());

    useEffect(() => {
    const interval = setInterval(() => {
        setCurrentDateTime(new Date());
    }, 1000);

    return () => clearInterval(interval);
    }, []);

    const year = currentDateTime.getFullYear();
    const month = (currentDateTime.getMonth() + 1).toString().padStart(2, '0');
    const day = currentDateTime.getDate().toString().padStart(2, '0');

    return (
    <div className="dateTime">
        <div className="date">
        {year}.{month}.{day}.
        </div>
        <div className="time">
        {currentDateTime.toLocaleTimeString()}
        </div>
    </div>
    );
}

export default DateTime;