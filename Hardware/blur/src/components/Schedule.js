import React, { useEffect, useState } from 'react';
import './Schedule.css';
import axios from 'axios';

const Schedule = ({ username, localdate }) => {
    const [schedule, setSchedule] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchSchedule = async () => {
            try {
                const response = await axios.get('https://jj.system32.kr/daily/view_rasp', {
                    params: {
                        username: username,
                        localdate: localdate
                    }
                });
                if (response.data.result === "success" && response.data.type === "todo_view") {
                    let items = response.data.message.split('\n');
                    if (items.length > 1) {
                        items = items.slice(0, -1);
                    }
                    setSchedule(items);
                    setError(null);
                } else {
                    setSchedule([]);
                    setError('Failed to fetch schedule');
                }
            } catch (error) {
                console.error('Error fetching schedule:', error);
                setError('Error fetching schedule: ' + error.message);
                setSchedule([]);
            } finally {
                setLoading(false);
            }
        };

        fetchSchedule();
    }, [username, localdate]);

    return (
        <div className="schedule-container">
            <div className="schedule-box">
                <h2>{localdate}</h2>
                <h3>Today's Schedule</h3>
                {loading ? (
                    <p>Loading...</p>
                ) : error ? (
                    <p className="error">{error}</p>
                ) : schedule.length > 0 ? (
                    <ul>
                        {schedule.map((item, index) => (
                            <li key={index}>{item}</li>
                        ))}
                    </ul>
                ) : (
                    <p>No schedule available for this date.</p>
                )}
            </div>
        </div>
    );
};

export default Schedule;