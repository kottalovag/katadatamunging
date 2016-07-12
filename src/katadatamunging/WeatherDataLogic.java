/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package katadatamunging;

import java.io.BufferedReader;
import java.io.IOException;

public class WeatherDataLogic implements IDataLogic {
    private DataRuler dataRuler;
    
    @Override
    public void processHeader(BufferedReader br) throws IOException {
        String headerLine = br.readLine();
        dataRuler = new DataRuler(headerLine, "\\s+");
    }
    
    @Override
    public Integer provideDiff(String line) {
        if (!dataRuler.mightProvideContent(line, 2)) {
            return null;
        }
        boolean dayColumnIsCorrect = false;
        try {
            String rawDay = dataRuler.extractContent(line, 0);
            dayColumnIsCorrect = rawDay.matches("\\d+");
        } catch (DataFormatException ex) {
            System.err.println("This was not supposed to happen with the day: " + ex);
        }
        if (!dayColumnIsCorrect) {
            return null;
        }
        try {
            return DataDiffer.calculateIntDiff(line, dataRuler, 1, 2);
        } catch (DataFormatException ex) {
            System.err.println("This was not supposed to happen while differing: " + ex);
            return null;
        }
    }
    
    public String processResult(String line) {
        try {        
            String content = dataRuler.extractContent(line, 0);
            return content;
        } catch (DataFormatException ex) {
            System.err.println("This was not supposed to happen with the day: " + ex);
        }
        return "";
    }
}
