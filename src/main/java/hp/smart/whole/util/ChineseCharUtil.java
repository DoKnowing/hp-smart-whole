package hp.smart.whole.util;

import java.util.regex.Pattern;

/**
 * @Author: SMA
 * @Date: 2017-09-18 14:09
 * @Explain: 判断字符, 字符串是否是中文
 */
public class ChineseCharUtil {

    private static final Pattern CJK_PATTERN_REG = Pattern.compile("[\\u4E00-\\u9FBF]+");
    // 大小写不同：\\p 表示包含，\\P 表示不包含
    // \\p{Cn} 的意思为 Unicode 中未被定义字符的编码，\\P{Cn} 就表示 Unicode中已经被定义字符的编码
    private static final Pattern CJK_PATTERN_NAME = Pattern.compile("\\p{InCJK Unified Ideographs}&amp;&amp;\\P{Cn}");

    // 根据Unicode编码完美的判断中文汉字和符号
    public static boolean isChineseChar(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    // 完整的判断中文汉字和符号
    // 只要包含中文汉字或符号都为中文
    public static boolean isChineseStr(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChineseChar(c)) {
                return true;
            }
        }
        return false;
    }

    // 只能判断部分CJK字符（CJK统一汉字）
    public static boolean isChineseByREG(String str) {
        if (str == null) {
            return false;
        }
        return CJK_PATTERN_REG.matcher(str.trim()).find();
    }

    // 只能判断部分CJK字符（CJK统一汉字）
    public static boolean isChineseByName(String str) {
        if (str == null) {
            return false;
        }
        return CJK_PATTERN_NAME.matcher(str.trim()).find();
    }

    public static void main(String[] args) {
        System.out.println(isChineseStr("`"));
    }
}
