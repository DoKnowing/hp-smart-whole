package hp.smart.whole.util;

import java.io.InputStream;
import java.io.Serializable;

/**
 * @Author: SMA
 * @Date: 2017-09-18 10:15
 * @Explain:
 */
public class SmartConfiguration implements Serializable {

    public static InputStream getResourceAsInputStream(String resoureName) {
        return SmartConfiguration.class.getClassLoader().getResourceAsStream(resoureName);
    }

    public static String getHome(String resourceName) {
        return SmartConfiguration.class.getClassLoader().getResource(resourceName).getPath();

    }

    public static void main(String[] args) {
        System.out.println(new SmartConfiguration().getHome("tencent_mabiao_0826_new.txt"));
    }
}
