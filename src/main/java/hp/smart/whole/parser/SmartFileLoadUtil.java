package hp.smart.whole.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;

/**
 * @Author: SMA
 * @Date: 2017-09-18 10:45
 * @Explain:
 */
public class SmartFileLoadUtil {
    public enum FileCode {
        GBK("GBK"),
        UTF_8("UTF-8");
        private String chartSet;

        private FileCode(String chartSet) {
            this.chartSet = chartSet;
        }

        public String getChartSet() {
            return chartSet;
        }

        public void setChartSet(String chartSet) {
            this.chartSet = chartSet;
        }
    }

    /*------------------ SUFFIX ------------------*/
    private static final String XLSX_SUFFIX = ".xlsx";
    private static final String XLS_SUFFIX = ".xls";

    public static InputStream getResourceAsInputStream(String resoureName) throws FileNotFoundException {
        return SmartFileLoadUtil.class.getClassLoader().getResourceAsStream(resoureName);
    }

    /**
     * 加载 excel文件
     *
     * @param path
     * @param sheetNumber
     * @return
     * @throws Exception
     */
    protected static Sheet loadExcel(String path, int sheetNumber) throws Exception {
        // estimate file is excel(xlsx.xls) file
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException(path + " is not exists or it's not file");
        }
        String fileName = file.getName();
        if (StringUtils.isEmpty(fileName) || !(fileName.endsWith(XLSX_SUFFIX) | fileName.endsWith(XLS_SUFFIX))) {
            throw new Exception(path + " is not excel which is must end with \".xlsx\" or \".xls\"");
        }
        return WorkbookFactory.create(file).getSheetAt(sheetNumber);
    }

    /**
     * 加载 csv文件
     *
     * @param path
     * @return
     * @throws IOException
     */
    protected static CSVParser loadCSV(String path) throws IOException {
        return loadCSV(path, FileCode.GBK, CSVFormat.DEFAULT);
    }

    /**
     * 加载 csv文件
     *
     * @param path
     * @param code
     * @return
     * @throws IOException
     */
    protected static CSVParser loadCSV(String path, FileCode code) throws IOException {
        return loadCSV(path, code, CSVFormat.DEFAULT);
    }

    /**
     * 加载 csv文件
     *
     * @param path
     * @param code
     * @param format
     * @return
     * @throws IOException
     */
    protected static CSVParser loadCSV(String path, FileCode code, CSVFormat format) throws IOException {
        return new CSVParser(new BufferedReader(new InputStreamReader(new FileInputStream(path), code.getChartSet())), format);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("smart-config.xml")));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }
}

