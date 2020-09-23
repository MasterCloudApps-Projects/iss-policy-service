package es.urjc.code.policy.service.api.v1.commands.createoffer.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class QuestionAnswer<T> {
    private String questionCode;
    private T answer;
    
    public QuestionAnswer() {

	}

    public QuestionAnswer(String questionCode, T answer) {
		this.questionCode = questionCode;
		this.answer = answer;
	}

	public String getQuestionCode() {
        return questionCode;
    }

    public T getAnswer() {
        return answer;
    }

    public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}

	public void setAnswer(T answer) {
		this.answer = answer;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof QuestionAnswer)) return false;

        QuestionAnswer that = (QuestionAnswer) o;

        return new EqualsBuilder()
                .append(questionCode, that.questionCode)
                .append(answer, that.answer)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(questionCode)
                .append(answer)
                .toHashCode();
    }
}
