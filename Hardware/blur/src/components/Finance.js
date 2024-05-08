import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Finance.css';

function Finance() {
    const [kospiValue, setKospiValue] = useState("");
    const [kosdaqValue, setKosdaqValue] = useState("");
    const [rateValue, setRateValue] = useState("");
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchFinanceData = async () => {
            try {
                const { data } = await axios.get('http://127.0.0.1:5000/api/finance');
                setKospiValue(data.kospi);
                setKosdaqValue(data.kosdaq);
                setRateValue(data.rate);
                setLoading(false);
            } catch (error) {
                console.error('Error fetching finance data:', error);
                setLoading(false);
            }
        };

        const getFetchInterval = () => {
            const now = new Date();
            const hours = now.getHours();
            // 오전 9시부터 오후 5시 사이 (24시간제, 9 <= hours < 17)
            if (hours >= 9 && hours < 17) {
                return 1000; // 1초마다
            } else {
                return 3600000; // 1시간마다
            }
        };

        fetchFinanceData(); // 초기 로드
        const intervalId = setInterval(fetchFinanceData, getFetchInterval());

        // 시간에 따라 갱신 주기를 변경하기 위해 매분마다 갱신 주기 체크
        const checkInterval = setInterval(() => {
            clearInterval(intervalId); // 현재 인터벌 클리어
            // 새로운 인터벌 설정
            const newIntervalId = setInterval(fetchFinanceData, getFetchInterval());
            // 클리어할 인터벌 업데이트
            clearInterval(checkInterval);
            setInterval(() => {
                clearInterval(newIntervalId); // 새 인터벌 클리어
                setInterval(fetchFinanceData, getFetchInterval()); // 최신 인터벌 재설정
            }, 60000); // 매분 체크
        }, 60000);

        return () => {
            clearInterval(intervalId);
            clearInterval(checkInterval);
        };
    }, []);

    return (
        <div className="Finance">
            {loading ? "데이터 로드 중..." :
            <div>
                코스피 : {kospiValue}<br />
                코스닥 : {kosdaqValue}<br />
                환율 : {rateValue}
            </div>}
        </div>
    );
}

export default Finance;
