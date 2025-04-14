package haaga_helia.fi.SoftwareProject1.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
public interface AnswerOptionRepository extends CrudRepository<AnswerOption, Long>{
    List<AnswerOption> findByQuestion(Question question);
}
