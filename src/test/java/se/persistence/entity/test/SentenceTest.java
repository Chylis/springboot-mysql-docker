package se.persistence.entity.test;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import se.persistence.SentenceRepository;
import se.persistence.entity.Sentence;

import java.util.Optional;

import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class SentenceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SentenceRepository sentenceRepository;

    private final String original = "The red fox crosses the ice, intent on none of my business.";
    private final String expected = "ehT der xof sessorc eht eci, tnetni no enon fo ym ssenisub.";

    @Test
    public void sentence_createdWithAValue_shouldContainReversedValue() {
        Sentence sentence = new Sentence(original);
        Assertions.assertThat(sentence.getValue()).isEqualTo(original);
        Assertions.assertThat(sentence.getReversedValue()).isEqualTo(expected);
    }

    @Test
    public void sentence_loadedIntoAManagedContext_shouldContainReversedValue() {
        entityManager.persistAndFlush(new Sentence(original));

        Optional<Sentence> managedSentence = sentenceRepository.findByValue(original);
        if (managedSentence.isPresent()) {
            Assertions.assertThat(managedSentence.get().getValue()).isEqualTo(original);
            Assertions.assertThat(managedSentence.get().getReversedValue()).isEqualTo(expected);
        } else {
            fail("Expected to find a sentence");
        }
    }

    @Test
    public void sentence_settingADifferentValue_shouldUpdateTheReversedValue() {
        Sentence sentence = new Sentence(original);
        sentence.setValue(expected);
        Assertions.assertThat(sentence.getValue()).isEqualTo(expected);
        Assertions.assertThat(sentence.getReversedValue()).isEqualTo(original);
    }
}
