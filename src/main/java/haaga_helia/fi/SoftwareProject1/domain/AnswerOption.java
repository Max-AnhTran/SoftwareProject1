package haaga_helia.fi.SoftwareProject1.domain;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "answer_options")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnswerOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    @NotBlank(message = "Answer option content is required")
    private String content;

    @Column(nullable = false)
    @JsonIgnore
    private boolean correct;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    @JsonIgnore
    private Question question;

    public AnswerOption(String content, boolean correct, Question question) {
        this.question = question;
        this.content = content;
        this.correct = correct;
    }

    public boolean getCorrect() {
        return correct;
    }
}

