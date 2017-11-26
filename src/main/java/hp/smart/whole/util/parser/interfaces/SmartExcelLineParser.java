package hp.smart.whole.util.parser.interfaces;

import org.apache.poi.ss.usermodel.Row;

/**
 * @Author: SMA
 * @Date: 2017-09-18 10:38
 * @Explain:
 */
public abstract class SmartExcelLineParser implements SmartLineParser<Row> {
    @Override
    public abstract void parser(Row row);
}
