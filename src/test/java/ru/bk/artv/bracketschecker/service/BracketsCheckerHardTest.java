package ru.bk.artv.bracketschecker.service;

import org.junit.jupiter.api.Test;
import ru.bk.artv.bracketschecker.dto.CheckTextRequest;
import ru.bk.artv.bracketschecker.dto.CheckTextResponse;

import static ru.bk.artv.bracketschecker.testobjects.TextExamplesForTests.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Проверяет ответы на запросы с текстом со вложенными скобками, с некорректным употреблением текста, а также с
 * именованными списками. Соответствует профилю full.
 *
 * String с текстом статически импотрируются из {@link ru.bk.artv.bracketschecker.testobjects.TextExamplesForTests}
 */
class BracketsCheckerHardTest {

    BracketsChecker bracketsChecker = new BracketsCheckerFull();

    /**
     * Проверяет текст с корректным употреблением скобок
     */
    @Test
    public void checkBracketsTest_ResponseIsCorrectTrue() {
        //given
        CheckTextRequest requestSimple = new CheckTextRequest(CORRECT_SIMPLE_TEXT);
        CheckTextRequest requestWithAttachments = new CheckTextRequest(CORRECT_TEXT_WITH_ATTACHMENTS);
        CheckTextRequest requestBulletedList = new CheckTextRequest(CORRECT_TEXT_WITH_BULLETED_LIST);
        CheckTextResponse correctResponse = new CheckTextResponse(true);

        //then
        CheckTextResponse responseSimple = bracketsChecker.checkBrackets(requestSimple);
        CheckTextResponse responseWithAttachments = bracketsChecker.checkBrackets(requestWithAttachments);
        CheckTextResponse responseBulletedList = bracketsChecker.checkBrackets(requestBulletedList);

        //when
        assertEquals(responseSimple, correctResponse);
        assertEquals(responseWithAttachments, correctResponse);
        assertEquals(responseBulletedList, correctResponse);
    }

    /**
     * Проверяет текст с некорректным употреблением скобок
     */
    @Test
    public void checkBracketsTest_ResponseIsIncorrectFalse() {
        //given
        CheckTextRequest requestIncorrectBrackets = new CheckTextRequest(INCORRECT_TEXT_WITH_BRACKETS);
        CheckTextRequest requestFormatProblems = new CheckTextRequest(INCORRECT_TEXT_WITH_FORMAT_PROBLEMS_1);
        CheckTextResponse correctResponse = new CheckTextResponse(false);

        //then
        CheckTextResponse responseIncorrectBrackets = bracketsChecker.checkBrackets(requestIncorrectBrackets);
        CheckTextResponse responseWithFormatProblems = bracketsChecker.checkBrackets(requestFormatProblems);

        //when
        assertEquals(responseIncorrectBrackets, correctResponse);
        assertEquals(responseWithFormatProblems, correctResponse);
    }
}