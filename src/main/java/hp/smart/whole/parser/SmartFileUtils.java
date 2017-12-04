package hp.smart.whole.parser;

import hp.smart.whole.parser.interfaces.SmartCsvLineParser;
import hp.smart.whole.parser.interfaces.SmartExcelLineParser;
import hp.smart.whole.parser.interfaces.SmartTxtLineParser;
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

    /**
     * 加载 excel文件
     *
     * @param path
     * @param parser
     * @throws Exception
     */
    public static void loadExcelFile(String path, SmartExcelLineParser parser) throws Exception {
        loadExcelFile(path, 0, false, parser);
    }

    /**
     * 处理excel文件
     *
     * @param path
     * @param sheetNumber
     * @param containHeader
     * @param parser
     * @throws Exception
     */
    public static void loadExcelFile(String path, int sheetNumber, boolean containHeader, SmartExcelLineParser parser) throws Exception {
        loadExcelFile(path, sheetNumber, containHeader ? 0 : 1, parser);
    }

    /**
     * 处理excel文件
     *
     * @param path
     * @param sheetNumber
     * @param begin
     * @param parser
     * @throws Exception
     */
    public static void loadExcelFile(String path, int sheetNumber, int begin, SmartExcelLineParser parser) throws Exception {
        Sheet sheet = SmartFileLoadUtil.loadExcel(path, sheetNumber);
        // 获取工作表的总行数
        int number = sheet.getLastRowNum();
        // 遍历每一行
        int count = begin;

        while (count <= number) {
            parser.parser(sheet.getRow(count));
            count++;
        }
        sheet = null;
    }

    /**
     * 处理csv文件
     *
     * @param path
     * @param parser
     * @throws IOException
     */
    public static void loadCsvFile(String path, SmartCsvLineParser parser) throws IOException {
        loadCsvFile(path, SmartFileLoadUtil.FileCode.GBK, parser);
    }

    /**
     * 处理csv文件
     *
     * @param path
     * @param code
     * @param parser
     * @throws IOException
     */
    public static void loadCsvFile(String path, SmartFileLoadUtil.FileCode code, SmartCsvLineParser parser) throws IOException {
        loadCsvFile(path, code, CSVFormat.DEFAULT, false, parser);
    }

    public static void loadCsvFile(String path, SmartFileLoadUtil.FileCode code, CSVFormat format, boolean containHeader, SmartCsvLineParser parser) throws IOException {
        CSVParser csvParser = SmartFileLoadUtil.loadCSV(path, code, format);
        Iterator<CSVRecord> iterator = csvParser.iterator();
        // 去掉header
        if (!containHeader && iterator.hasNext()) {
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
