package se.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import se.persistence.SentenceRepository;
import se.persistence.entity.Sentence;
import se.service.SentenceService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
public class SentenceServiceTest {

    /*
    To check the Service class, we need to have an instance of Service class created and available as a @Bean so
    that we can @Autowire it in our test class. This configuration is achieved by using the @TestConfiguration annotation.
    During component scanning, we might find components or configurations created only for specific tests accidentally get
    picked up everywhere. To help prevent that, Spring Boot provides @TestConfiguration annotation that can be used on classes in
    src/test/java to indicate that they should not be picked up by scanning.
    */
    @TestConfiguration
    static class SentenceServiceTestContextConfiguration {
        @Bean
        public SentenceService sentenceService() {
            return new SentenceService();
        }
    }

    @Autowired
    private SentenceService sentenceService;

    @MockBean
    private SentenceRepository sentenceRepository;


    @Test
    public void createSentence_givenValidValue_willCreateSentence() {
        String value = "The red fox crosses the ice, intent on none of my business";
        Sentence sentence = new Sentence(value);
        Mockito.when(sentenceRepository.save(Mockito.any())).thenReturn(sentence);

        Sentence created = sentenceService.createSentence(Optional.of(value));

        assertThat(created.getValue()).isEqualTo(value);
        assertThat(created.getExternalId()).isEqualTo(sentence.getExternalId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createSentence_givenEmptyOptional_willThrowException() {
        sentenceService.createSentence(Optional.empty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createSentence_givenEmptyValue_willThrowException() {
        String value = "";
        sentenceService.createSentence(Optional.of(value));
    }

    @Test
    public void deleteSentenceWithExternalId_givenExternalId_thenSentenceShouldNotBeFound() {
        String externalId = "0337d076-ed02-469d-88a9-bbff1ebc81af";
        Mockito.when(sentenceRepository.findByExternalId(externalId)).thenReturn(Optional.empty());
        sentenceService.deleteSentenceWithExternalId(externalId);
        //TODO: Hmm
    }

    @Test
    public void getAllSentences_whenSentencesExist_shouldReturnSentences() {
        String value = "The red fox crosses the ice, intent on none of my business";
        Sentence sentence = new Sentence(value);
        Mockito.when(sentenceRepository.findAll()).thenReturn(Arrays.asList(sentence));

        List<Sentence> sentenceList = sentenceService.getAllSentences();

        assertThat(sentenceList.size()).isEqualTo(1);
        assertThat(sentenceList.get(0).getValue()).isEqualTo(value);
    }

    @Test
    public void getAllSentences_whenNoSentencesExist_shouldREturnEmptyList() {
        Mockito.when(sentenceRepository.findAll()).thenReturn(Collections.emptyList());

        List<Sentence> sentenceList = sentenceService.getAllSentences();
        assertThat(sentenceList).isEmpty();
    }
}
