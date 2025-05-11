package haaga_helia.fi.SoftwareProject1.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "answer_submissions")
public class AnswerSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // which quiz this answer belongs to
    @ManyToOne(optional = false)
    @JoinColumn(name = "quiz_id")
    @JsonIgnore
    private Quiz quiz;

    // which question
    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id")
    @JsonIgnore
    private Question question;

    // whether the student's submitted answer was correct
    @Column(nullable = false)
    private boolean correct;

    @Column(nullable = false)
    private LocalDateTime submittedAt = LocalDateTime.now();

    public AnswerSubmission() {
    }

    public AnswerSubmission(Quiz quiz, Question question, boolean correct) {
        this.quiz = quiz;
        this.question = question;
        this.correct = correct;
    }

    // Getters & setters

    public Long getId() {
        return id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
