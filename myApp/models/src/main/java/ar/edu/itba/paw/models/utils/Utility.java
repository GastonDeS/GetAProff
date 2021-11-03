package ar.edu.itba.paw.models.utils;

import java.util.StringTokenizer;

public final class Utility {

    private Utility() {};

    public static String capitalizeString(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(str, " ");
        while (tokenizer.hasMoreElements()) {
            stringBuilder.append(capitalizeFirstLetter(tokenizer.nextToken())).append(" ");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    private static String capitalizeFirstLetter(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
}
