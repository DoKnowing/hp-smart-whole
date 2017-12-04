package hp.smart.whole.parser.interfaces;

import org.apache.commons.csv.CSVRecord;

/**
 * @Author: SMA
 * @Date: 2017-09-18 10:42
 * @Explain:
 */
public abstract class SmartCsvLineParser implements SmartLineParser<CSVRecord> {
    @Override
    public abstract void parser(CSVRecord csvRecord);
}
