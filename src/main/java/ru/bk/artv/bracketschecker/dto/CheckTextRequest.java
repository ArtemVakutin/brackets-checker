package ru.bk.artv.bracketschecker.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

/**
 * Класс DTO, используемый в качестве request и содержащий текстовое поле для проверки на наличие скобок
 * */
@Schema(description = "Запрос, содержащий текст")
public class CheckTextRequest {

    /**
     * Принимаемый текст
     * */
    @Schema(description = "Проверяемый текст",
            example = "Мы поехали в г. Калачинск (Омская область)")
    private String text;

    public CheckTextRequest() {
    }

    public CheckTextRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
                "text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckTextRequest that = (CheckTextRequest) o;
        return Objects.equals(text, that.text);
    }
}
