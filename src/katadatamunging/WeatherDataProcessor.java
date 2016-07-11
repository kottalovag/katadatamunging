package katadatamunging;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class WeatherDataProcessor {

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
            DataProcessor dp = new DataProcessor();
            dp.processStream(br);
        } catch (FileNotFoundException exc) {
            System.err.println("Input file not found");
        } catch (IOException exc) {
            System.out.println("Problem while reading file: " + exc.toString());
        }
    }
}
