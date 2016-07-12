package katadatamunging;

public abstract class DifferDataLogicBase implements IDataLogic {
    protected static final String SEPARATOR_REGEX = "\\s+";
    
    abstract protected int getColumn1();
    abstract protected int getColumn2();
    abstract protected boolean isDataLineValid(String line);
    
    private final DataRuler dataRuler;
    
    protected DataRuler getDataRuler() {
        return dataRuler;
    }
    
    protected DifferDataLogicBase(DataRuler dataRuler) {
        this.dataRuler = dataRuler;
    }
    
    static Integer calculateIntDiff(String line, DataRuler dataRuler, int idx1, int idx2) throws DataFormatException {
        String rawData1 = dataRuler.extractContent(line, idx1);
        String rawData2 = dataRuler.extractContent(line, idx2);
        Integer data1 = DataUtility.extractIntFromRawValue(rawData1);
        Integer data2 = DataUtility.extractIntFromRawValue(rawData2);
        if (data1 == null || data2 == null) {
            return null;
        }
        return Math.abs(data1 - data2);
    }
    
    @Override
    public Integer provideDiff(String line) {
        if (!isDataLineValid(line)) {
            return null;
        }
        int biggerColIdx = Math.max(getColumn1(), getColumn2());
        if (!getDataRuler().mightProvideContent(line, biggerColIdx)) {
            return null;
        }
        try {
            return calculateIntDiff(line, getDataRuler(), getColumn1(), getColumn2());
        } catch (DataFormatException ex) {
            System.err.println("This was not supposed to happen while differing: " + ex);
            return null;
        }
    }
}
