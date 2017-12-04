package hp.smart.whole.comparator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * @Author: SMA
 * @Date: 2017-09-18 13:26
 * @Explain: 字符串比较器, 中文无法比较
 */
@Deprecated
public class StringComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
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
            if (cArray1[i] > cArray2[i]) {
                return 1;
            } else if (cArray1[i] < cArray2[i]) {
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

    @Override
    public boolean equals(Object obj) {
        return this.equals(obj);
    }

    public static void main(String[] args) {
//        TreeSet<String> set = new TreeSet<>(new StringComparator());
        TreeSet<String> set = new TreeSet<>();
        set.addAll(Arrays.asList("asd", "sd", "er", "era"));
        System.out.println(set);

    }
}
