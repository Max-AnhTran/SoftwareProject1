### Project Backlog
https://github.com/users/zerogero/projects/1

### Deployed quiz management site
https://software-project-1-quiz-teacher-application-postgres.2.rahtiapp.fi/

### Deployed quiz application for students
https://software-project-1-quiz-student.onrender.com/

### Spring 1 Review
https://edu.flinga.fi/s/EZ8U9D3

### Spring 2 Review
https://edu.flinga.fi/s/EWEKZG8

### REST API Swagger documentation
http://software-project-1-quiz-teacher-application-postgres.2.rahtiapp.fi/swagger-ui/index.html

# Quizzer

**Quizzer** is a full-stack web application developed to help teachers create, manage, and share quizzes with their students. It enhances learning by providing interactive multiple-choice quizzes related to various course topics.

The platform consists of two dashboards:
- **Teacher Dashboard:** Enables teachers to create quizzes, add questions and answers, manage categories, and publish quizzes.
- **Student Dashboard:** Allows students to take quizzes, receive instant feedback, view quiz results, and share reviews.

## Features

### Teacher Features
- Create, edit, delete quizzes
- Add multiple-choice questions with difficulty levels (Easy, Normal, Hard)
- Add answer options with correctness flags
- Manage categories for organizing quizzes
- Publish/unpublish quizzes for student access

### Student Features
- View list of published quizzes
- Take quizzes and receive real-time feedback
- View quiz results with correct/wrong answers summary
- Review quizzes, edit and delete reviews
- See others' reviews for better engagement

## Technologies Used
- Frontend: React.js
- Backend: Spring Boot
- Database: PostgreSQL
- Hosting:

## Data Model

### Category
**Purpose:** Organizes quizzes under different subjects or themes.

**Attributes:**
* `id`: Unique identifier
* `name`: Category name (e.g., "Math")
* `description`: Optional explanation

**Relationships:**
* One category has many quizzes (One-to-Many)

### Quiz
**Purpose:** Represents a quiz created by a teacher.

**Attributes:**
* `id`: Unique identifier
* `name`: Quiz title
* `description`: Summary or topic explanation
* `courseCode`: Related course
* `published`: Boolean for visibility

**Relationships:**
* Belongs to one category (Many-to-One)
* Has many questions (One-to-Many)
* Receives many reviews (One-to-Many)
* Records many answer submissions (One-to-Many)

### Question
**Purpose:** A question in a quiz.

**Attributes:**
* `id`: Unique identifier
* `content`: Text of the question
* `difficulty`: Enum value (EASY, NORMAL, HARD)

**Relationships:**
* Belongs to one quiz (Many-to-One)
* Has many answer options (One-to-Many)
* Relates to many answer submissions (One-to-Many)

### AnswerOption
**Purpose:** Represents a possible answer to a question.

**Attributes:**
* `id`: Unique identifier
* `content`: Text of the answer
* `correct`: Boolean flag

**Relationships:**
* Belongs to one question (Many-to-One)

### AnswerSubmission
**Purpose:** A student’s submitted answer to a question.

**Attributes:**
* `id`: Unique identifier
* `correct`: Boolean if the answer was right
* `submittedAt`: Time of submission

**Relationships:**
* Belongs to one quiz (Many-to-One)
* Belongs to one question (Many-to-One)

### Review
**Purpose:** A student’s written review of a quiz.

**Attributes:**
* `id`: Unique identifier
* `author`: Student name
* `content`: Text review
* `createdAt`: Time of review

**Relationships:**
* Belongs to one quiz (Many-to-One)

![ER Diagram](https://www.mermaidchart.com/raw/32964988-49c0-4c6c-8eb9-6f27ff4c95ba?theme=light&version=v0.1&format=svg)

## Team Members
- [Ezza Jalal](https://github.com/EzzaJalal)
- [Anh Tran (Max)](https://github.com/Max-AnhTran)
- [Daniel Thagapsov](https://github.com/zerogero)

