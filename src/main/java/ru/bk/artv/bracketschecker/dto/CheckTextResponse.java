package ru.bk.artv.bracketschecker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Класс DTO, используемый в качестве request и содержащий текстовое поле для проверки на наличие скобок
 * */
@Schema(description = "Ответ на запрос на проверку скобок")
public class CheckTextResponse {

    /**
     * Принимает значение true в случае, если переданный текст корректно прошел проверку на наличие скобок и false, если
     * не прошел
     * */
    @JsonProperty("isCorrect")
    @Schema(name = "isCorrect",
            description = "true - означает, что скобки расставлены правильно, false - неправильно",
            example = "true")
    private boolean correct;

    public CheckTextResponse() {
    }

    public CheckTextResponse(boolean correct) {
        this.correct = correct;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    @Override
    public String toString() {
        return "AnswerDTO{" +
                "isCorrect=" + correct +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckTextResponse answerDTO = (CheckTextResponse) o;
        return correct == answerDTO.correct;
    }
}
