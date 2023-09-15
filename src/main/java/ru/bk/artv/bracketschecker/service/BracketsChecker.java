package ru.bk.artv.bracketschecker.service;

import ru.bk.artv.bracketschecker.dto.CheckTextResponse;
import ru.bk.artv.bracketschecker.dto.CheckTextRequest;

/**
 * Интерфейс, содержащий метод для проверки корректности расстановки скобок в тексте
 * */
public interface BracketsChecker {

    /**
     * Проверка корректности расстановки скобок в тексте
     * @param req - запрос пользователя, содержащий поле с проверяемым текстом
     * @return - ответ, содержащий boolean, принимающей true в случае корректности текста и false в случае некорректности
     * */
    CheckTextResponse checkBrackets(CheckTextRequest req);
}
