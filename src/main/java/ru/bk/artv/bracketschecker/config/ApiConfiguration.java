package ru.bk.artv.bracketschecker.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Класс конфигурирует документацию api Swagger.
 * Api-Docs доступны по адресу /api-doc, доступ без аутентификации.
 * */
@OpenAPIDefinition(
        info = @Info(
                title = "Brackets Checker Api",
                description = "Brackets Checker", version = "1.0.0",
                contact = @Contact(
                        name = "Vakutin Artem",
                        email = "artv@bk.ru")))
public class ApiConfiguration {
}
