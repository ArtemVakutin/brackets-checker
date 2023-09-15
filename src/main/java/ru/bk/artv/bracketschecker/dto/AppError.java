package ru.bk.artv.bracketschecker.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

/**
 * Класс AppError является объектом DTO, возвращающимся rest-контроллером в случае наличия
 * в запросе ожидаемых исключений.
 * */
@Schema(description = "Объект, передающийся в случае наличия ошибок в запросе")
public class AppError {

    /**
     *  Код ошибки
     *  */
    @Schema(description = "код ошибки",
            example = "400")
    private int statusCode;

    /**
     * Сообщение об ошибке
     * */
    @Schema(description = "сообщение, содержащие причину ошибки",
            example = "The text is blank")
    private String message;

    public AppError() {
    }

    public AppError(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AppErrorDTO{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppError appError = (AppError) o;
        return statusCode == appError.statusCode &&
                Objects.equals(message, appError.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, message);
    }
}
