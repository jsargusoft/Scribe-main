import React from 'react';
import { useLocation } from "react-router-dom";
import Header from "./Header";
import { useTheme } from "../Context/ThemeContext";

const MyStories = () => {
  const location = useLocation();
  const stories = location.state?.stories || [];
  const { isDarkMode } = useTheme();

  return (
    <>
      <Header />
      <div className={`${isDarkMode ? "bg-darkCharcoal" : "bg-lightGray"} p-6`}>
        <h1 
          className={`text-3xl font-bold mb-6 text-center ${
            isDarkMode ? "text-lightGray" : "text-darkCharcoal"
          }`}
        >
          My Stories
        </h1>

        <div className="flex flex-col max-w-[800px] mx-auto">
          {stories.length > 0 ? (
            stories.map((story) => (
            
              <div
                key={story.story_id}
                className={`${
                  isDarkMode ? "bg-white/5" : "bg-white"
                } mb-4 rounded-lg overflow-hidden hover:shadow-lg transition-shadow duration-300 flex items-start`}
              >
                {/* Image Section */}
                <div className="w-[140px] h-auto flex-shrink-0">
                  <img
                    src={story.image}
                    alt={story.title}
                    className="w-full h-full rounded-md"
                  />
                </div>

                {/* Content Section */}
                <div className="flex-1 ml-4 min-h-[160px] flex flex-col p-4">
                  <h2 
                    className={`text-xl font-semibold ${
                      isDarkMode ? "text-lightGray" : "text-gray-800"
                    }`}
                  >
                    {story.title}
                  </h2>
                  <p 
                    className={`${
                      isDarkMode ? "text-gray-300" : "text-gray-600"
                    } text-base mt-2 mb-auto`}
                  >
                    {story.description ? story.description.substring(0, 150): "No description available"}
                  </p>
                  
                  <div className="mt-4">
                    <p 
                      className={`${
                        isDarkMode ? "text-gray-400" : "text-gray-500"
                      } text-xs mb-2`}
                    >
                      Created At: {new Date(story.created_at).toLocaleDateString()}
                    </p>
                    <div className="flex justify-between items-center">
                      <span
                        className="px-3 py-2 my-4 text-xs bg-red-100 text-red-600 rounded-full"
                      >
                        In Progress
                      </span>
                      <button 
                        className="text-blue-500 hover:underline text-sm"
                      >
                        View Story
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            ))
          ) : (
            <p className={`text-center ${
              isDarkMode ? "text-gray-400" : "text-gray-500"
            }`}>
              No stories available.
            </p>
          )}
        </div>
      </div>
    </>
  );
};

export default MyStories;