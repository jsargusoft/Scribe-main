import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  axios.defaults.withCredentials = true;
  const user = {
    email: email,
    password: password,
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/api/auth/login",user)

      if (response.status === 200) {
        console.log("Login successful", response.data);
        navigate("/story")
      }
    } catch (err) {
      console.error("Login failed", err);
      setError("Invalid email or password");
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-lightGray">
      <div className="w-full max-w-md p-8 space-y-4 bg-white rounded-lg shadow-lg">
        <h2 className="text-3xl font-bold text-center text-mediumBlue">Login</h2>
        
        {error && <p className="text-red-500 text-center">{error}</p>}

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label htmlFor="email" className="block text-lg text-darkCharcoal">
              Email
            </label>
            <input
              type="email"
              id="email"
              name="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-mediumBlue"
              required
            />
          </div>

          <div>
            <label htmlFor="password" className="block text-lg text-darkCharcoal">
              Password
            </label>
            <input
              type="password"
              id="password"
              name="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-mediumBlue"
              required
            />
          </div>

          <button
            type="submit"
            className="w-full py-3 bg-mediumBlue text-white rounded-lg hover:bg-lightBlue focus:outline-none"
          >
            Login
          </button>
        </form>

        <p className="text-center text-sm text-darkCharcoal mt-4">
          Don't have an account?{" "}
          <a href="/signup" className="text-mediumBlue hover:underline">
            Sign Up
          </a>
        </p>
      </div>
    </div>
  );
};

export default Login;
