package ru.bk.artv.bracketschecker.service;

import org.junit.jupiter.api.Test;
import ru.bk.artv.bracketschecker.dto.CheckTextRequest;
import ru.bk.artv.bracketschecker.dto.CheckTextResponse;

import static ru.bk.artv.bracketschecker.testobjects.TextExamplesForTests.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Проверяет ответы на запросы с текстом без вложенный скобок. Соответсвует профилю false. Запросы со вложенными скобками,
 * именованными списками должны обратываться некорректно.
 *
 *  * String с текстом статически импотрируются из {@link ru.bk.artv.bracketschecker.testobjects.TextExamplesForTests}
 */
class BracketsCheckerSoftTest {

    BracketsChecker bracketsChecker = new BracketsCheckerSoft();

    /**
     * Проверяет текст с корректным употреблением скобок (без вложенных скобок, проверок форматировани и списков)
     */
    @Test
    public void checkBracketsTest_ResponseIsCorrectTrue() {
        //given
        CheckTextRequest request = new CheckTextRequest(CORRECT_SIMPLE_TEXT);
        CheckTextResponse correctResponse = new CheckTextResponse(true);

        //then
        CheckTextResponse response = bracketsChecker.checkBrackets(request);

        //when
        assertEquals(response, correctResponse);
    }

    /**
     * Проверяет текст с вложенными скобками, некорректным употреблением скобок
     */
    @Test
    public void checkBracketsTest_ResponseIsIncorrectFalse() {
        //given
        CheckTextRequest requestIncorrectBrackets = new CheckTextRequest(INCORRECT_TEXT_WITH_BRACKETS);
        CheckTextRequest requestWithAttachments = new CheckTextRequest(CORRECT_TEXT_WITH_ATTACHMENTS);
        CheckTextResponse correctResponse = new CheckTextResponse(false);

        //then
        CheckTextResponse responseIncorrectBrackets = bracketsChecker.checkBrackets(requestIncorrectBrackets);
        CheckTextResponse responseWithAttachments = bracketsChecker.checkBrackets(requestWithAttachments);

        //when
        assertEquals(responseIncorrectBrackets, correctResponse);
        assertEquals(responseWithAttachments, correctResponse);
    }
}