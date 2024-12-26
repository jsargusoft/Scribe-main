import React, { useState } from "react";
import ProfilePic from "../assets/profilepic.jpeg";
import { MagnifyingGlassIcon, SunIcon, MoonIcon } from "@heroicons/react/24/outline";
import { useTheme } from "../Context/ThemeContext";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";

const Header = () => {
  const { isDarkMode, toggleTheme } = useTheme();
  const navigate = useNavigate();

  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const logout = async () => {
    try {
      await axios.post("http://localhost:8080/api/auth/logout", {});
      navigate("/");
    } catch (err) {
      console.error("Logout failed", err);
    }
  };

  const navigateToMyStories = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.get("http://localhost:8080/api/user");
      const userId = res.data.user_id;
      const response = await axios.get("http://localhost:8080/api/story", {
        params: { userId: userId },
      });
      navigate("/mystories", { state: { stories: response.data } });
    } catch (error) {
      console.error("Error fetching user details:", error);
    }
  };
  

  const toggleDropdown = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  return (
    <header
      className={`px-6 py-4 shadow-md flex items-center justify-between ${isDarkMode ? "bg-charcoal text-lightGray" : "bg-white text-darkGray"
        }`}
    >
      {/* Left Side (App Name) */}
      <div className="flex items-center space-x-4">
        <h1 className="text-3xl font-extrabold text-mutedBlue">
          <a href="/dashboard">Scribe</a>
        </h1>
      </div>

      {/* Center (Search Bar) */}
      <div className="flex items-center space-x-4 flex-1 justify-center">
        <div className="relative w-full max-w-lg">
          <input
            type="text"
            placeholder="Search Stories..."
            className={`w-full rounded-md py-2 pl-4 pr-10 focus:outline-none focus:ring-2 ${isDarkMode
              ? "bg-darkCharcoal text-lightGray border-borderGray"
              : "bg-lightBeige text-darkGray border-softGray"
              }`}
            aria-label="Search"
          />
          <MagnifyingGlassIcon
            className={`absolute top-2.5 right-3 h-5 w-5 ${isDarkMode ? "text-lightGray" : "text-mutedBlue"
              }`}
            aria-hidden="true"
          />
        </div>
      </div>

      {/* Right Side (Write dropdown, User Profile, Theme Toggle, and Logout) */}
      <div className="flex items-center space-x-6">
        {/* Write Dropdown */}
        <div className="relative">
          <div
            className="flex items-center cursor-pointer"
            onClick={toggleDropdown}
          >
              Write

            {isDropdownOpen ? (
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                strokeWidth={1.5}
                stroke="currentColor"
                className="w-6 h-6 ml-2"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  d="M19.5 15.75l-7.5-7.5-7.5 7.5"
                />
              </svg>
            ) : (
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                strokeWidth={1.5}
                stroke="currentColor"
                className="w-6 h-6 ml-2"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  d="m19.5 8.25-7.5 7.5-7.5-7.5"
                />
              </svg>
            )}
          </div>

          {/* Dropdown Menu */}
          {isDropdownOpen && (
            <div className={`absolute right-0 mt-2 w-48 ${isDarkMode ? "bg-darkCharCoal" : "bg-white"} border border-gray-300 rounded-lg shadow-lg z-50`}>
              <ul className="py-1">
                <li
                  className={`px-4 py-2 ${isDarkMode ? "hover:bg-gray-700" : "hover:bg-gray-100"} cursor-pointer`}
                  onClick={() => console.log("New Story Clicked")}
                >
                  Create Story
                </li>
                <li
                  className={`px-4 py-2 ${isDarkMode ? "hover:bg-gray-700" : "hover:bg-gray-100"} cursor-pointer`}
                  onClick={navigateToMyStories}
                >
                  My Stories
                </li>
              </ul>
            </div>
          )}
        </div>

        {/* Theme Toggle Button */}
        <button
          onClick={toggleTheme}
          className={`p-2 rounded-full ${isDarkMode ? "bg-gold text-charcoal" : "bg-charcoal text-lightGray"
            }`}
        >
          {isDarkMode ? (
            <SunIcon className="h-6 w-6" />
          ) : (
            <MoonIcon className="h-6 w-6" />
          )}
        </button>

        {/* Profile Avatar */}
        <a href="/story">
          <div className="relative">
            <img
              src={ProfilePic}
              alt="User Avatar"
              className="h-10 w-10 rounded-full border-2 border-mutedBlue object-cover"
            />
          </div>
        </a>

        {/* Logout Button */}
        <button
          onClick={logout}
          className="text-mutedBlue hover:text-mutedOrange focus:outline-none focus:ring-2 focus:ring-mutedBlue"
        >
          Logout
        </button>
      </div>
    </header>
  );
};

export default Header;
