package katadatamunging;

public class SoccerDataLogic extends DifferDataLogicBase {
    private final int columnFIdx;
    private final int columnAIdx;
    
    private static final String NUM_HEADER = "##";
    private static final String HYPHEN_HEADER = "-";
    
    private static DataRuler createDataRuler(String headerLine) {
        StringBuilder sb = new StringBuilder(headerLine.length());
        sb
                .append(headerLine, 0, 3)
                .append(NUM_HEADER)
                .append(headerLine, 3 + NUM_HEADER.length(), 47)
                .append(HYPHEN_HEADER)
                .append(headerLine, 47 + HYPHEN_HEADER.length(), headerLine.length());
        headerLine = sb.toString();
        return new DataRuler(headerLine, SEPARATOR_REGEX);
    }
    
    public SoccerDataLogic(String headerLine) {
        super(createDataRuler(headerLine));
        columnFIdx = getDataRuler().getColumnIdx("F");
        columnAIdx = getDataRuler().getColumnIdx("A");
    }

    @Override
    protected int getColumn1() {
        return columnFIdx;
    }

    @Override
    protected int getColumn2() {
        return columnAIdx;
    }

    @Override
    protected boolean isDataLineValid(String line) {
        try {
            String rawNum = getDataRuler().extractContent(line, NUM_HEADER);
            return rawNum.matches("\\d+\\.");
        } catch (DataFormatException ex) {
            return false;
        }
    }    
    
    public String processResult(String line) {
        try {        
            String content = getDataRuler().extractContent(line, "Team");
            return content;
        } catch (DataFormatException ex) {
            System.err.println("This was not supposed to happen with the Team: " + ex);
        }
        return "";
    }
}
