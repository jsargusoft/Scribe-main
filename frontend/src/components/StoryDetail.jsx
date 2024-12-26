import { useLocation, useParams } from "react-router-dom";
import { useTheme } from "../Context/ThemeContext";
import Header from "./Header";

const StoryDetail = () => {
    const { state } = useLocation();
    const { isDarkMode } = useTheme();
    // const { storyId } = useParams();
    const story = state?.story; 

    const handleAddToReadingList = () => {
        console.log("Added to reading list:", story);
        alert(`${story.title} has been added to your reading list!`);
    };

    if (!story) {
        return <p>Error: Story data not available. Please go back and select a story again.</p>;
    }

    return (
        <div className={`min-h-screen ${isDarkMode ? "bg-darkCharcoal text-lightGray" : "bg-lightGray text-darkCharcoal"}`}>
            <Header />
            <main className="px-6 py-12 space-y-8">
                {/* Top Section */}
                <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
                    {/* Image Section */}
                    <div className="flex justify-center items-center">
                        <img
                            src={story.image}
                            alt={story.title}
                            className="max-w-full max-h-96 object-cover rounded-lg shadow-md"
                        />
                    </div>

                    {/* Details Section */}
                    <div className={`space-y-4  ${isDarkMode ? "text-lightBeige" : "text-deepGray"}`}>
                        <h1 className="text-4xl font-extrabold">{story.title}</h1>
                        <p className="text-lg">
                            <strong>Author:</strong> {story.author_name}
                        </p>
                        <p className="text-lg">
                            <strong>Genres:</strong> {story.genres.map((genre) => genre.name).join(", ")}
                        </p>
                        <p className="text-lg">
                            <strong>Kids Appropriate:</strong> {story.isKidsAppropriate ? "Yes" : "No"}
                        </p>
                        {/* Add to Reading List Button */}
                        <button
                            onClick={handleAddToReadingList}
                            className="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition duration-300"
                        >
                            Add to Reading List
                        </button>
                    </div>
                </div>

                {/* Bottom Section */}
                <div className={`${isDarkMode ? "bg-darkCharcoal text-lightGray" : "bg-lightGray text-darkCharcoal"} p-6 rounded-lg shadow-md`}>
                    <h2 className="text-2xl font-bold mb-4">Description</h2>
                    <p className="text-lg">{story.decription}</p>
                </div>
            </main>
        </div>
    );
};

export default StoryDetail;
