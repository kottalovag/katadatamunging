import java.io.IOException;
import main.java.DataProcessorMain;
import org.junit.Test;
import static org.junit.Assert.*;

public class SoccerDataTest {
    @Test public void testDataProcessorMainWithSoccerData() {
        String testFileName = "input/football.dat";
        String mode = DataProcessorMain.MODE_SOCCER;
        String expectedResult = "Aston_Villa";
        String result = "";
        try {
            result = DataProcessorMain.processInputFile(testFileName, mode);
        } catch (IOException ex) {
            System.out.println("Problem: " + ex);
        }
        assertEquals("DataProcessorMain.processInputFile should return " + expectedResult, 
                expectedResult, result);
    }
}
