import React, { useState, useEffect } from 'react';
import './App.css';
import CameraFeed from './components/CameraFeed';
import DateTime from './components/DateTime';
import Weather from './components/Weather';
import CheerUp from './components/CheerUp';
import Login from './components/Login';
import axios from 'axios';

function App() {
    const [widgets, setWidgets] = useState([]);
    const [showText, setShowText] = useState(false);
    const [userName, setUserName] = useState("Guest");
    const [isActive, setIsActive] = useState(false);

    useEffect(() => {
        const fetchWidgets = async () => {
            try {
                const response = await axios.get('https://jj.system32.kr/widgets_index');
                const messageOnly = response.data.message;
                const mappedWidgets = Object.entries(messageOnly).map(([type, position]) => ({
                    type,
                    row: position[0],
                    col: position[1]
                }));
                setWidgets(mappedWidgets);
            } catch (error) {
                console.error('Error fetching widgets:', error);
            }
        };

        fetchWidgets();
        const timer = setTimeout(() => setShowText(true), 1000);
        return () => clearTimeout(timer);
    }, []);

    const handleUserDetection = (active, name = "Guest") => {
        setIsActive(active);
        setUserName(name);
    };

    return (
        <div className={`container ${showText ? 'show' : ''}`}>
            <CameraFeed onUserDetected={handleUserDetection} />
            {[1, 2, 3].map(row => (
                <div className="row" key={row} style={{ height: "330px" }}>
                    {[1, 2, 3, 4].map(col => (
                        <div className="col" key={col}>
                            {widgets.find(widget => widget.row === row && widget.col === col) ? (
                                (() => {
                                    const widget = widgets.find(widget => widget.row === row && widget.col === col);
                                    switch (widget.type) {
                                        case 'DateTime': return <DateTime key={`${row}-${col}`} />;
                                        case 'Weather': return <Weather key={`${row}-${col}`} />;
                                        case 'CheerUp': return <CheerUp key={`${row}-${col}`} userName={userName} />;
                                        case 'Login': return <Login key={`${row}-${col}`} />;
                                        default: return null;
                                    }
                                })()
                            ) : null}
                        </div>
                    ))}
                </div>
            ))}
            {!isActive && <Login />}
        </div>
    );
}

export default App;