package haaga_helia.fi.SoftwareProject1.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByCategory(Category category);
    List<Quiz> findByPublished(boolean published);
    List<Quiz> findByCourseCode(String courseCode);
    List<Quiz> findAllByOrderByCategory_NameAsc();
    List<Quiz> findByCategoryId(Long categoryId);
    Quiz findByCourseCodeAndPublished(String courseCode, boolean b);
    List<Quiz> findByCategoryAndPublished(Category category, boolean published);
}
