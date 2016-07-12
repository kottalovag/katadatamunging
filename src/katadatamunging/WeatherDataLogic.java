package katadatamunging;

public class WeatherDataLogic extends DifferDataLogicBase {
    private static final String DAY_REGEX = "\\d+";
    
    private final int columnDayIdx = 0;
    private final int columnMaxTIdx = 1;
    private final int columnMinTIdx = 2;
    
    public WeatherDataLogic(String headerLine) {
        super(new DataRuler(headerLine, SEPARATOR_REGEX));
    }

    @Override
    protected int getColumn1() {
        return columnMaxTIdx;
    }

    @Override
    protected int getColumn2() {
        return columnMinTIdx;
    }
    
    private String extractDay(String line) throws DataFormatException {
        return getDataRuler().extractContent(line, columnDayIdx);
    }

    @Override
    protected boolean isDataLineValid(String line) {
        try {
            if (getDataRuler().mightProvideContent(line, columnDayIdx)) {
                return extractDay(line).matches(DAY_REGEX);
            }
        } catch (DataFormatException ex) {
            System.err.println("This was not supposed to happen with the day: " + ex);
        } 
        return false;
    }
    
    public String processResult(String line) {
        try {        
            return extractDay(line);
        } catch (DataFormatException ex) {
            System.err.println("This was not supposed to happen with the day: " + ex);
            return "";
        }
    }
}
