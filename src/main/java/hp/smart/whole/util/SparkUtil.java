package hp.smart.whole.util;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @Author: SMA
 * @Date: 2017-09-24 18:10
 * @Explain:
 */
public class SparkUtil {
    public static SparkConf createSparkConf(Class klass, String master, String appName, String esHttpNodes, Map<String, String> customizedConf) {
        SmartConfiguration.LOG.debug("[SmartConfiguration] master : {}, appName : {} , esNodes : {}", master, appName, esHttpNodes);

        SparkConf sparkConf = new SparkConf()
                .setMaster(master)
                .setAppName(appName)
                .setJars(JavaSparkContext.jarOfClass(klass))
                .set("spark.akka.timeout", "1000")
                .set("spark.cores.max", SmartConfiguration.getInstance().get("spark.task.cores"))
                .set("spark.default.parallelism", "50")
                .set("spark.executor.memory", "2500m")
                .set("spark.executor.cores", "2")
                .set("spark.yarn.jar", SmartConfiguration.getInstance().get("spark.yarn.jar"))
                .set("spark.speculation.multiplier", "1.0")
                // 添加spark重试次数
                .set("spark.port.maxRetries", "100");

        String sparkExecutorOpts = "";
        try {
            sparkExecutorOpts = sparkConf.get("spark.executor.extraJavaOptions");
            if (sparkExecutorOpts == null) sparkExecutorOpts = "";
        } catch (NoSuchElementException ignore) {
        }
        sparkConf.set("spark.executor.extraJavaOptions", sparkExecutorOpts + " -XX:+UseConcMarkSweepGC");
        sparkExecutorOpts = "";
        try {
            sparkExecutorOpts = sparkConf.get("spark.driver.extraJavaOptions");
            if (sparkExecutorOpts == null) sparkExecutorOpts = "";
        } catch (NoSuchElementException ignore) {
        }
        sparkConf.set("spark.driver.extraJavaOptions", sparkExecutorOpts + " -XX:+UseConcMarkSweepGC");

        String sparkHome = System.getenv("SPARK_HOME");
        if (sparkHome != null) {
            sparkConf.setSparkHome(sparkHome);
        }

        if (customizedConf != null)
            for (Map.Entry<String, String> e : customizedConf.entrySet()) {
                sparkConf.set(e.getKey(), e.getValue());
            }

        return sparkConf;
    }
}
