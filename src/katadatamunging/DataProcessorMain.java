package katadatamunging;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataProcessorMain {

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
            //WeatherDataLogic dataLogic = new WeatherDataLogic();
            SoccerDataLogic dataLogic = new SoccerDataLogic();
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
