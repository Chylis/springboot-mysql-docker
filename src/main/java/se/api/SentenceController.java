package se.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.persistence.entity.Sentence;
import se.service.SentenceService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path="sentence")
public class SentenceController {

    private static final Logger log = LoggerFactory.getLogger(SentenceController.class);

    private SentenceService sentenceService;

    public SentenceController(SentenceService sentenceService) {
        this.sentenceService = sentenceService;
    }

    /**
     * Creates a reversed sentence
     * @param body A json-object containing the sentence.
     *             E.g. {"sentence":"The red fox crosses the ice, intent on none of my business."}
     * @return "201 Created" together with the newly created sentence or "400 Bad Request" if invalid input was provided.
     */
    @PostMapping(consumes = "application/json")
    public @ResponseBody ResponseEntity<Sentence> createSentence(@RequestBody Map<String, String> body) {
        Optional<String> valueParam = Optional.ofNullable(body.get("sentence"));

        Sentence created = sentenceService.createSentence(valueParam);
        return new ResponseEntity(created, HttpStatus.CREATED);
    }

    /**
     * Deletes a sentence by its externalId.
     * @param externalId The externalId of the sentence to delete.
     * @return "200 OK"
     */
    @DeleteMapping()
    public @ResponseBody ResponseEntity deleteSentence(@RequestParam String externalId) {
        sentenceService.deleteSentenceWithExternalId(externalId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * @return "200 OK" together with a list of all created sentences.
     */
    @GetMapping("/list")
    public @ResponseBody ResponseEntity<List<Sentence>> getAllSentences() {
        List<Sentence> sentences = sentenceService.getAllSentences();
        return new ResponseEntity(sentences, HttpStatus.OK);
    }
}
