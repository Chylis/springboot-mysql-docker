package se.persistence.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import se.persistence.SentenceRepository;
import se.persistence.entity.Sentence;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class SentenceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SentenceRepository sentenceRepository;

    @Test
    public void findByValue_givenValidValue_whenPersisted_thenEntityShouldBeFound() {
        String value = "The red fox crosses the ice, intent on none of my business";
        Sentence sentence = new Sentence(value);
        entityManager.persist(sentence);
        entityManager.flush();

        Optional<Sentence> found = sentenceRepository.findByValue(value);
        if (found.isPresent()) {
            assertThat(found.get().getValue().equals(value)).isTrue();
        } else {
            fail("Expected to find a sentence");
        }
    }

    @Test
    public void findByExternalId_givenValidValue_whenPersisted_thenEntityShouldBeFound() {
        Sentence sentence = new Sentence("The red fox crosses the ice, intent on none of my business");
        entityManager.persist(sentence);
        entityManager.flush();

        Optional<Sentence> found = sentenceRepository.findByExternalId(sentence.getExternalId());
        if (found.isPresent()) {
            assertThat(found.get().getExternalId().equals(sentence.getExternalId())).isTrue();
        } else {
            fail("Expected to find a sentence");
        }
    }

    @Test
    public void givenValidSentences_whenPersistSentence_thenSentencesShouldBeFound() {
        String value = "The red fox crosses the ice, intent on none of my business";
        Sentence sentence = new Sentence(value);
        entityManager.persist(sentence);
        entityManager.flush();

        List<Sentence> found = sentenceRepository.findAll();
        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0).getValue()).isEqualTo(value);
    }
}
