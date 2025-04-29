package haaga_helia.fi.SoftwareProject1.domain.dto;

public class QuestionStatsDTO {
    private Long questionId;
    private String questionText;
    private long correctCount;
    private long wrongCount;

    public QuestionStatsDTO(Long questionId, String questionText, long correctCount, long wrongCount) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.correctCount = correctCount;
        this.wrongCount = wrongCount;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public long getCorrectCount() {
        return correctCount;
    }

    public long getWrongCount() {
        return wrongCount;
    }

    @Override
    public String toString() {
        return "QuestionStatsDTO{" +
                "questionId=" + questionId +
                ", questionText='" + questionText + '\'' +
                ", correctCount=" + correctCount +
                ", wrongCount=" + wrongCount +
                '}';
    }
}
