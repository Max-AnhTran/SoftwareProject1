import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';

interface Question {
  id: number;
  content: string;
}

interface AnswerOption {
  id: number;
  content: string;
}

const QuizDetailPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [questions, setQuestions] = useState<Question[]>([]);
  // tracks the selected option per question (undefined before selection)
  const [selected, setSelected] = useState<Record<number, number | undefined>>({});
  const [feedback, setFeedback] = useState<Record<number, string>>({});

// separate the fetch and use the async await function format

  useEffect(() => {
    fetch(`http://localhost:8080/api/quizzes/${id}/questions`)
      .then(res => res.json())
      .then(data => setQuestions(data));
  }, [id]);

  const handleAnswer = async (questionId: number, optionId: number) => {
    setSelected(prev => ({ ...prev, [questionId]: optionId }));
    try {
      const res = await fetch('http://localhost:8080/api/answers/submit', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ questionId, answerOptionId: optionId, quizId: id }),
      });
      if (!res.ok) {
        throw new Error(`Failed to submit answer: ${res.status} ${res.statusText}`);
      }
      const result = await res.json();
      setFeedback(prev => ({
        ...prev,
        [questionId]: result.correct ? '✅ Correct!' : '❌ Wrong',
      }));
    } catch (error) {
      console.error('Error submitting answer:', error);
      setFeedback(prev => ({
        ...prev,
        [questionId]: '❌ Error submitting answer',
      }));
    }
  };

  return (
    <div className="space-y-8">
      <h1 className="text-2xl font-bold text-gray-800">Quiz Questions</h1>
      {questions.map(q => (
        <div key={q.id} className="bg-white p-6 rounded-2xl shadow">
          <h2 className="font-semibold text-lg mb-3">{q.content}</h2>
          <ul className="space-y-3">
            <AnswerList
              questionId={q.id}
              onAnswer={handleAnswer}
              selected={selected[q.id]}
            />
          </ul>
          {feedback[q.id] && (
            <p
              className={`mt-4 font-medium ${
                feedback[q.id].startsWith('✅') ? 'text-green-600' : 'text-red-600'
              }`}
            >
              {feedback[q.id]}
            </p>
          )}
        </div>
      ))}
      <div className="flex space-x-4">
        <Link
          to={`/quizzes/${id}/results`}
          className="px-4 py-2 bg-secondary-600 text-white rounded-lg hover:bg-secondary-700 transition"
        >
          Statistics For All Results
        </Link>
        <Link
          to={`/quizzes/${id}/reviews`}
          className="px-4 py-2 border border-secondary-600 text-secondary-600 rounded-lg hover:bg-secondary-50 transition"
        >
          Review Quiz
        </Link>
      </div>
        <Link to="/quizzes" className="inline-block mt-4 px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-100 transition">
           ⬅ Back to Quizzes
        </Link>
    </div>
  );
};

const AnswerList: React.FC<{
  questionId: number;
  onAnswer: (qid: number, optId: number) => void;
  selected: number | undefined;
}> = ({ questionId, onAnswer, selected }) => {
  const [opts, setOpts] = useState<AnswerOption[]>([]);
  useEffect(() => {
    fetch(`http://localhost:8080/api/questions/${questionId}/answers`)
      .then(res => res.json())
      .then(data => setOpts(data));
  }, [questionId]);

  return (
    <>
      {opts.map(o => (
        <li key={o.id}>
          <button
            disabled={selected != null}
            onClick={() => onAnswer(questionId, o.id)}
            className={`w-full text-left p-3 rounded-lg border ${
              selected === o.id ? 'border-primary-600 bg-primary-50' : 'border-gray-200 hover:bg-gray-50'
            } transition`}
          >
            {o.content}
          </button>
        </li>
      ))}
    </>
  );
};

export default QuizDetailPage;





