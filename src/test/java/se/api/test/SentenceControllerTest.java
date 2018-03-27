package se.api.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import se.api.SentenceController;
import se.persistence.entity.Sentence;
import se.service.SentenceService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SentenceController.class)
public class SentenceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SentenceService sentenceService;

    private static String CONTEXT_PATH = "/api";

    private String buildUrl(String relativePath) {
        return String.format("%s%s", CONTEXT_PATH, relativePath);
    }

    @Test
    public void createSentence_givenValidValue_shouldReturnSentenceAnd201Created() throws Exception {
        String value = "hej hej hej";
        Sentence s1 = new Sentence(value);
        BDDMockito.given(sentenceService.createSentence(Optional.of(value))).willReturn(s1);

        mockMvc.perform(post(buildUrl("/sentence"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"sentence\":\"%s\"}", value))
                .contextPath(CONTEXT_PATH))
                .andExpect(status().isCreated());
    }

    @Test
    public void createSentence_givenIllegalArgumentException_shouldReturn400BadRequest() throws Exception {
        String value = "";
        Mockito.when(sentenceService.createSentence(Optional.of(value))).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(post(buildUrl("/sentence"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"sentence\":\"%s\"}", value))
                .contextPath(CONTEXT_PATH))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllSentences_whenExistingSentences_shouldReturnListAnd200OK() throws Exception {
        Sentence s1 = new Sentence("hej hej hej");
        List<Sentence> allSentences = Arrays.asList(s1);
        BDDMockito.given(sentenceService.getAllSentences()).willReturn(allSentences);

        mockMvc.perform(get(buildUrl("/sentence/list"))
                .contextPath(CONTEXT_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].value", is(s1.getValue())));
    }

    @Test
    public void getAllSentences_whenNoSentencesExist_shouldReturnEmptyListAnd200OK() throws Exception {
        List<Sentence> allSentences = new ArrayList<>();
        BDDMockito.given(sentenceService.getAllSentences()).willReturn(allSentences);

        mockMvc.perform(get(buildUrl("/sentence/list"))
                .contextPath(CONTEXT_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}