import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';

interface Review { id: number; author: string; content: string; }

const QuizReviewPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [reviews, setReviews] = useState<Review[]>([]);
  const [author, setAuthor] = useState('');
  const [content, setContent] = useState('');
  const [editingId, setEditingId] = useState<number|null>(null);

  useEffect(() => {
    fetch(`/api/quizzes/${id}/reviews`)
      .then(r => r.json())
      .then(setReviews);
  }, [id]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const method = editingId ? 'PUT' : 'POST';
    const endpoint = editingId
      ? `/api/quizzes/${id}/reviews/${editingId}`
      : `/api/quizzes/${id}/reviews`;
    const res = await fetch(endpoint, {
      method, headers: {'Content-Type':'application/json'},
      body: JSON.stringify({ author, content })
    });
    const updated = await res.json();
    if (editingId) {
      setReviews(reviews.map(r => r.id === updated.id ? updated : r));
      setEditingId(null);
    } else {
      setReviews([...reviews, updated]);
    }
    setAuthor(''); setContent('');
  };

  return (
    <div className="space-y-6">
      <h1 className="text-3xl font-bold text-gray-800">Reviews for Quiz #{id}</h1>

      <form
        onSubmit={handleSubmit}
        className="bg-white p-6 rounded-2xl shadow space-y-4"
      >
        <input
          type="text"
          placeholder="Your name"
          value={author}
          onChange={e => setAuthor(e.target.value)}
          className="w-full border-gray-200 rounded-lg p-3 focus:ring-primary-500 focus:border-primary-500"
          required
        />
        <textarea
          placeholder="Your review"
          value={content}
          onChange={e => setContent(e.target.value)}
          className="w-full border-gray-200 rounded-lg p-3 focus:ring-primary-500 focus:border-primary-500"
          rows={4}
          required
        />
        <button
          type="submit"
          className="px-6 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700 transition"
        >
          {editingId ? 'Update Review' : 'Submit Review'}
        </button>
      </form>

      <div className="space-y-4">
        {reviews.map(r => (
          <div key={r.id} className="bg-white p-5 rounded-2xl shadow flex justify-between">
            <div>
              <p className="font-medium">{r.author}</p>
              <p className="text-gray-700">{r.content}</p>
            </div>
            <div className="flex space-x-3">
              <button
                onClick={() => {
                  setEditingId(r.id);
                  setAuthor(r.author);
                  setContent(r.content);
                }}
                className="text-secondary-600 hover:text-secondary-700"
              >
                Edit
              </button>
              <button
                onClick={async () => {
                  await fetch(`/api/quizzes/${id}/reviews/${r.id}`, { method: 'DELETE' });
                  setReviews(reviews.filter(x => x.id !== r.id));
                }}
                className="text-red-600 hover:text-red-700"
              >
                Delete
              </button>
            </div>
          </div>
        ))}
      </div>

        <Link to="/quizzes" className="inline-block mt-4 px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-100 transition">
          ⬅ Back to Quizzes
        </Link>
    </div>
  );
};

export default QuizReviewPage;
