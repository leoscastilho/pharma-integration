package com.estee.na.util;

import lombok.extern.slf4j.Slf4j;

import java.text.Normalizer;

@Slf4j
public class ParserUtil {

    public static boolean nonNullNonEmpty(String s) {
        return s != null && !s.isEmpty();
    }

    /**
     * Returns true if string is null or blank
     */
    public static boolean isEmpty(String s){
        return ! nonNullNonEmpty(s);
    }

    /**
     * Normalizes a name
     * <p>
     * https://blog.mafr.de/2015/10/10/normalizing-text-in-java/
     *
     * @param s text to be normalized
     * @return normalized name
     */
    public static String normalize(String s) {

        if (s == null) {
            return null;
        }

        // Stripping trailing white spaces
        String normalized = s.trim();

        // Manual cases
        normalized = normalized.replaceAll("Æ", "AE")
                .replaceAll("Ð", "D")
                .replaceAll("Ø", "O")
                .replaceAll("Œ", "OE")
                .replaceAll("ß", "SS")
                .replaceAll("Þ", "TH");

        // Canonical decomposition
        normalized = Normalizer.normalize(normalized, Normalizer.Form.NFD);

        // Removing non-ASCII characters
        normalized = normalized.replaceAll("[^\\p{ASCII}]", "");

        // Removing intra string white spaces
        normalized = normalized.replaceAll("\\s+", "-")
                .replaceAll("[^-a-zA-Z0-9]", "");

        // Upper case
        normalized = normalized.toUpperCase();

        return normalized;
    }

}
