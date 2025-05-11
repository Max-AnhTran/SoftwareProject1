import React, {useEffect, useState} from "react";
import {useParams, Link} from "react-router-dom";

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

const QuizzesByCategoryPage: React.FC = () => {
    const {id} = useParams<{id: string}>();
    const [quizzes, setQuizzes] = useState<Quiz[]>([]);
    const [category, setCategory] = useState<Category | null>(null);

    const fetchQuizzes = async () => {
        try {
            const res = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/categories/${id}/quizzes`);
            if (!res.ok) throw new Error(`Failed to load quizzes (${res.status})`);
            const data: Quiz[] = await res.json();
            setQuizzes(data);
            if (data.length > 0) {
                setCategory(data[0].category);
            } else {
                const catRes = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/categories`);
                if (catRes.ok) {
                    const all: Category[] = await catRes.json();
                    const found = all.find((c) => c.id.toString() === id);
                    setCategory(found || null);
                }
            }
        } catch (err) {
            console.error(err);
        }
    };

    useEffect(() => {
        fetchQuizzes();
    }, [id]);

    if (!category) return <div className="p-4">Loading...</div>;

    return (
        <div className="space-y-6 p-6 max-w-6xl mx-auto">
            <h1 className="text-3xl font-bold text-gray-800">{category.name}</h1>
            {category.description && <p className="text-gray-600 mb-6">{category.description}</p>}
            <div className="overflow-x-auto">
                <table className="min-w-full bg-white rounded-lg shadow">
                    <thead>
                        <tr className="bg-gray-100 text-left">
                            <th className="py-3 px-4">Name</th>
                            <th className="py-3 px-4">Description</th>
                            <th className="py-3 px-4">Course</th>
                            <th className="py-3 px-4">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {quizzes.map((q) => (
                            <tr key={q.id} className="border-b">
                                <td className="py-3 px-4 text-blue-600 hover:underline">
                                    <Link to={`/quizzes/${q.id}`}>{q.name}</Link>
                                </td>
                                <td className="py-3 px-4 text-gray-700">{q.description}</td>
                                <td className="py-3 px-4 text-gray-700">{q.courseCode}</td>
                                <td className="py-3 px-4">
                                    <Link to={`/quizzes/${q.id}/results`} className="text-indigo-600 hover:underline">
                                        See results
                                    </Link>
                                </td>
                            </tr>
                        ))}
                        {quizzes.length === 0 && (
                            <tr>
                                <td colSpan={4} className="py-6 px-4 text-center text-gray-500">
                                    No quizzes found in this category.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>

            {/* Button Row */}
            <div className="flex flex-wrap gap-4 mt-4">
                <Link to="/" className="px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-100 transition">
                    🏠 Back to Home
                </Link>
                <Link
                    to="/quizzes"
                    className="px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-100 transition"
                >
                    ⬅ Back to Quizzes
                </Link>
            </div>
        </div>
    );
};

export default QuizzesByCategoryPage;
