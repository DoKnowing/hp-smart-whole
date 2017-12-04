package hp.smart.whole.comparator;

import hp.smart.whole.util.ChineseCharUtil;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.TreeSet;

/**
 * @Author: SMA
 * @Date: 2017-09-18 13:56
 * @Explain: 比较器: 数字 < 字母 < 汉字
 */
public class SmartComparator implements Comparator<String> {
    private static final Collator collator = Collator.getInstance(Locale.CHINA);

    @Override
    public int compare(String o1, String o2) {
        return compareStr(o1, o2);
    }

    /**
     * 单个字段字符的比较,ASCII表: 数字 < 字母 < 汉字
     *
     * @param c1
     * @param c2
     * @return
     */
    public static int compare(char c1, char c2) {
        if (c1 == c2) {
            return 0;
        }
        boolean isChineseCharC1 = ChineseCharUtil.isChineseChar(c1);
        boolean isChineseCharC2 = ChineseCharUtil.isChineseChar(c2);
        if (!isChineseCharC1 && !isChineseCharC2) {
            return c1 - c2;
        } else if ((!isChineseCharC1 && isChineseCharC2) || (isChineseCharC1 && !isChineseCharC2)) {
            if (isChineseCharC1) {
                return 1;
            } else {
                return -1;
            }
        }
        return collator.compare(String.valueOf(c1), String.valueOf(c2));
    }

    /**
     * 字符串比较
     *
     * @param o1
     * @param o2
     * @return
     */
    public static int compareStr(String o1, String o2) {
        if (o1 == null && o2 == null) {
            return 0;
        }
        if (o1 == null) {
            return -1;
        }
        if (o2 == null) {
            return 1;
        }
        if ("".equals(o1.trim()) && "".equals(o2.trim())) {
            return 0;
        }
        if (o1.trim().equals(o2.trim())) {
            return 0;
        }
        char[] cArray1 = o1.toCharArray();
        char[] cArray2 = o2.toCharArray();
        int comparatorLength = cArray1.length > cArray2.length ? cArray2.length : cArray1.length;
        for (int i = 0; i < comparatorLength; i++) {
            if (compare(cArray1[i], cArray2[i]) > 0) {
                return 1;
            } else if (compare(cArray1[i], cArray2[i]) < 0) {
                return -1;
            }
        }
        if (cArray1.length > cArray2.length) {
            return 1;
        } else if (cArray1.length < cArray2.length) {
            return -1;
        }
        return 0;
    }

    public static void main(String[] args) {
        TreeSet<String> set = new TreeSet<>(new SmartComparator());
        set.addAll(Arrays.asList("122", "Aa", "133", "A", "一不做,二不休", "一不做", "二"));
        System.out.println(set);

    }
}
