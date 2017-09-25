package hp.smart.whole.core.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
//                .set("spark.master.url", "local")
                .setJars(JavaSparkContext.jarOfClass(SparkClusterTest.class))
                .set("spark.cores.max", "2")
                .set("spark.local.dir", "/tmp/spark")
                .set("spark.executor.memory", "1024m")
                .set("spark.default.parallelism", "2");
        return sparkConf;
    }

    public void test(String input) {
        LOG.info("[Test] test spark ...");

        JavaSparkContext jsc = new JavaSparkContext(createSparkConf());
        JavaRDD rdd = jsc.textFile(input);
        LOG.info("[Test] 读取数据");
        long count = rdd.count();
        LOG.info("[Test] Count : {}", count);
        System.out.println("[Test] Count : " + count);
    }

    public static void main(String[] args) {
        new SparkClusterTest().test("hdfs://smart-master:8020/tmp/sma/test");
    }
}
