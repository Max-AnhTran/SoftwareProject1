import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';

interface QuestionStats {
  questionId: number;
  questionText: string;
  correctCount: number;
  wrongCount: number;
}
interface QuizResults {
  totalAnswers: number;
  correctPercentage: number;
  perQuestionStats: QuestionStats[];
}

const QuizResultsPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [r, setR] = useState<QuizResults|null>(null);

  useEffect(() => {
    fetch(`/api/quizzes/${id}/results`)
      .then(res => res.json())
      .then(setR);
  }, [id]);

  if (!r) return <div className="p-4">Loading...</div>;

  return (
    <div className="space-y-6">
      <h1 className="text-3xl font-bold text-gray-800">Quiz Results</h1>
      <div className="flex items-center space-x-12">
        <div>
          <p className="text-gray-600">Total Answers</p>
          <p className="text-2xl font-semibold">{r.totalAnswers}</p>
        </div>
        <div>
          <p className="text-gray-600">Correct %</p>
          <p className="text-2xl font-semibold">{r.correctPercentage.toFixed(1)}%</p>
        </div>
      </div>

      <h2 className="text-xl font-semibold">Per-Question Stats</h2>
      <ul className="space-y-4">
        {r.perQuestionStats.map(q => (
          <li
            key={q.questionId}
            className="bg-white p-5 rounded-2xl shadow flex justify-between"
          >
            <div>
              <p className="font-medium">{q.questionText}</p>
            </div>
            <div className="text-gray-700">
              <span className="mr-4">✅ {q.correctCount}</span>
              <span>❌ {q.wrongCount}</span>
            </div>
          </li>
        ))}
      </ul>

      <Link to="/quizzes" className="inline-block mt-4 px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-100 transition">
        ⬅ Back to Quizzes
      </Link>
    </div>
  );
};

export default QuizResultsPage;




