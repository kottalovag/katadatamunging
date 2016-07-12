/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package katadatamunging;

import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author kottalovag
 */
public class SoccerDataLogic implements IDataLogic {
    private DataRuler dataRuler;
    private int columnFIdx;
    private int columnAIdx;
    
    private final String NUM_HEADER = "##";
    
    @Override
    public void processHeader(BufferedReader br) throws IOException {
        String headerLine = br.readLine();
        StringBuilder sb = new StringBuilder(headerLine.length());
        sb.append(headerLine, 0, 3)
                .append(NUM_HEADER)
                .append(headerLine, 3 + NUM_HEADER.length(), 47)
                .append("-")
                .append(headerLine, 48, headerLine.length());
        headerLine = sb.toString();
        dataRuler = new DataRuler(headerLine, "\\s+");
        columnFIdx = dataRuler.getColumnIdx("F");
        columnAIdx = dataRuler.getColumnIdx("A");
    }

    @Override
    public Integer provideDiff(String line) {
        boolean lineIsValid = false;
        try {
            String rawNum = dataRuler.extractContent(line, NUM_HEADER);
            lineIsValid = rawNum.matches("\\d+\\.");
        } catch (DataFormatException ex) {
            // This is OK here, in case we cannot parse, we jump over the line
        }
        if (!lineIsValid) {
            return null;
        }
        if (!dataRuler.mightProvideContent(line, columnFIdx)) {
            return null;
        }
        if (!dataRuler.mightProvideContent(line, columnAIdx)) {
            return null;
        }
        try {
            return DataDiffer.calculateIntDiff(line, dataRuler, columnFIdx, columnAIdx);
        } catch (DataFormatException ex) {
            System.err.println("This was not supposed to happen while differing: " + ex);
            return null;
        }
    }
    
    public String processResult(String line) {
        try {        
            String content = dataRuler.extractContent(line, "Team");
            return content;
        } catch (DataFormatException ex) {
            System.err.println("This was not supposed to happen with the day: " + ex);
        }
        return "";
    }
}
