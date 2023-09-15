package ru.bk.artv.bracketschecker.service;

import ru.bk.artv.bracketschecker.config.BeansConfiguration;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Класс BracketsCheckerUtil содержит только статические методы для проверки корректности употребления скобок в тексте
 * разными способами. Конструктор private, является util-классом.
 */
public class BracketsCheckerUtil {

    public static final Character OPEN_BRACKET = '(';
    public static final Character CLOSE_BRACKET = ')';

    /**
     * Regex для поиска количества открытых и закрытых скобок и текста между ними
     */
    public static final String ROUND_BRACKETS_REGEX = "\\(.+?\\)";

    /**
     * Regex для поиска именованных списков. Именованные списки определяются только при наличии одного из способов
     * переноса текста. Напр. 1) желтый,
     * 2) зеленый. Допускается буквенное именование. Списки 1) зеленый, 2) желтый не будут распознаны (таким образом
     * в текстах они по правилам языка употребляться не должны).
     */
    public static final String BRACKET_BULLETED_LIST_REGEX = "[\r\n\\u2029]([a-zA-Zа-яА-Я]||[1-9]){1,3}\\)[\\s\\t]";

    /**
     * Массив regex для поиска ошибок форматирования.
     * 0) - отсутствие пробела между текстом и открывающей скобкой. Напр. "Яна водила машину(зеленую)". В качестве
     * элемента, предшествующего скобке, допускаются знаки препинания.
     * 1) - отсутствие пробела после закрывающей скобки. Напр.: "Яна водила машину (зеленую)и любила мороженое". Знаки
     * препинания после закрывающей скобки не обрабатываются.
     * 2) - наличие выражений в скобках друг за другом без разделителя в виде текста и знаков препинания. "(текст)(текст)"
     * 3) - то же самое с пробелом "(текст) (текст)
     */
    public static final String[] BRACKET_FORMAT_ERRORS_REGEX = new String[]{
            "(.*)([a-zA-Zа-яА-Я]|[!?,.])\\((.*)",
            "(.*)\\)[a-zA-Zа-яА-Я](.*)",
            "(.*)\\)\\((.*)",
            "(.*)\\)[ ]\\((.*)"
    };

    /**
     * Конструктор private, класс не является bean-ом Spring
     */
    private BracketsCheckerUtil() {
    }

    /**
     * Удаляет именованный список из текста для дальнейшей обработки.
     *
     * @param text - обрабатываемый текст
     * @return - текст без именованных списков.
     */
    public static String deleteBulletedList(String text) {
        return text.replaceAll(BRACKET_BULLETED_LIST_REGEX, " ");
    }

    /**
     * Проверяет текст на простое употребление скобок. Напр. "Яна водила машину (зеленую)". Употребление вложных скобок
     * обрабатывается некорректно. Сперва скобки сравниваются по количеству, потом проверяется количество найденных
     * употреблений типа (текст). В случае совпадения с количеством скобок возвращается true.
     *
     * @param text - обрабатываемый текст
     * @return - true - если употребление скобок корректно и false - если некорректно.
     */
    public static boolean checkBracketsCorrectSimple(String text) {
        long openBrackets = text.chars().filter(ch -> ch == OPEN_BRACKET).count();
        long closeBrackets = text.chars().filter(ch -> ch == CLOSE_BRACKET).count();

        Matcher matcher = Pattern.compile(ROUND_BRACKETS_REGEX).matcher(text);
        long count = matcher.results().count();

        return (count == openBrackets && openBrackets == closeBrackets);
    }

    /**
     * Проверяет текст на употребление скобок с учетом вложенных скобок.
     * употребление скобок. Корректно обрабатывает выражения типа "Яна ехала на машине (красного цвета (Сузуки))."
     * Напр. "Яна водила машину (зеленую)".
     * Не использует regex (обработка вложенных скобок с ним проблематична).
     *
     *
     * @param text - обрабатываемый текст
     * @return - true - если употребление скобок корректно и false - если некорректно.
     */
    public static boolean checkBracketsCorrectWithAttachments(String text) {
        Deque<Character> openBrackets = new ArrayDeque<>();
        boolean textBetween = true;

        for (char ch : text.toCharArray()) {
            if (ch == OPEN_BRACKET) { //в случае открывающей скобки и отсутствия текста перед ней - метод возвращает false
                if (!textBetween) {
                    return false;
                } else {
                    openBrackets.add(ch); //если текст есть, скобка добавляется в Deque
                    textBetween = false;
                }
            } else if (ch == CLOSE_BRACKET) {
                if (openBrackets.isEmpty() || !textBetween) {//если очередь пуста или нет текста между скобками, false
                    return false;
                }
                openBrackets.pop();
            } else { //в случае наличия текста textBetween=true
                textBetween = true;
            }
        }
        return openBrackets.isEmpty();
    }

    /**
     * Проверяет текст на ошибки форматирования в соответствии со списком regex (Константа BRACKET_FORMAT_ERRORS_REGEX).
     *
     * @param text - обрабатываемый текст
     * @return - true - если ошибок не найдено и false - если некорректно.
     */
    static boolean checkBracketsForCorrectFormat(String text) {
        return Stream.of(BRACKET_FORMAT_ERRORS_REGEX).noneMatch(text::matches);
    }
}
