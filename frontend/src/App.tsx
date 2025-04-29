import React from 'react';
import { Routes, Route, Link, Navigate } from 'react-router-dom';

import HomePage from './pages/HomePage';
import QuizListPage from './pages/QuizListPage';
import QuizDetailPage from './pages/QuizDetailPage';
import QuizResultsPage from './pages/QuizResultsPage';
import QuizReviewPage from './pages/QuizReviewPage';

import './styles/global.css';

const App: React.FC = () => (
  <div className="min-h-screen flex flex-col">
    {/* Navbar */}
    <header className="bg-blue-600 shadow-md">
      <div className="container mx-auto px-6 py-4 flex items-center">
        <Link to="/" className="flex items-center">
          <img
            src="/Haagahelia-logo.png"
            alt="Haaga-Helia"
            className="h-10 w-auto"
          />
          <span className="ml-3 text-xl font-bold text-white">
            Student Dashboard
          </span>
        </Link>
        <nav className="ml-auto">
          <Link
            to="/quizzes"
            className="px-4 py-2 rounded-lg font-semibold text-white uppercase hover:bg-blue-700 transition"
          >
            Quizzes
          </Link>
        </nav>
      </div>
    </header>

    {/* Main content */}
    <main className="flex-grow container mx-auto px-6 py-8">
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/quizzes" element={<QuizListPage />} />
        <Route path="/quizzes/:id" element={<QuizDetailPage />} />
        <Route path="/quizzes/:id/results" element={<QuizResultsPage />} />
        <Route path="/quizzes/:id/reviews" element={<QuizReviewPage />} />
        <Route path="*" element={<Navigate to="/" />} />
      </Routes>
    </main>
  </div>
);

export default App;






