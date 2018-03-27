package se.persistence;

import org.springframework.data.repository.CrudRepository;
import se.persistence.entity.Sentence;

import java.util.List;
import java.util.Optional;

public interface SentenceRepository extends CrudRepository<Sentence, Long> {
    List<Sentence> findAll();
    Optional<Sentence> findByExternalId(String externalId);
    Optional<Sentence> findByValue(String value);
}