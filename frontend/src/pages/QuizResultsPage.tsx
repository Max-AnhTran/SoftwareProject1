import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';

interface QuestionStats {
  questionId: number;
  questionText: string;
  correctCount: number;
  wrongCount: number;
}

interface QuizResults {
  perQuestionStats: QuestionStats[];
}

const QuizResultsPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [r, setR] = useState<QuizResults | null>(null);

  useEffect(() => {
    const fetchResults = async () => {
      try {
        const res = await fetch(`/api/quizzes/${id}/results`);
        if (!res.ok) throw new Error(`Failed to fetch results (${res.status})`);
        const data: QuizResults = await res.json();
        setR(data);
      } catch (err) {
        console.error(err);
      }
    };
    fetchResults();
  }, [id]);

  if (!r) return <div className="p-4">Loading...</div>;

  // Derive stats
  const totalQuestions = r.perQuestionStats.length;
  const correctAnswers = r.perQuestionStats.reduce((sum, q) => sum + q.correctCount, 0);
  const wrongAnswers = r.perQuestionStats.reduce((sum, q) => sum + q.wrongCount, 0);
  const totalAnswers = correctAnswers + wrongAnswers;
  const totalAttempts = totalQuestions > 0
    ? Math.round(totalAnswers / totalQuestions)
    : 0;
  const missedQuestions = Math.max(0, totalAttempts * totalQuestions - totalAnswers);
  const correctPercentage = totalAnswers > 0
    ? (correctAnswers * 100) / totalAnswers
    : 0;

  return (
    <div className="space-y-6 p-6 max-w-4xl mx-auto">
      <h1 className="text-3xl font-bold text-gray-800">Quiz Results</h1>
      <div className="grid grid-cols-2 gap-6">
        <StatCard label="Total Attempts" value={totalAttempts} />
        <StatCard label="Total Answers" value={totalAnswers} />
        <StatCard
          label="Correct Answer Percentage"
          value={`${correctPercentage.toFixed(1)}%`}
        />
        <StatCard label="Correct Answers" value={correctAnswers} />
        <StatCard label="Wrong Answers" value={wrongAnswers} />
        <StatCard label="Missed Questions" value={missedQuestions} />
      </div>

      <h2 className="text-xl font-semibold mt-6">Per-Question Stats</h2>
      <ul className="space-y-4">
        {r.perQuestionStats.map((q) => (
          <li
            key={q.questionId}
            className="bg-white p-5 rounded-2xl shadow flex justify-between"
          >
            <p className="font-medium">{q.questionText}</p>
            <div className="text-gray-700">
              <span className="mr-4">✅ {q.correctCount}</span>
              <span>❌ {q.wrongCount}</span>
            </div>
          </li>
        ))}
      </ul>

      <Link
        to="/quizzes"
        className="inline-block mt-4 px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-100 transition"
      >
        ⬅ Back to Quizzes
      </Link>
    </div>
  );
};

interface StatCardProps {
  label: string;
  value: number | string;
  className?: string;
}

const StatCard: React.FC<StatCardProps> = ({ label, value, className = '' }) => (
  <div className={`bg-white p-5 rounded-2xl shadow ${className}`}>  
    <p className="text-gray-600">{label}</p>
    <p className="text-2xl font-semibold">{value}</p>
  </div>
);

export default QuizResultsPage;





