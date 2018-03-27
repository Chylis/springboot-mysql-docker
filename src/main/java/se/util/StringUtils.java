package se.util;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtils {

    /**
     * Reverses all the words in the received sentence
     * @param sentence The sentence to reverse. May not be null.
     * @return A String containing the reversed words
     */
    public static String reverseWordsInSentence(String sentence) {
        final String nonAlphaNumericRegex = "[^A-Za-z0-9\\s]";

        //Remove all non-alphanumerics (i.e. keep all alpha numerics + spaces)
        String alphaNumerics = sentence.replaceAll(nonAlphaNumericRegex, "");

        //Reverse all the words
        StringBuilder builder = new StringBuilder(
                (Arrays.stream(alphaNumerics.split("\\s"))
                        .map(word -> new StringBuilder(word).reverse().toString())
                        .collect(Collectors.joining(" ")))
        );

        //Re-insert all the non-alphanumerics
        Pattern pattern = Pattern.compile(nonAlphaNumericRegex);
        Matcher matcher = pattern.matcher(sentence);
        while (matcher.find()) {
            builder.insert(matcher.start(), matcher.group());
        }

        return builder.toString();
    }
}
