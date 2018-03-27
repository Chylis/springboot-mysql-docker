package se.util;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class StringUtilsTest {

    @Test
    public void reverseWordsInSentence_givenValidSentence_shouldReverseWords() {
        String original = "The red fox crosses the ice, intent on none of my business.";
        String expected = "ehT der xof sessorc eht eci, tnetni no enon fo ym ssenisub.";
        Assertions.assertThat(StringUtils.reverseWordsInSentence(original)).isEqualTo(expected);

        original = "Hello there  ! How are you doing ??    I'm just fine";
        expected = "olleH ereht  ! woH era uoy gniod ??    m'I tsuj enif";
        Assertions.assertThat(StringUtils.reverseWordsInSentence(original)).isEqualTo(expected);

        original = "Word.";
        expected = "droW.";
        Assertions.assertThat(StringUtils.reverseWordsInSentence(original)).isEqualTo(expected);
    }

    @Test
    public void reverseWordsInSentence_givenEmptySentence_shouldReturnEmptySentence() {
        String original = "";
        String expected = "";
        Assertions.assertThat(StringUtils.reverseWordsInSentence(original)).isEqualTo(expected);
    }
}
