import React, {useEffect, useState} from "react";
import {useParams, Link, useNavigate} from "react-router-dom";
import {PieChart, Pie, Cell, Legend, Tooltip, ResponsiveContainer} from "recharts";

interface Question {
    id: number;
    content: string;
    difficulty: "EASY" | "NORMAL" | "HARD";
    answerOptions: AnswerOption[];
}

interface AnswerOption {
    id: number;
    content: string;
}

interface Quiz {
    id: number;
    name: string;
    questions: Question[];
}

const COLORS = ["#22c55e", "#ef4444", "#9ca3af"]; // green, red, gray

const QuizDetailPage: React.FC = () => {
    const {id} = useParams<{id: string}>();
    const [quiz, setQuiz] = useState<Quiz | null>(null);
    const [selected, setSelected] = useState<Record<number, number | null>>({});
    const [feedback, setFeedback] = useState<Record<number, string>>({});
    const navigate = useNavigate();

    const fetchQuiz = async () => {
        try {
            const res = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/quizzes/published/${id}`);
            if (!res.ok) throw new Error(`Failed to load quiz (${res.status})`);
            const data: Quiz = await res.json();
            setQuiz(data);
        } catch (err) {
            console.error(err);
        }
    };

    useEffect(() => {
        fetchQuiz();
    }, [id]);

    // compute stats for current attempt
    const questions = quiz?.questions || [];
    const total = questions.length;
    const correctCount = Object.values(feedback).filter((f) => f.startsWith("✅")).length;
    const wrongCount = Object.values(feedback).filter((f) => f.startsWith("❌")).length;
    const missedCount = total - (correctCount + wrongCount);

    const chartData = [
        {name: "Correct", value: correctCount},
        {name: "Wrong", value: wrongCount},
        {name: "Missed", value: missedCount},
    ];

    return (
        <div className="flex flex-col lg:flex-row p-6 max-w-6xl mx-auto gap-8">
            {/* Left: Questions */}
            <div className="flex-1 space-y-8">
                <h1 className="text-2xl font-bold text-gray-800">Quiz Questions</h1>
                {quiz ? (
                    questions.map((q) => (
                        <QuestionItem
                            key={q.id}
                            question={q}
                            selected={selected[q.id] ?? null}
                            onSelect={(optId) => setSelected((s) => ({...s, [q.id]: optId}))}
                            feedback={feedback[q.id]}
                            onSubmit={async () => {
                                const optionId = selected[q.id];
                                if (optionId == null) return;
                                try {
                                    const res = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/answers/submit`, {
                                        method: "POST",
                                        headers: {"Content-Type": "application/json"},
                                        body: JSON.stringify({
                                            quizId: Number(id),
                                            questionId: q.id,
                                            answerOptionId: optionId,
                                        }),
                                    });
                                    if (!res.ok) throw new Error(`HTTP ${res.status}`);
                                    const result = (await res.json()) as {correct: boolean};
                                    setFeedback((f) => ({
                                        ...f,
                                        [q.id]: result.correct ? "✅ Correct!" : "❌ Wrong",
                                    }));
                                } catch (err) {
                                    console.error(err);
                                    setFeedback((f) => ({...f, [q.id]: "❌ Error submitting answer"}));
                                }
                            }}
                        />
                    ))
                ) : (
                    <p>Loading quiz…</p>
                )}

                <div className="flex space-x-4">
                    <Link
                        to={`/quizzes/${id}/results`}
                        className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition"
                    >
                        View Results
                    </Link>
                    <Link
                        to={`/quizzes/${id}/reviews`}
                        className="px-4 py-2 border border-blue-600 text-blue-600 rounded-lg hover:bg-blue-50 transition"
                    >
                        Review Quiz
                    </Link>
                </div>

                <button
                    onClick={() => navigate(-1)}
                    className="inline-block mt-4 px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-100 transition"
                >
                    ⬅ Back to Quizzes
                </button>
            </div>

            {/* Right: Pie Chart */}
            <div className="w-full lg:w-80 h-80">
                <h2 className="text-xl font-semibold mb-4">Current Attempt Statistics</h2>
                <ResponsiveContainer width="100%" height="100%">
                    <PieChart>
                        <Pie data={chartData} dataKey="value" nameKey="name" innerRadius="50%" outerRadius="80%" label>
                            {chartData.map((_, idx) => (
                                <Cell key={idx} fill={COLORS[idx]} />
                            ))}
                        </Pie>
                        <Legend verticalAlign="bottom" height={36} />
                        <Tooltip />
                    </PieChart>
                </ResponsiveContainer>
            </div>
        </div>
    );
};

interface QuestionItemProps {
    question: Question;
    selected: number | null;
    onSelect: (optionId: number) => void;
    feedback?: string;
    onSubmit: () => Promise<void>;
}

const QuestionItem: React.FC<QuestionItemProps> = ({question, selected, onSelect, feedback, onSubmit}) => {
    return (
        <div className="bg-white p-6 rounded-2xl shadow space-y-4">
            <div className="flex justify-between items-center">
                <h2 className="font-semibold text-lg">{question.content}</h2>
                <span className="text-sm font-medium text-gray-500 uppercase">{question.difficulty}</span>
            </div>

            <ul className="space-y-2">
                {question.answerOptions.map((opt) => (
                    <li key={opt.id}>
                        <label className="flex items-center space-x-3">
                            <input
                                type="radio"
                                name={`q-${question.id}`}
                                value={opt.id}
                                checked={selected === opt.id}
                                onChange={() => onSelect(opt.id)}
                                disabled={!!feedback}
                                className="form-radio text-blue-600"
                            />
                            <span>{opt.content}</span>
                        </label>
                    </li>
                ))}
            </ul>

            <div className="flex items-center space-x-4">
                <button
                    onClick={onSubmit}
                    disabled={selected == null || !!feedback}
                    className="px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 disabled:opacity-50 transition"
                >
                    Submit Your Answer
                </button>
                {feedback && (
                    <p className={`font-medium ${feedback.startsWith("✅") ? "text-green-600" : "text-red-600"}`}>
                        {feedback}
                    </p>
                )}
            </div>
        </div>
    );
};

export default QuizDetailPage;
