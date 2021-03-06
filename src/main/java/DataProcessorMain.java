package main.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class DataProcessorMain {    
    public static final String MODE_WEATHER = "weather";
    public static final String MODE_SOCCER = "soccer";
    public static final String[] MODES = {MODE_WEATHER, MODE_SOCCER};
    
    private static boolean checkArgs(String[] args) {
        boolean ok = true;
        if (args.length < 1) {
            System.out.println("Missing arg. 1: data logic mode " + Arrays.toString(MODES));
            ok = false;
        } else {
            boolean foundMode = false;
            for (String mode : MODES) {
                if (mode.equals(args[0])) {
                    foundMode = true;
                }
            }
            if (!foundMode) {
                System.out.println("Arg. 1: selected [" + args[0] 
                        + "] data logic mode is incorrect. Select one of " 
                        + Arrays.toString(MODES));
            }
            ok = ok && foundMode;
        }
        if (args.length < 2) {
            System.out.println("Missing arg. 2: file path");
            ok = false;
        }
        return ok;
    }
    
    public static IDataLogic createDataLogic(String mode, String headerLine) {
        switch (mode) {
            case MODE_WEATHER:
                return new WeatherDataLogic(headerLine);
            case MODE_SOCCER:
                return new SoccerDataLogic(headerLine);
            default:
                return null;
        }
    }
    
    public static String processInputFile(String inputFileName, String mode) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(inputFileName));
        String headerLine = br.readLine();        
        IDataLogic dataLogic = createDataLogic(mode, headerLine);
        DataProcessor dataProcessor = new DataProcessor(dataLogic);
        dataProcessor.processStream(br);
        return dataLogic.processResult(dataProcessor.getWinnerLine());
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (!checkArgs(args)) {
            return;
        }
        String inputFileName = args[1];
        String mode = args[0];
        try {
            String result = processInputFile(inputFileName, mode);
            System.out.println(result);
            return;
        } catch (FileNotFoundException exc) {
            System.err.println("Input file not found:" + exc.toString());
        } catch (IOException exc) {
            System.err.println("Problem while reading file: " + exc.toString());
        }
        System.out.println("There were errors during processing.");
    }
}
