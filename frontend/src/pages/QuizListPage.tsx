import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

interface Category {
  id: number;
  name: string;
  description?: string;
}

interface Quiz {
  id: number;
  name: string;
  description: string;
  courseCode: string;
  category: Category;
}

const QuizListPage: React.FC = () => {
  const [quizzes, setQuizzes] = useState<Quiz[]>([]);

// check the link if /quizzes => /api/quizzes/published
// check link if /quizzes/category/:id => /api/quizzes/published/category/:id

  useEffect(() => {
    const fetchQuizzes = async () => {
      try {
        const res = await fetch('/api/quizzes/published');
        if (!res.ok) throw new Error(`Failed to fetch: ${res.status}`);
        const data: Quiz[] = await res.json();
        setQuizzes(data);
      } catch (err) {
        console.error(err);
      }
    };

    fetchQuizzes();
  }, []);

  return (
    <div className="p-6 max-w-6xl mx-auto">
      <h1 className="text-3xl font-bold mb-6 text-gray-800">Available Quizzes</h1>
      <ul className="grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
        {quizzes.map((q) => (
          <li
            key={q.id}
            className="bg-white rounded-2xl p-6 shadow hover:shadow-lg transform hover:-translate-y-1 transition"
          >
            {/* Category Badge */}
            <span className="inline-block mb-2 mr-2 px-3 py-1 text-sm font-medium text-blue-600 bg-blue-100 rounded-full">
              {q.category.name}
            </span>

            {/* Course Badge */}
            <span className="inline-block mb-2 px-3 py-1 text-sm font-medium text-green-600 bg-green-100 rounded-full">
              {q.courseCode}
            </span>

            {/* Title & Description */}
            <h2 className="text-xl font-semibold mb-2">{q.name}</h2>
            <p className="text-gray-600 mb-4">{q.description}</p>

            {/* Take Quiz Button */}
            <Link
              to={`/quizzes/${q.id}`}
              className="inline-block px-5 py-2 bg-blue-600 text-white font-medium rounded-lg shadow-md hover:bg-blue-700 transition"
            >
              Take Quiz
            </Link>
          </li>
        ))}
      </ul>

      {/* Sticky Back Button */}
      <div className="sticky bottom-4 flex justify-end">
        <Link
          to="/"
          className="inline-block mt-6 mr-4 px-4 py-2 rounded-lg text-gray-700 font-medium bg-gray-100 hover:bg-blue-100 hover:shadow-md transition-all duration-200"
        >
          🏠 Back to Home
        </Link>
      </div>
    </div>
  );
};

export default QuizListPage;




