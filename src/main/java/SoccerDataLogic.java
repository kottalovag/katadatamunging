package main.java;

public class SoccerDataLogic extends DifferDataLogicBase {    
    private static final String NUM_HEADER = "##";
    private static final int NUM_HEADER_START_POS = 3;
    private static final String DASH_HEADER = "-";
    private static final int DASH_HEADER_START_POS = 47;
    private static final String TEAM_HEADER = "Team";
    private static final String FOR_HEADER = "F";
    private static final String AGAINST_HEADER = "A";
    
    private static final String NUM_REGEX = "\\d+\\.";
    
    private final int columnFIdx;
    private final int columnAIdx;
    
    private static DataRuler createDataRuler(String headerLine) {
        StringBuilder sb = new StringBuilder(headerLine.length());
        sb
                .append(headerLine, 0, NUM_HEADER_START_POS)
                .append(NUM_HEADER)
                .append(headerLine, NUM_HEADER_START_POS + NUM_HEADER.length(), DASH_HEADER_START_POS)
                .append(DASH_HEADER)
                .append(headerLine, DASH_HEADER_START_POS + DASH_HEADER.length(), headerLine.length());
        headerLine = sb.toString();
        return new DataRuler(headerLine, SEPARATOR_REGEX);
    }
    
    public SoccerDataLogic(String headerLine) {
        super(createDataRuler(headerLine));
        columnFIdx = getDataRuler().getColumnIdx(FOR_HEADER);
        columnAIdx = getDataRuler().getColumnIdx(AGAINST_HEADER);
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
            return rawNum.matches(NUM_REGEX);
        } catch (DataFormatException ex) {
            return false;
        }
    }    
    
    @Override
    public String processResult(String line) {
        try {        
            String content = getDataRuler().extractContent(line, TEAM_HEADER);
            return content;
        } catch (DataFormatException ex) {
            System.err.println("This was not supposed to happen with the Team: " + ex);
        }
        return "";
    }
}
