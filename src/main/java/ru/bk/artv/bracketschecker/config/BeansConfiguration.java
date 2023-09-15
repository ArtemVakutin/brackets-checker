package ru.bk.artv.bracketschecker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.bk.artv.bracketschecker.service.BracketsCheckerFull;
import ru.bk.artv.bracketschecker.service.BracketsChecker;
import ru.bk.artv.bracketschecker.service.BracketsCheckerSoft;

/**
 * Класс конфигурации BeansConfiguration предполагает два профиля работы. По умолчанию запускается в режиме
 * полной проверки текста (full). При необходимости проверки лишь по соответствию открытых и закрытых скобок без
 * вложенных скобок и наличии текста между ними - установите профиль soft через mvn -Psoft при запуске.
 * Разделения профилей на product и dev не предусмотрено.
 * */
@Configuration
public class BeansConfiguration {

    /**
     * Инстанциирует BracketsChecker, проверяющий корректность скобок всеми предусмотренными проектом способами
     * */
    @Bean("roundBracketsChecker")
    @Profile({"!soft", "full"})
    public BracketsChecker roundBracketsCheckerFull() {
        return new BracketsCheckerFull();
    }

    /**
     * Инстанциирует BracketsChecker, проверяющий корректность скобок только на соответствие закрытых открытым, их
     * последовательности и наличии текста между ними
     * */
    @Bean("roundBracketsChecker")
    @Profile({"soft"})
    public BracketsChecker roundBracketsCheckerSoft() {
        return new BracketsCheckerSoft();
    }
}
