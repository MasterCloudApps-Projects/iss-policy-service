package es.urjc.code.policy.service.api.v1.commands.createoffer.dto;

import java.math.BigDecimal;

public class NumericQuestionAnswer extends QuestionAnswer<BigDecimal> {
    public NumericQuestionAnswer(String questionCode, BigDecimal answer) {
        super(questionCode, answer);
    }
}

