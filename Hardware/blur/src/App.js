import React, { useState, useEffect } from 'react';
import './App.css';
import CameraFeed from './components/CameraFeed';
import DateTime from './components/DateTime';
import Weather from './components/Weather';
import CheerUp from './components/CheerUp';
import Login from './components/Login';
import Finance from './components/Finance';
import News from './components/News'
import Message from './components/Message';
import Traffic from './components/Traffic'
import DHT from './components/DHT';
import axios from 'axios';

function App() {
    const [widgets, setWidgets] = useState([]);
    const [showText, setShowText] = useState(false);
    const [userName, setUserName] = useState("Guest");
    const [userid, setUserId] = useState("Guest");
    const [isActive, setIsActive] = useState(false);

    useEffect(() => {
        const fetchWidgets = async () => {
            try {
                const response = await axios.get('https://jj.system32.kr/widgets_index');
                // const response = await axios.get('https://jj.system32.kr/get_widgets_custom/username=qqq');
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

    const handleUserDetection = async (active, name = "Guest", id = "Guest") => {
        setIsActive(active);
        setUserName(name);
        setUserId(id);
        try {
            const loginResponse = await axios.post('https://jj.system32.kr/get_widgets_custom/username=' + id);
            if(loginResponse.data.message === "Empty Database"){
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
            }else{
                const mappedWidgets = Object.entries(loginResponse.data.message).map(([type, position]) => ({
                    type,
                    row: position[0],
                    col: position[1]
                }));
                console.log(mappedWidgets);
                setWidgets(mappedWidgets);
            }
        } catch (error) {
            console.error('Error logging in:', error);
        }
    };

    return (
        <div className={`container ${showText ? 'show' : ''}`}>
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
                                        case 'Finance' : return <Finance key={`${row}-${col}`} />;
                                        case 'News' : return <News key={`${row}-${col}`} />;
                                        case 'Traffic' : return <Traffic key={`${row}-${col}`} />;
                                        case 'Message' : return <Message key={`${row}-${col}`} userName={userName}/>;
                                        case 'Room' : return <DHT key={`${row}-${col}`} />;
                                        default: return null;
                                    }
                                })()
                            ) : null}
                        </div>
                    ))}
                </div>
            ))}
            <CameraFeed onUserDetected={handleUserDetection} />
        </div>
    );
}

export default App;