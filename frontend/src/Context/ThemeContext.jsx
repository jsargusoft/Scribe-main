import React, { createContext, useContext, useState } from "react";

// Create a Context for the theme
const ThemeContext = createContext();

export const useTheme = () => {
    return useContext(ThemeContext); // Custom hook to consume the context
};

export const ThemeProvider = ({ children }) => {
    const [isDarkMode, setIsDarkMode] = useState(false);

    const toggleTheme = () => {
        setIsDarkMode(prevMode => !prevMode);
        document.documentElement.classList.toggle("dark", !isDarkMode);
    };

    return (
        <ThemeContext.Provider value={{ isDarkMode, toggleTheme }}>
            {children}
        </ThemeContext.Provider>
    );
};
