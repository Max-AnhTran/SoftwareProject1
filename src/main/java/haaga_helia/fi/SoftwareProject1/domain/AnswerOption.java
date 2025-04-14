package haaga_helia.fi.SoftwareProject1.domain;

import jakarta.persistence.*;

@Entity
public class AnswerOption {
    //not using an enum when a boolean is fine
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    private Boolean correct;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    
    public AnswerOption(String content, Boolean correct, Question question) {
        super();
        this.content = content;
        this.correct = correct;
        this.question = question;
    }

    public AnswerOption() {

    }

    public Long getId() {
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

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
    
}
