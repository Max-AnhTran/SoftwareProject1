package haaga_helia.fi.SoftwareProject1.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long categoryid;
    private String name, description;
    public Category() {

    }
    public Category(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }
    public Long getCategoryid() {
        return categoryid;
    }
    public void setCategoryid(Long id) {
        this.categoryid = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<Quiz> quizzes;

    public List<Quiz> getQuizzes() {
        return quizzes;
    }
    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }
    
    
}
