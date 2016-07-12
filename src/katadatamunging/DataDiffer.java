package katadatamunging;

public class DataDiffer {
    public static Integer calculateIntDiff(String line, DataRuler dataRuler, int idx1, int idx2) throws DataFormatException {
        String rawData1 = dataRuler.extractContent(line, idx1);
        String rawData2 = dataRuler.extractContent(line, idx2);
        Integer data1 = DataUtility.extractIntFromRawValue(rawData1);
        Integer data2 = DataUtility.extractIntFromRawValue(rawData2);
        if (data1 == null || data2 == null) {
            return null;
        }
        return Math.abs(data1 - data2);
    }
}
