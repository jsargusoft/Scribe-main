import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import { useTheme } from "../Context/ThemeContext";
import axios from "axios";
import Header from "./Header";

const Stories = () => {
    const [stories, setStories] = useState([]);
    const { isDarkMode } = useTheme(); 

    useEffect(() => {
        axios.get("http://localhost:8080/story/getAll")
            .then((response) => {
                console.log(response.data);
                setStories(response.data);
            })
            .catch((error) => {
                console.error("Error fetching stories:", error);
            });
    }, []);

    return (
        <div className={`min-h-screen ${isDarkMode ? "bg-darkCharcoal text-lightGray" : "bg-lightGray text-darkCharcoal"}`}>
            <Header />
            <main className="px-6 py-12 space-y-8">

                <section className="text-center">
                    <h2 className={`text-4xl font-extrabold  ${isDarkMode ? "text-lightBlue" : "text-mediumBlue"} mb-4`}>
                        Your Story Awaits
                    </h2>
                    <p className="text-lg font-medium">
                        Welcome to <span className={` ${isDarkMode ? "text-lightBlue" : "text-mediumBlue"}`}>Scribe</span>, the ultimate platform for storytellers and readers!
                    </p>
                </section>

                {/* Story Cards Section */}
                <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-y-3">
                    {stories.map((story) => (
                        <div key={story.storyId} className={`p-4 rounded-lg shadow-md w-60 ${isDarkMode ? "bg-deepGray" : "bg-lightGray"}`}>
                            <img
                                src={story.image}
                                alt={story.title}
                                className="w-full h-50 object-cover rounded-md mb-4"
                            />
                            <h3 className={`text-xl font-bold ${isDarkMode ? "text-lightBlue" : "text-mediumBlue"} mb-2`}>{story.title}</h3>

                            <p className="text-sm mb-4">
                                {story.decription ? story.decription.substring(0, 100) + "..." : "No description available"}
                            </p>

                            {/* Link to the story detail page */}
                            <Link
                                to={`/story/${story.title}`}
                                state={{ story }}
                                className="text-turquoise hover:underline"
                            >
                                Read Full Story
                            </Link>
                        </div>
                    ))}
                </section>

            </main>

            {/* Footer */}
            <footer className="mt-12 text-center text-mutedGray">
                <p>&copy; {new Date().getFullYear()} Scribe. All Rights Reserved.</p>
            </footer>
        </div>
    );
};

export default Stories;
