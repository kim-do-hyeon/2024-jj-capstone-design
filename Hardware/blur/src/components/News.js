import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './News.css';

function News() {
    const [newsData, setNewsData] = useState([]);
    const [currentNewsIndex, setCurrentNewsIndex] = useState(0);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchNewsData = async () => {
            try {
                setLoading(true);  // Ensure loading state is set during fetch
                const { data } = await axios.get('http://127.0.0.1:5555/api/news');
                console.log(data);
                setNewsData(data.news_datas); // Assuming data.news_datas is an array of objects
                setLoading(false);
            } catch (error) {
                console.error('Error fetching News data:', error);
                setLoading(false);
            }
        };

        fetchNewsData(); // Initial load

        const fetchInterval = setInterval(fetchNewsData, 600000); // Fetch news data every 10 minutes
        return () => clearInterval(fetchInterval); // Cleanup interval on component unmount
    }, []);

    useEffect(() => {
        if (!loading && newsData.length > 0) {
            const displayInterval = setInterval(() => {
                setCurrentNewsIndex(currentIndex => {
                    const nextIndex = currentIndex + 1;
                    return nextIndex < newsData.length ? nextIndex : 0; // Loop back to the start if end is reached
                });
            }, 10000); // Change news item every 10 seconds

            return () => clearInterval(displayInterval); // Cleanup interval on component unmount
        }
    }, [loading, newsData.length]); // Depends on loading and newsData

    return (
        <div className="News">
            {loading ? "데이터 로드 중..." :
            <div className="news-item">
                {newsData.length > 0 && Object.keys(newsData[currentNewsIndex]).map((key, index) => (
                    <div key={index}>
                        <h3>{key}</h3>
                        <p>{newsData[currentNewsIndex][key]}</p>
                    </div>
                ))}
            </div>}
        </div>
    );
}

export default News;
