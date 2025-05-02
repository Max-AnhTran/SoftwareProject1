import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

interface Category {
  id: number;
  name: string;
  description: string;
}

// Add the cards for the categories that go to the QuizListPage with all quizzes in that category

const HomePage: React.FC = () => {
  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const res = await fetch('/api/categories');
        if (!res.ok) throw new Error(`Failed to load categories: ${res.status}`);
        const data: Category[] = await res.json();
        setCategories(data);
      } catch (err) {
        console.error(err);
      }
    };
    fetchCategories();
  }, []);

  const features = [
    { img: '/Quiz.png', label: 'Take Quizzes' },
    { img: '/Knowledge.png', label: 'Gain Knowledge' },
    { img: '/Time.png', label: 'Track Progress' },
    { img: '/Thinking.png', label: 'Improve Thinking' },
    { img: '/e-learning-icon-design-vector.png', label: 'E-Learning' },
    { img: '/Astronaut.png', label: 'Reach New Heights' },
  ];

  const currentYear = new Date().getFullYear();

  return (
    <div className="space-y-16">

      {/* Hero Section */}
      <section
        className="relative h-[70vh] flex items-center justify-center text-center px-4"
        style={{
          backgroundImage: "url('/Home.jpg')",
          backgroundSize: 'cover',
          backgroundPosition: 'center',
        }}
      >
        <div className="absolute inset-0 bg-black opacity-50" />
        <div className="relative z-10 max-w-2xl">
          <h1 className="text-5xl sm:text-6xl font-extrabold text-white mb-4">
            Welcome to Quizzer
          </h1>
          <p className="text-lg sm:text-xl text-gray-200 italic mb-8">
            “Learning is not attained by chance, it must be sought for with ardour and diligence.”
          </p>
          <Link
            to="/quizzes"
            className="inline-block px-8 py-3 bg-blue-600 text-white font-semibold rounded-full shadow-lg hover:bg-blue-700 transition"
          >
            Try Now
          </Link>
        </div>
      </section>

      {/* Categories Section */}
      <section className="max-w-6xl mx-auto px-4">
        <h2 className="text-3xl font-bold text-center text-gray-800 mb-8">
            Browse By Categories
        </h2>
        <div className="grid gap-8 sm:grid-cols-2 lg:grid-cols-3">
          {categories.map((cat) => (
        <Link
             key={cat.id}
             to={`/categories/${cat.id}`}
             className="
               bg-white 
               border 
               border-gray-200 
               p-8 
               rounded-lg 
               shadow-md 
               hover:shadow-xl 
               transform 
               hover:-translate-y-2 
               transition-all 
               duration-300
             "
           >
             <h3 className="text-2xl font-semibold text-blue-600 mb-3">
               {cat.name}
             </h3>
             <p className="text-gray-500 leading-relaxed">
               {cat.description}
             </p>
           </Link>
          ))}
        </div>
      </section>

      {/* Features (“Why Choose Quizzer?”) */}
      <section className="bg-gray-50 py-12">
        <div className="container mx-auto px-4">
          <h2 className="text-3xl font-bold text-center text-gray-800 mb-8">
            Why Choose Quizzer?
          </h2>
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
            {features.map((f, idx) => (
              <div
                key={idx}
                className="bg-white p-6 rounded-xl shadow-lg flex flex-col items-center"
              >
                <img src={f.img} alt={f.label} className="h-15 w-20 mb-4" />
                <p className="text-center text-gray-700 font-medium">
                  {f.label}
                </p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-gray-100 text-center py-4">
        <p className="text-gray-600 text-sm">
          © {currentYear} Quizzer. All rights reserved.
        </p>
      </footer>
    </div>
  );
};

export default HomePage;
