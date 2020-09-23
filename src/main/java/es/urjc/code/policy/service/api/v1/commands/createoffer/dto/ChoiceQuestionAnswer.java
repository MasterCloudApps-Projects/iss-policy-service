package es.urjc.code.policy.service.api.v1.commands.createoffer.dto;

public class ChoiceQuestionAnswer extends QuestionAnswer<String> {
    public ChoiceQuestionAnswer(String questionCode, String answer) {
        super(questionCode, answer);
    }
}
