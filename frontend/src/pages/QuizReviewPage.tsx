import React, { useEffect, useState } from 'react'; 
import { useParams, Link } from 'react-router-dom';

interface Review { id: number; author: string; content: string; }

const QuizReviewPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [reviews, setReviews] = useState<Review[]>([]);
  const [author, setAuthor] = useState('');
  const [content, setContent] = useState('');
  const [editingId, setEditingId] = useState<number|null>(null);

  // Fetch reviews with async/await
  useEffect(() => {
    const fetchReviews = async () => {
      try {
        const res = await fetch(`/api/quizzes/${id}/reviews`);
        if (!res.ok) throw new Error(`Failed to load reviews (${res.status})`);
        const data: Review[] = await res.json();
        setReviews(data);
      } catch (err) {
        console.error(err);
      }
    };
    fetchReviews();
  }, [id]);

  const resetForm = () => {
    setEditingId(null);
    setAuthor('');
    setContent('');
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const method = editingId ? 'PUT' : 'POST';
    const endpoint = editingId
      ? `/api/quizzes/${id}/reviews/${editingId}`
      : `/api/quizzes/${id}/reviews`;
    try {
      const res = await fetch(endpoint, {
        method,
        headers: {'Content-Type':'application/json'},
        body: JSON.stringify({ author, content })
      });
      if (!res.ok) throw new Error(`HTTP ${res.status}`);
      const updated: Review = await res.json();
      if (editingId) {
        setReviews(reviews.map(r => r.id === updated.id ? updated : r));
      } else {
        setReviews([...reviews, updated]);
      }
      resetForm();
    } catch (err) {
      console.error('Failed to submit review:', err);
    }
  };

  return (
    <div className="p-6 max-w-3xl mx-auto space-y-6">
      <h1 className="text-3xl font-bold text-gray-800">Reviews for Quiz #{id}</h1>

      <form
        onSubmit={handleSubmit}
        className="bg-gray-50 p-6 rounded-2xl shadow space-y-4"
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
        <div className="flex space-x-4">
          <button
            type="submit"
            className="px-6 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700 transition"
          >
            {editingId ? 'Update Review' : 'Submit Review'}
          </button>
          <button
            type="button"
            onClick={resetForm}
            className="px-6 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300 transition"
          >
            Cancel
          </button>
        </div>
      </form>

      <div className="space-y-4">
        {reviews.map(r => (
          <div
            key={r.id}
            className="bg-gray-50 p-5 rounded-2xl shadow flex justify-between"
          >
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
                className="text-blue-600 hover:text-blue-700"
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

      <Link
        to="/quizzes"
        className="inline-block mt-4 px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-100 transition"
      >
        ⬅ Back to Quizzes
      </Link>
    </div>
  );
};

export default QuizReviewPage;
