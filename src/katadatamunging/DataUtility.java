/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package katadatamunging;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kottalovag
 */
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
