package haaga_helia.fi.SoftwareProject1.domain.dto;

import java.util.List;

public class QuizResultsDTO {
    private Long quizId;
    private long totalAnswers;
    private double correctPercentage;
    private List<QuestionStatsDTO> perQuestionStats;

    public QuizResultsDTO(Long quizId, long totalAnswers, double correctPercentage,
            List<QuestionStatsDTO> perQuestionStats) {
        this.quizId = quizId;
        this.totalAnswers = totalAnswers;
        this.correctPercentage = correctPercentage;
        this.perQuestionStats = perQuestionStats;
    }

    public Long getQuizId() {
        return quizId;
    }

    public long getTotalAnswers() {
        return totalAnswers;
    }

    public double getCorrectPercentage() {
        return correctPercentage;
    }

    public List<QuestionStatsDTO> getPerQuestionStats() {
        return perQuestionStats;
    }

    @Override
    public String toString() {
        return "QuizResultsDTO{" +
                "quizId=" + quizId +
                ", totalAnswers=" + totalAnswers +
                ", correctPercentage=" + correctPercentage +
                ", perQuestionStats=" + perQuestionStats +
                '}';
    }
}
