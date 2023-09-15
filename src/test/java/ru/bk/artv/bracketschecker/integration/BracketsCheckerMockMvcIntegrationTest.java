package ru.bk.artv.bracketschecker.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.bk.artv.bracketschecker.dto.CheckTextRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.bk.artv.bracketschecker.testobjects.TextExamplesForTests.*;

/**
 * Запускает Spring и проверяет проект на ответы на разные запросы клиента. В том числе происходит проверка на отсутствие
 * текста в запросе
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("full")
public class BracketsCheckerMockMvcIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * Проверяет варианты клиентского текста с корректным употреблением скобок
     *
     *  * String с текстом статически импотрируются из {@link ru.bk.artv.bracketschecker.testobjects.TextExamplesForTests}
     */
    @Test
    public void checkBracketsRest_ReturnsValidEntity_ResponseTrue() throws Exception {

        mockMvc.perform(
                post("/api/checkBrackets")
                        .content(objectMapper.writeValueAsString(new CheckTextRequest(CORRECT_SIMPLE_TEXT)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isCorrect").value(true));

        mockMvc.perform(
                post("/api/checkBrackets")
                        .content(objectMapper.writeValueAsString(new CheckTextRequest(CORRECT_TEXT_WITH_ATTACHMENTS)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isCorrect").value(true));

        mockMvc.perform(
                post("/api/checkBrackets")
                        .content(objectMapper.writeValueAsString(new CheckTextRequest(CORRECT_TEXT_WITH_BULLETED_LIST)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isCorrect").value(true));
    }

    /**
     * Проверяет варианты клиентского текста с некорректным употреблением скобок
     */
    @Test
    public void checkBracketsRest_ReturnsValidEntity_ResponseFalse() throws Exception {

        mockMvc.perform(
                post("/api/checkBrackets")
                        .content(objectMapper.writeValueAsString(new CheckTextRequest(INCORRECT_TEXT_WITH_BRACKETS)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isCorrect").value(false));

        mockMvc.perform(
                post("/api/checkBrackets")
                        .content(objectMapper.writeValueAsString(new CheckTextRequest(INCORRECT_TEXT_WITH_FORMAT_PROBLEMS_1)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isCorrect").value(false));

        mockMvc.perform(
                post("/api/checkBrackets")
                        .content(objectMapper.writeValueAsString(new CheckTextRequest(INCORRECT_TEXT_WITH_FORMAT_PROBLEMS_2)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isCorrect").value(false));

        mockMvc.perform(
                post("/api/checkBrackets")
                        .content(objectMapper.writeValueAsString(new CheckTextRequest(INCORRECT_TEXT_WITH_FORMAT_PROBLEMS_3)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isCorrect").value(false));

        mockMvc.perform(
                post("/api/checkBrackets")
                        .content(objectMapper.writeValueAsString(new CheckTextRequest(INCORRECT_TEXT_WITH_FORMAT_PROBLEMS_4)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isCorrect").value(false));
    }

    /**
     * Проверяет ответ на запрос с пустым текстом
     */
    @Test
    public void checkBracketsRest_ReturnsAppErrorEntity() throws Exception {

        mockMvc.perform(
                post("/api/checkBrackets")
                        .content(objectMapper.writeValueAsString(new CheckTextRequest("")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }
}
