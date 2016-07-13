package main.java;

import java.io.BufferedReader;
import java.io.IOException;

public class DataProcessor {
    private String winnerLine = null;
    private Integer minDiff = null;
    
    private final IDataLogic dataLogic;
    
    public DataProcessor(IDataLogic dataLogic) {
        this.dataLogic = dataLogic;
    }
    
    public String getWinnerLine() {
        return winnerLine;
    }
    
    public void processStream(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            Integer diff = dataLogic.provideDiff(line);
            if (diff != null) {
                if (winnerLine == null || minDiff > diff ) {
                    winnerLine = line;
                    minDiff = diff;
                }
            }
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
}
