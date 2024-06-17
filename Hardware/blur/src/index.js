import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import { WeatherProvider } from './components/WeatherContext';
import App from './App';
import reportWebVitals from './reportWebVitals';
import 'bootstrap/dist/css/bootstrap.css';

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
    <React.StrictMode>
        <WeatherProvider>
            <App />
        </WeatherProvider>
    </React.StrictMode>
);

reportWebVitals();