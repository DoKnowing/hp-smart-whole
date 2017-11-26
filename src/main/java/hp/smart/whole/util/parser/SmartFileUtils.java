package hp.smart.whole.util.parser;

import hp.smart.whole.util.parser.interfaces.SmartCsvLineParser;
import hp.smart.whole.util.parser.interfaces.SmartExcelLineParser;
import hp.smart.whole.util.parser.interfaces.SmartTxtLineParser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 * @Author: SMA
 * @Date: 2017-09-18 10:30
 * @Explain: 实现读取Excel, Csv, txt文件
 */
public class SmartFileUtils {

    public static void loadExcelFile(String path, SmartExcelLineParser parser) throws Exception {
        loadExcelFile(path, 0, false, parser);
    }

    public static void loadExcelFile(String path, int sheetNumber, boolean returnHead, SmartExcelLineParser parser) throws Exception {
        Sheet sheet = SmartFileLoadUtil.loadExcel(path, sheetNumber);
        // 获取工作表的总行数
        int number = sheet.getLastRowNum();
        // 遍历每一行
        int count = 0;
        if (!returnHead) {
            count = 1;
        }
        while (count <= number) {
            parser.parser(sheet.getRow(count));
            count++;
        }
        sheet = null;
    }

    public static void loadCsvFile(String path, SmartCsvLineParser parser) throws IOException {
        loadCsvFile(path, SmartFileLoadUtil.FileCode.GBK, parser);
    }

    public static void loadCsvFile(String path, SmartFileLoadUtil.FileCode code, SmartCsvLineParser parser) throws IOException {
        loadCsvFile(path, code == SmartFileLoadUtil.FileCode.GBK ? "GBK" : "UTF-8", CSVFormat.DEFAULT, false, parser);
    }

    public static void loadCsvFile(String path, String code, CSVFormat format, boolean returnHead, SmartCsvLineParser parser) throws IOException {
        CSVParser csvParser = SmartFileLoadUtil.loadCSV(path, code, format);
        Iterator<CSVRecord> iterator = csvParser.iterator();
        // 去掉header
        if (!returnHead && iterator.hasNext()) {
            iterator.next();
        }
        while (iterator.hasNext()) {
            parser.parser(iterator.next());
        }
        csvParser.close();
        csvParser = null;
    }

    public static void loadTxtFile(String path, SmartTxtLineParser parser) throws IOException {
        loadTxtFile(path, "UTF-8", parser);
    }

    public static void loadTxtFile(String path, String code, SmartTxtLineParser parser) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(SmartFileLoadUtil.getResourceAsInputStream(path), code));
        String line = "";
        while ((line = reader.readLine()) != null) {
            parser.parser(line);
        }
        reader.close();
        reader = null;
    }
}
