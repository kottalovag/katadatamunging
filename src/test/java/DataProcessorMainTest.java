import java.io.IOException;
import main.java.DataProcessorMain;
import org.junit.Test;
import static org.junit.Assert.*;

public class DataProcessorMainTest {
    private void validateDataProcessor(String testFileName, String mode, String expectedResult) {
        String result = "";
        try {
            result = DataProcessorMain.processInputFile(testFileName, mode);
        } catch (IOException ex) {
            System.err.println("Problem: " + ex);
        }
        assertEquals("DataProcessorMain.processInputFile should return " + expectedResult, 
            expectedResult, result);
    }
    
    @Test public void testDataProcessorMainWithSoccerData() {
        validateDataProcessor(
                "src/test/resources/football.dat", 
                DataProcessorMain.MODE_SOCCER,
                "Aston_Villa"
        );
    }
    
    @Test public void testDataProcessorMainWithWeatherData() {
        validateDataProcessor(
                "src/test/resources/weather.dat", 
                DataProcessorMain.MODE_WEATHER,
                "14"
        );
    }
}
