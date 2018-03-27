package se.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import se.util.StringUtils;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Sentence {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The external id of the Sentence - visible for external clients
     */
    private String externalId;

    /**
     * The original sentence
     */
    private String value;

    /**
     * The reversed value of the sentence.
     */
    @Transient
    private String reversedValue;

    protected Sentence() { }

    public Sentence(String value) {
        setValue(value);
        setExternalId(UUID.randomUUID().toString());
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        reversedValue = StringUtils.reverseWordsInSentence(value);
    }

    /**
     * @return A String containing the words in 'value', reversed.
     */
    public String getReversedValue() {
        return reversedValue;
    }

    @PostLoad
    private void postLoad() {
        reversedValue = StringUtils.reverseWordsInSentence(value);
    }
}
