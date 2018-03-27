package se.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.persistence.SentenceRepository;
import se.persistence.entity.Sentence;

import java.util.List;
import java.util.Optional;

@Service
public class SentenceService {

    private static final Logger log = LoggerFactory.getLogger(SentenceService.class);

    @Autowired
    private SentenceRepository sentenceRepository;

    /**
     * @param maybeValue The sentence to persist
     * @return The persisted Sentence
     * @throws IllegalArgumentException If the received sentence if null or empty
     */
    public Sentence createSentence(Optional<String> maybeValue) throws IllegalArgumentException {
        String value = maybeValue
                .filter(s -> !s.isEmpty())
                .orElseThrow(IllegalArgumentException::new);

        return sentenceRepository
                .findByValue(value)
                .orElseGet(() -> sentenceRepository.save(new Sentence(value)));
    }


    /**
     * Removes the Sentence with the received externalId, if present
     * @param externalId The externalId of the Sentence to remove
     */
    public void deleteSentenceWithExternalId(String externalId) {
        sentenceRepository
                .findByExternalId(externalId)
                .ifPresent(sentence -> sentenceRepository.delete(sentence));
    }

    /**
     * @return A list of all sentences
     */
    public List<Sentence> getAllSentences() {
        return sentenceRepository.findAll();
    }
}
