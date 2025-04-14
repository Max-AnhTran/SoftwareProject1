package haaga_helia.fi.SoftwareProject1.domain;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name, description, courseCode;
    private Boolean published;

    //join columns and maybe change it from long to category, which will be defined later
    private Long teacher_id;
    @ManyToOne
    @JoinColumn(name = "categoryid")
    private Category category;
    public Quiz() {

    }
    public Quiz(String name, String description, String courseCode, Boolean published, 
            Category category) {
        super();
        this.name = name;
        this.description = description;
        this.courseCode = courseCode;
        this.published = published;
        this.category = category;
    }
    public Long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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
    public String getCourseCode() {
        return courseCode;
    }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    public Boolean getPublished() {
        return published;
    }
    public void setPublished(Boolean published) {
        this.published = published;
    }
    public Long getTeacher_id() {
        return teacher_id;
    }
    public void setTeacher_id(long teacher_id) {
        this.teacher_id = teacher_id;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    @JsonIgnore
    @OneToMany(mappedBy = "quiz")
    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    

    

}
