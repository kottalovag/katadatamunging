package main.java;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataUtility {
    private final static Pattern numberPattern = Pattern.compile("(\\-?\\d+\\.?\\d?)");
    
    public static Integer extractIntFromRawValue(String value) {
        Matcher matcher = numberPattern.matcher(value);
        if (!matcher.find()) {
            return null;
        }
        String rawNum = matcher.group();
        return Integer.parseInt(rawNum);
    }
}
