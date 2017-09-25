package hp.smart.whole.util;

import org.apache.hadoop.conf.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * @Author: SMA
 * @Date: 2017-09-18 10:15
 * @Explain:
 */
public class SmartConfiguration extends Configuration {
    public static final Logger LOG = LoggerFactory.getLogger(SmartConfiguration.class);

    private static SmartConfiguration _singleton = null;

    private SmartConfiguration() {
        this.addResource("dev/smart-config.xml");
        this.addResource("dev/core-site.xml");
        this.addResource("dev/hdfs-site.xml");
        this.addResource("dev/mapred-site.xml");
        this.addResource("dev/yarn-site.xml");
    }

    public static SmartConfiguration getInstance() {
        if (_singleton == null) {
            synchronized (SmartConfiguration.class) {
                if (_singleton == null) {
                    _singleton = new SmartConfiguration();
                }
            }
        }
        return _singleton;
    }

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
