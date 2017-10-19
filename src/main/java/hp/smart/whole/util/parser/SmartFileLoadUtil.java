package hp.smart.whole.util.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;

/**
 * @Author: SMA
 * @Date: 2017-09-18 10:45
 * @Explain:
 */
public class SmartFileLoadUtil {
    public static enum FileCode {
        GBK,
        UTF_8;

        private FileCode() {
        }
    }

    /*------------------ SUFFIX ------------------*/
    public static final String XLSX_SUFFIX = ".xlsx";
    public static final String XLS_SUFFIX = ".xls";

    /*------------------ CODE ------------------*/
    public static final String CODE_GBK = "GBK";
    public static final String CODE_UTF_8 = "UTF-8";


    public static InputStream getResourceAsInputStream(String resoureName) {
        return SmartFileLoadUtil.class.getClassLoader().getResourceAsStream(resoureName);
    }

    public static String getFullPath(String resourceName) {
        return getFullPath(resourceName, true);

    }

    public static String getFullPath(String resourceName, boolean isFull) {
        if (isFull) {
            return resourceName;
        }
        return SmartFileLoadUtil.class.getClassLoader().getResource(resourceName).getPath();
    }

    public static Sheet loadExcel(String excelName, int sheetNumber) throws Exception {
        // estimate file is excel(xlsx.xls) file
        File file = new File(getFullPath(excelName));
        if (!file.exists()) {
            file = new File(getFullPath(excelName, false));
        }
        if (!file.exists() || !file.isFile()) {
            throw new Exception(file.getAbsolutePath() + " is not exists or it's not file");
        }
        String fileName = file.getName().equals(excelName) ? excelName : null;
        if (fileName == null || !(fileName.endsWith(XLSX_SUFFIX) | fileName.endsWith(XLS_SUFFIX))) {
            throw new Exception(file.getAbsolutePath() + " is not excel which is must endwith xlsx/xls");
        }

        return WorkbookFactory.create(file).getSheetAt(sheetNumber);
    }

    public static CSVParser loadCSV(String resourceName) throws IOException {
        return loadCSV(resourceName, "GBK", CSVFormat.DEFAULT);
    }

    public static CSVParser loadCSV(String resourceName, FileCode code) throws IOException {
        return loadCSV(resourceName, code == FileCode.GBK ? "GBK" : "UTF-8", CSVFormat.DEFAULT);
    }

    public static CSVParser loadCSV(String resourceName, String code, CSVFormat format) throws IOException {
        return new CSVParser(new BufferedReader(new InputStreamReader(getResourceAsInputStream(resourceName), code)), format);
    }


    public static void main(String[] args) {
        System.out.println(true | true);
    }
}

