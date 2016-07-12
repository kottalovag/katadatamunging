package katadatamunging;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataProcessorMain {

    
    public static void performProcessing(IDataLogic dataLogic) {
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws DataFormatException {
        if (args.length < 1) {
            System.out.println("Missing file argument");
            return;
        }
        String inputFileName = args[0];
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFileName));
            String headerLine = br.readLine();        
            //WeatherDataLogic dataLogic = new WeatherDataLogic(headerLine);
            SoccerDataLogic dataLogic = new SoccerDataLogic(headerLine);
            DataProcessor dataProcessor = new DataProcessor(dataLogic);
            dataProcessor.processStream(br);
            System.out.println(dataLogic.processResult(dataProcessor.getWinnerLine()));
        } catch (FileNotFoundException exc) {
            System.err.println("Input file not found:" + exc.toString());
        } catch (IOException exc) {
            System.err.println("Problem while reading file: " + exc.toString());
        }
    }
}
