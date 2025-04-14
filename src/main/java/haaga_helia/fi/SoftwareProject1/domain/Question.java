package haaga_helia.fi.SoftwareProject1.domain;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
@Entity
public class Question {
    public enum Difficulty {
        EASY,
        NORMAL, // honestly, i'd rather call this medium instead of normal
        HARD 
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    private Difficulty difficulty;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    public Question(){

    }
    public Question(String content, Difficulty difficulty, Quiz quiz) {
        super();
        this.content = content;
        this.difficulty = difficulty;
        this.quiz = quiz;
    }
    public long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Difficulty getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
    public Quiz getQuiz() {
        return quiz;
    }
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
    @JsonIgnore
    @OneToMany(mappedBy = "question")
    private List<AnswerOption> answers;
    
    

}
