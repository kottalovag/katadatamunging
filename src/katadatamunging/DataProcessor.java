/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package katadatamunging;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kottalovag
 */

public class DataProcessor {
    //implementation specific
    private Integer winnerDay = null;
    private Integer maxDiff = null;
    
    private final Pattern numberPattern = Pattern.compile("(\\-?\\d+\\.?\\d?)");
    
    public DataProcessor() {}
    
    private boolean containsNumber(String content) {
        return numberPattern.matcher(content).find();
    }
    
    private Integer extractIntFromRawValue(String value) {
        Matcher matcher = numberPattern.matcher(value);
        if (!matcher.find()) {
            return null;
        }
        String rawNum = matcher.group();
        return Integer.parseInt(rawNum);
    }
    
    //implementation specific
    private boolean isValidDataLine(String line, DataRuler dataRuler) throws DataFormatException {
        if (!dataRuler.mightProvideContent(line, 2)) {
            return false;
        }
        String rawDay = dataRuler.extractContent(line, 0);
        boolean dayColumnIsCorrect = rawDay.matches("\\d+");
        return dayColumnIsCorrect; 
    }
    
    //implementation specific
    private void processDataLine(String line, DataRuler dataRuler) throws DataFormatException {
        String rawDay = dataRuler.extractContent(line, 0);
        String rawMaxT = dataRuler.extractContent(line, 1);
        String rawMinT = dataRuler.extractContent(line, 2);
        Integer day = extractIntFromRawValue(rawDay);
        Integer maxT = extractIntFromRawValue(rawMaxT);
        Integer minT = extractIntFromRawValue(rawMinT);
        if (day == null || minT == null || maxT == null) {
            return; // skip
        }
        int diff = maxT - minT;
        if (winnerDay == null || maxDiff < diff ) {
            winnerDay = day;
            maxDiff = diff;
        }
    }
    
    private void debugDataLine(String line, DataRuler dataRuler) throws DataFormatException {
        System.out.println("[" + line + "] (len: " + line.length() + ")");
        for (int i = 0; i < dataRuler.getNumHeaders(); ++i) {
            String raw = dataRuler.extractContent(line, i);
            if (raw.equals("")) {
                System.out.print("- ");
            } else {
                System.out.print(raw + " ");
            }
        }
        System.out.println();
    }
    
    public void processStream(BufferedReader br) throws IOException, DataFormatException {
        String headerLine = br.readLine();
        DataRuler dataRuler = new DataRuler(headerLine, "\\s+");
        
        String line;
        while ((line = br.readLine()) != null) {
            if (isValidDataLine(line, dataRuler)) {
                processDataLine(line, dataRuler);
                //debugDataLine(line, dataRuler);
            }
        }
        //System.out.println("Best day: " + winnerDay + " with T-spread: " + maxDiff);
        System.out.println(winnerDay);
    }
}
