package ru.bk.artv.bracketschecker.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bk.artv.bracketschecker.dto.AppError;
import ru.bk.artv.bracketschecker.dto.CheckTextRequest;
import ru.bk.artv.bracketschecker.dto.CheckTextResponse;
import ru.bk.artv.bracketschecker.service.BracketsChecker;

import javax.validation.constraints.NotBlank;

/** Класс содержит один контроллер по адресу /api/checkBrackets.*/
@RestController
@RequestMapping(path = "/api")
@Tag(name="TextCheckerRestController", description="Проверяет текст на правильность расстановки скобок")
public class TextCheckerRestController {

    private static final Logger log = LoggerFactory.getLogger(TextCheckerRestController.class);
    private static final String TEXT_IS_BLANK = "The text is blank";
    private final BracketsChecker bracketsChecker; //в зависимости от профиля могут инстанциироваться разные объекты

    public TextCheckerRestController(BracketsChecker bracketsChecker) {
        this.bracketsChecker = bracketsChecker;
    }

    /** Метод контроллера запускает проверку текста на корректную расстановку скобок. Принимаются и возвращаются данные
     * в application/json. Аутентификация не предусмотрена, доступ открытый.
     * @param req содержит одно поле с проверяемым текстом.
     * @return возвращает CheckTextResponse результат проверки с isCorrect = false в случае некорректного употребления
     * в тексте скобок и isCorrect = true в случае корректного употребления.
     * При отсутствии текста в запросе возвращается AppError с кодом 400 и сообщением "The text is blank".*/
    @PostMapping(path = "/checkBrackets")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Проверка текста на корректную расстановку круглых скобок",
            description = "Позволяет проверить корректность расстановки открывающих и закрывающих скобок в тексте"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Запрос обработан успешно",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CheckTextResponse.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Принимаемый запрос содержит пустой текст",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppError.class))})})
    public ResponseEntity<?> checkBracketsRest(
            @RequestBody @NotBlank @Parameter(description = "Проверяемый текст") CheckTextRequest req) {

        log.info("Request is : {}", req);

        if (req.getText() == null || req.getText().isBlank()) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new AppError(400, TEXT_IS_BLANK));
        }

        CheckTextResponse answer = bracketsChecker.checkBrackets(req);
        log.info("Answer is : {}", req);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(answer);
    }


}
