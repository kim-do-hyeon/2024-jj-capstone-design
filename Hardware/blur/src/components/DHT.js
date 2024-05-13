import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './DHT.css';

function DHT() {
    const [tempData, setTempData] = useState([]);
    const [humiData, setHumiData] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchDHTData = async () => {
            try {
                setLoading(true);  // Ensure loading state is set during fetch
                const { data } = await axios.get('http://127.0.0.1:5000/api/dht');
                console.log(data);
                setTempData(data.temp)
                setHumiData(data.humi)
                setLoading(false);
            } catch (error) {
                console.error('Error fetching DHT data:', error);
                setLoading(false);
            }
        };

        fetchDHTData(); // Initial load

        const fetchInterval = setInterval(fetchDHTData, 10000);
        return () => clearInterval(fetchInterval);
    }, []);

    return (
        <div className="DHT">
            {loading ? "데이터 로드 중..." :
            <div>
                방온도 : {tempData}<br />
                방습도 : {humiData}
            </div>}
        </div>
    );
}

export default DHT;
