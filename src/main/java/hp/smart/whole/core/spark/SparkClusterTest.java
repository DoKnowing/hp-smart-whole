package hp.smart.whole.core.spark;

import hp.smart.whole.util.SmartConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.storage.StorageLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple3;

import java.io.Serializable;

/**
 * @Author: SMA
 * @Date: 2017-09-25 17:53
 * @Explain:
 */
public class SparkClusterTest implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(SparkClusterTest.class);


    public SparkConf createSparkConf() {
        SparkConf sparkConf = new SparkConf().setAppName("SparkClusterTestAlone")
//                .setMaster("local[2]")
                .setMaster("spark://smart-master:9099")
                .setJars(JavaSparkContext.jarOfClass(SparkClusterTest.class))
                .set("spark.cores.max", "2")
                .set("spark.local.dir", "/tmp/spark")
                .set("spark.executor.memory", "1024m")
                .set("spark.default.parallelism", "2")
                .set("spark.eventLog.enabled", "true")
                .set("spark.eventLog.dir", "hdfs://smart-master:8020/spark/logs/histoty")
                .set("spark.history.fs.logDirectory", "hdfs://smart-master:8020/spark/logs/histoty")
                .set("spark.files", "file:///opt/software/spark/spark/conf/hdfs-site.xml,file:///opt/software/spark/spark/conf/core-site.xml");
        String sparkHome = System.getenv("SPARK_HOME");
        if (sparkHome != null) {
            sparkConf.setSparkHome(sparkHome);
        }

        return sparkConf;
    }


    public SparkConf createSparkConfYarn() {
        SparkConf sparkConf = new SparkConf()
                .setMaster("yarn-client")
                .setAppName("SparkClusterTestYarn")
                .setJars(JavaSparkContext.jarOfClass(SparkClusterTest.class))
                .set("spark.akka.timeout", "1000")
                .set("spark.cores.max", "2")
                .set("spark.default.parallelism", "2")
                .set("spark.executor.memory", "1024m")
                .set("spark.executor.cores", "1")
                .set("spark.yarn.jar", SmartConfiguration.getInstance().get("spark.yarn.jar"))
                .set("spark.speculation.multiplier", "1.0")
                // 添加spark重试次数
                .set("spark.port.maxRetries", "100");
        return sparkConf;
    }

    public void test(String input, boolean yarn) throws InterruptedException {
        LOG.info("[Test] test spark ...");

        JavaSparkContext jsc = null;
        if (yarn) {
            jsc = new JavaSparkContext(createSparkConfYarn());
        } else {
            jsc = new JavaSparkContext(createSparkConf());
        }
        JavaRDD<String> rdd = jsc.textFile(input);
        rdd.persist(StorageLevel.MEMORY_AND_DISK());
        LOG.info("[Test] 读取数据");
        long count = rdd.count();

        LOG.info("[Test] Count : {}", count);
        System.out.println("[Test] Count : " + count);
        Thread.sleep(60 * 1000L);
        LOG.info("[Test] 开始过滤数据");
        JavaRDD filterRDD = rdd.map(new Function<String, Tuple3<String, String, String>>() {
            @Override
            public Tuple3<String, String, String> call(String s) throws Exception {
                String[] vars = s.split("\t");
                String first = vars[0];
                String sencond = "";
                String third = "";
                if (vars.length == 2) {
                    sencond = vars[1];
                }
                if (vars.length == 3) {
                    third = vars[2];
                }

                return new Tuple3<String, String, String>(first, sencond, third);
            }
        }).filter(new Function<Tuple3<String, String, String>, Boolean>() {
            @Override
            public Boolean call(Tuple3<String, String, String> tuple3) throws Exception {
                if (StringUtils.isEmpty(tuple3._2()) || StringUtils.isEmpty(tuple3._3())) {
                    return false;
                }
                return true;
            }
        });
        long fc = filterRDD.count();
        LOG.info("[Test] Filtr Count : {}", fc);
        System.out.println("[Test] Filter Count : " + fc);
    }

    public static void main(String[] args) throws InterruptedException {
        boolean yarn = true;
        if (args != null && args[0].equals("local")) {
            yarn = false;
        }

        new SparkClusterTest().test("hdfs://smart-master:8020/tmp/sma/test", yarn);
    }
}
