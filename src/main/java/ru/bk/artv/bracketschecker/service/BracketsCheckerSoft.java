package ru.bk.artv.bracketschecker.service;

import ru.bk.artv.bracketschecker.config.BeansConfiguration;
import ru.bk.artv.bracketschecker.dto.CheckTextResponse;
import ru.bk.artv.bracketschecker.dto.CheckTextRequest;

/**
 * Класс BracketsCheckerFull предназначен для проверки текста только на закрытие скобки после их открытия и наличие
 * текста между скобками.
 *
 * Bean объявляется в {@link BeansConfiguration}
 */
public class BracketsCheckerSoft implements BracketsChecker {

    /**
     * Метод с помощью статического метода класса {@link BracketsCheckerUtil} производит проверку текста только
     * на акрытие скобки после их открытия и наличие текста между скобками.
     * Например "Яна ехала на машине (красного цвета), а у Леши тоже была машина (зеленого цвета)."
     * - корректно, а "Яна ехала на машине (красного цвета (Сузуки))." - некорректно. Помарки форматирования не
     * проверяются, именованные списки не обрабатываются и дают некорректный результат.
     *
     * @param req - запрос пользователя, содержащий поле с проверяемым текстом
     * @return - ответ, содержащий boolean, принимающей true в случае корректности текста и false в случае некорректности
     */
    @Override
    public CheckTextResponse checkBrackets(CheckTextRequest req) {
        String text = req.getText();
        return new CheckTextResponse(BracketsCheckerUtil.checkBracketsCorrectSimple(text));
    }
}
