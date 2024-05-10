import React, { createContext, useState, useContext } from 'react';

const WeatherContext = createContext(null);

export const useWeather = () => useContext(WeatherContext);

export const WeatherProvider = ({ children }) => {
    const [location, setLocation] = useState({ latitude: null, longitude: null });

    return (
        <WeatherContext.Provider value={{ location, setLocation }}>
            {children}
        </WeatherContext.Provider>
    );
};
