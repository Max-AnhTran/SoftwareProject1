package haaga_helia.fi.SoftwareProject1.domain;

import lombok.*;

import java.util.List;

// import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "quizzes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Quiz name is required")
    private String name;

    @Column(length = 255)
    private String description;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "Course code is required")
    private String courseCode;

    @Column(nullable = false)
    private boolean published;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Question> questions;

    // @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    // @JsonIgnore
    // private List<Review> reviews;

    @Override
    public String toString() {
        return "Quiz{id=" + id + ", name='" + name + "'}"; // Avoid referencing other entities here
    }
}
