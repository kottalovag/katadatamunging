package katadatamunging;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataRuler {
    final private String SEPARATOR_REGEX;
    final private Pattern separatorPattern;
    final private String[] headers;
    final private Integer[] headerStartPositions;
    final private HashMap<String, Integer> headerColumnIndices;
    
    public DataRuler(String headerLine, String separatorRegex) {
        SEPARATOR_REGEX = separatorRegex;
        separatorPattern = Pattern.compile(SEPARATOR_REGEX);
        headers = headerLine.trim().split(SEPARATOR_REGEX);
        headerStartPositions = new Integer[headers.length];
        headerColumnIndices = new HashMap<>();
        int actPos = 0;
        int actColumnIdx = 0;
        for (String entry: headers) {
            headerColumnIndices.put(entry, actColumnIdx);
            int startIdx = headerLine.indexOf(entry, actPos);
            headerStartPositions[actColumnIdx] = startIdx;
            actPos = startIdx + entry.length();
            ++actColumnIdx;
        }
    }
    
    public int getNumHeaders() {
        return headers.length;
    }
    
    public int getColumnIdx(String header) {
        return headerColumnIndices.get(header);
    }
    
    public boolean mightProvideContent(String line, int columnIdx) {
        int headerStartPos = headerStartPositions[columnIdx];
        return line.length() >= headerStartPos;
    }
    
    public boolean mightProvideContent(String line, String header) {
        int columnIdx = headerColumnIndices.get(header);
        return mightProvideContent(line, columnIdx);
    }
    
    private int detectContentLeftBound(String line, int columnIdx) throws DataFormatException {
        /* The gap between two data entries is uncertain due to the freedom
        of the data format. 
        Concerns:
        - The boundaries of a header entry do not necessarily form a boundary for 
        the data column: the data header might be too short to 'cover' the whole 
        width of its data column.
        - A value might be missing so we cannot rely on splitting the data line
        along separators.
        The only fix rules seem to be the followings:
        - There is at least one separator between data header entries.
        - Any present value will overlap with one and only one data header.
        */
        if (columnIdx == 0) {
            return 0;
        }
        //We will project the gap columns between two data headers to the data line
        //and search for the left boundary of the value.
        int prevColumnIdx = columnIdx - 1;
        int gapStartPos = headerStartPositions[prevColumnIdx] 
                + headers[prevColumnIdx].length() - 1; 
        //-1 because of the possibility of a single space gap separator 
        //aligned with the previous header's last character
        
        int headerStartPos = headerStartPositions[columnIdx];
        String gap = line.substring(gapStartPos, headerStartPos + 1);
        //+1 because of the possibility of a single space gap separator
        //aligned with the the header's first character
        
        String reversedGap = new StringBuilder(gap).reverse().toString();
        Matcher matcher = separatorPattern.matcher(reversedGap);
        if (!matcher.find()) {
            throw new DataFormatException("Format problem: no separator before data column "
                    + columnIdx + " in line:" + line);
        }
        int contentLeftBound = headerStartPos - matcher.start();
        return contentLeftBound;
    }
    
    private int detectContentRightBound(String line, int columnIdx) throws DataFormatException {
        if (columnIdx == headers.length - 1) {
            return line.length() - 1;
        }
        int nextContentLeftBound = detectContentLeftBound(line, columnIdx + 1);
        return nextContentLeftBound - 1;
    }
    
    public String extractContent(String line, int columnIdx) throws DataFormatException {
        int leftBound = detectContentLeftBound(line, columnIdx);
        int rightBound = detectContentRightBound(line, columnIdx);
        String contentPlaceHolder = line.substring(leftBound, rightBound + 1);
        String strippedContent = separatorPattern.matcher(contentPlaceHolder).replaceAll("");
        return strippedContent;
    }
    
    public String extractContent(String line, String header) throws DataFormatException {
        int columnIdx = headerColumnIndices.get(header);
        return DataRuler.this.extractContent(line, columnIdx);
    }
}