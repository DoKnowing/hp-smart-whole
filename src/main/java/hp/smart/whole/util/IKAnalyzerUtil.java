package hp.smart.whole.util;


import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: SMA
 * @date: 2017-10-19 15:03
 * @explain:
 */
public class IKAnalyzerUtil {

    protected static IKAnalyzer analyzer = new IKAnalyzer(false);
    protected static IKAnalyzer smarAnalyzer = new IKAnalyzer(true);

    public static Set<String> analyzer(String str) throws IOException {
        return analyzer(str, false);
    }

    public static Set<String> analyzer(String str, boolean smartAnalyzer) throws IOException {
        Set<String> set = null;
        if (StringUtils.isNotEmpty(str)) {
            StringReader reader = new StringReader(str);
            TokenStream ts = null;
            if (smartAnalyzer) {
                ts = smarAnalyzer.tokenStream("", reader);
            } else {
                ts = analyzer.tokenStream("", reader);
            }

            CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
            ts.reset();
            set = new HashSet<String>();
            while (ts.incrementToken()) {
                set.add(term.toString());
            }
            ts.close();
            reader.close();
        }
//        close();
        return set;

    }

    public static void close() {
        if (analyzer != null) {
            analyzer.close();
        }
        if (smarAnalyzer != null) {
            smarAnalyzer.close();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(analyzer("今天是个好的天气,我好想出去玩.", true));
        System.out.println(analyzer("今天是个好的天气,我好想出去玩.", true));
        System.out.println(analyzer("今天是个好的天气,我好想出去玩.", true));
    }
}
