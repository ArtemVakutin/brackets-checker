package ru.bk.artv.bracketschecker.service;

import ru.bk.artv.bracketschecker.config.BeansConfiguration;
import ru.bk.artv.bracketschecker.dto.CheckTextResponse;
import ru.bk.artv.bracketschecker.dto.CheckTextRequest;

/**
 * Класс BracketsCheckerFull осуществляет проверку текста всеми способами, имеющимися в проекте. Происходит проверка
 * вложенных скобок: "Яна ехала на машине (красного цвета (Сузуки)).", поиск ошибок и помарок форматирования,
 * например "машина(красная)" - отсутсвует пробел, "(зеленая)(желтая) - выражения в скобках идут друг за другом,
 * правилами русского языка не предусмотрено. Наличие именованных списков: 1) 2) и т.д. при наличии знаков переноса
 * перед элементом списка обрабатывается корректно.
 *
 * Bean объявляется в {@link BeansConfiguration}
 */
public class BracketsCheckerFull implements BracketsChecker {

    /**
     * Метод с помощью статических методов класса {@link BracketsCheckerUtil} производит проверку текста. Из
     * текста удаляются корректные именованные списки, далее проверяется на правильность употребления скобок и на ошибки
     * форматирования, связанные с употреблением скобок.
     *
     * @param req - запрос пользователя, содержащий поле с проверяемым текстом
     * @return - ответ, содержащий boolean, принимающей true в случае корректности текста и false в случае некорректности
     */
    @Override
    public CheckTextResponse checkBrackets(CheckTextRequest req) {
        String text = BracketsCheckerUtil.deleteBulletedList(req.getText());

        boolean correctBracketsCount = BracketsCheckerUtil.checkBracketsCorrectWithAttachments(text);
        boolean correctFormat = BracketsCheckerUtil.checkBracketsForCorrectFormat(text);

        return new CheckTextResponse((correctBracketsCount && correctFormat));
    }
}
