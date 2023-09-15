package ru.bk.artv.bracketschecker.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.bk.artv.bracketschecker.dto.AppError;
import ru.bk.artv.bracketschecker.dto.CheckTextRequest;
import ru.bk.artv.bracketschecker.dto.CheckTextResponse;
import ru.bk.artv.bracketschecker.service.BracketsChecker;
import ru.bk.artv.bracketschecker.testobjects.TextExamplesForTests;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.bk.artv.bracketschecker.testobjects.TextExamplesForTests.CORRECT_SIMPLE_TEXT;
import static org.junit.jupiter.api.Assertions.*;
import static ru.bk.artv.bracketschecker.testobjects.TextExamplesForTests.INCORRECT_TEXT_BLANK;

@ExtendWith(MockitoExtension.class)
class TextCheckerRestControllerTest {

    @Mock
    BracketsChecker bracketsChecker;

    @InjectMocks
    TextCheckerRestController controller;

    @Test
    void checkBracketsRest_ReturnValidResponse() {
        //given
        CheckTextRequest request = new CheckTextRequest(CORRECT_SIMPLE_TEXT);
        CheckTextResponse response = new CheckTextResponse(true);

        doReturn(response).when(this.bracketsChecker).checkBrackets(request);

        //when
        ResponseEntity<?> responseEntity = controller.checkBracketsRest(request);

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(response, responseEntity.getBody());
    }




    @Test
    public void checkBracketsRest_ReturnsAppErrorEntity() throws Exception {
        //given
        CheckTextRequest request = new CheckTextRequest(INCORRECT_TEXT_BLANK);
        AppError appError = new AppError(400, "The text is blank");

        //when
        ResponseEntity<?> responseEntity = controller.checkBracketsRest(request);

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(appError, responseEntity.getBody());
    }

}