package ru.bk.artv.bracketschecker.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static ru.bk.artv.bracketschecker.testobjects.TextExamplesForTests.*;

/**
 * Класс проверяет все методы util-класса  {@link BracketsCheckerUtil}
 *
 * String с текстом статически импотрируются из {@link ru.bk.artv.bracketschecker.testobjects.TextExamplesForTests}
 */
class BracketsCheckerUtilTest {

    /**
     * Проверка распознавания именованных списков
     */
    @Test
    public void deleteBulletedListTest() {
        //given
        char openBracket = ')';

        //when
        String nonBulletedList = BracketsCheckerUtil.deleteBulletedList(CORRECT_TEXT_WITH_BULLETED_LIST);
        long openBrackets = nonBulletedList.chars().filter(ch -> ch == openBracket).count();

        //then
        assertEquals(openBrackets, 1);
    }

    /**
     * Проверка корректности распознавания без внутренних скобок
     */
    @Test
    void checkBracketsCorrectSimpleTest() {
        boolean noAttachments = BracketsCheckerUtil.checkBracketsCorrectSimple(CORRECT_SIMPLE_TEXT);
        boolean withAttachments = BracketsCheckerUtil
                .checkBracketsCorrectSimple(CORRECT_TEXT_WITH_ATTACHMENTS);

        assertTrue(noAttachments);
        assertFalse(withAttachments);
    }

    /**
     * Проверка корректности распознавания с внутренними скобками
     */
    @Test
    void checkBracketsCorrectWithAttachmentsTest() {
        boolean noAttachments = BracketsCheckerUtil.checkBracketsCorrectSimple(CORRECT_SIMPLE_TEXT);
        boolean withAttachments = BracketsCheckerUtil
                .checkBracketsCorrectSimple(CORRECT_TEXT_WITH_ATTACHMENTS);
        boolean isIncorrect = BracketsCheckerUtil
                .checkBracketsCorrectSimple(INCORRECT_TEXT_WITH_BRACKETS);

        assertTrue(noAttachments);
        assertFalse(withAttachments);
        assertFalse(isIncorrect);
    }

    /**
     * Проверка корректности распознавания с разными ошибками форматирования
     */
    @Test
    void checkBracketsForCorrectFormatTest() {
        boolean isIncorrectFormat1 = BracketsCheckerUtil.checkBracketsForCorrectFormat(INCORRECT_TEXT_WITH_FORMAT_PROBLEMS_1);
        boolean isIncorrectFormat2 = BracketsCheckerUtil.checkBracketsForCorrectFormat(INCORRECT_TEXT_WITH_FORMAT_PROBLEMS_2);
        boolean isIncorrectFormat3 = BracketsCheckerUtil.checkBracketsForCorrectFormat(INCORRECT_TEXT_WITH_FORMAT_PROBLEMS_3);
        boolean isIncorrectFormat4  = BracketsCheckerUtil.checkBracketsForCorrectFormat(INCORRECT_TEXT_WITH_FORMAT_PROBLEMS_4);
        boolean isCorrect = BracketsCheckerUtil.checkBracketsForCorrectFormat(CORRECT_TEXT_WITH_ATTACHMENTS);

        assertFalse(isIncorrectFormat1);
        assertFalse(isIncorrectFormat2);
        assertFalse(isIncorrectFormat3);
        assertFalse(isIncorrectFormat4);
        assertTrue(isCorrect);
    }
}