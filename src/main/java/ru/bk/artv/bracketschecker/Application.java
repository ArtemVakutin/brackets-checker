package ru.bk.artv.bracketschecker;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * По умолчанию запускается в режиме полной проверки текста. При необходимости проверки лишь по соответствию открытых и
 * закрытых скобок на одном уровне и текста между ними - установите профиль soft через mvn -Psoft при запуске.
 * Конфигурация бинов находится в config, exception handler-ов не предусмотрено
 * */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
