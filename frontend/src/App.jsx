import React from "react";
import Login from './components/Login';
// import Register from './components/Register';
// import ErrorPage from "./components/ErrorPage";
// import UserProfile from "./components/UserProfile";
import Header from "./components/Header";
import MyStories from "./components/MyStories"

import { createBrowserRouter, RouterProvider, Outlet } from "react-router-dom";
import Stories from "./components/Stories";
import StoryDetail from "./components/StoryDetail";
import { ThemeProvider } from "./Context/ThemeContext";

const router = createBrowserRouter([
  {
    path : "/",
    element : <Login />
  },
  {
    path: "/header",
    element: <Header />,
  },
  {
    path: "/story",
    element: <Stories />,
  },
  {
    path: "/story/:title",
    element: <StoryDetail />,
  },
  {
    path : "/mystories",
    element: <MyStories />
  }

  // {
  //   path: "/story",
  //   element: <Stories />,
  // },
  // {
  //   path : "/story/:storyId",
  //   element: <StoryDetail />
  // },
  // {
  //   path: "/login",
  //   element: <Login />,
  // },
  // {
  //   path: "/register",
  //   element: <Register />,
  // },
  // {
  //   path: "/articles",
  //   element: <Articles />,
  // },
  // {
  //   path: "/profile",
  //   element: <UserProfile />
  // },
  // {
  //   path: "/articles/:article_title",
  //   element: <Dashboard />,
  // },
  // {
  //   path: "*",
  //   element: <ErrorPage />
  // },
])

export default function App() {
  return (
    <ThemeProvider>
      <RouterProvider router={router} />
    </ThemeProvider>
  );
}