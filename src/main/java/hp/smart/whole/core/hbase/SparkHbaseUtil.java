package hp.smart.whole.core.hbase;

import hp.smart.whole.util.SmartConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.protobuf.ProtobufUtil;
import org.apache.hadoop.hbase.protobuf.generated.ClientProtos;
import org.apache.hadoop.hbase.util.Base64;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import scala.Tuple2;

import java.io.IOException;

/**
 * @author: SMA
 * @date: 2017-10-18 19:47
 * @explain:
 */
public class SparkHbaseUtil {
    public static void main(String[] args) throws IOException {
        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("hbase test");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        Configuration conf = SmartConfiguration.getInstance();
        conf.set("hbase.rootdir", "hdfs://localhost:9000/hbase");
        //Scan操作
        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes("1"));
        scan.setStopRow(Bytes.toBytes("3"));
        scan.addFamily(Bytes.toBytes("info"));
        scan.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"));

        ClientProtos.Scan proto = ProtobufUtil.toScan(scan);
        String ScanToString = Base64.encodeBytes(proto.toByteArray());

        conf.set(TableInputFormat.SCAN, ScanToString);
        JavaPairRDD<ImmutableBytesWritable, Result> myRDD = sc.newAPIHadoopRDD(conf, TableInputFormat.class,
                ImmutableBytesWritable.class, Result.class);
        //输出数据条数
        System.out.println("count: " + myRDD.count());
        //把读取到的Result转化成String RDD并保存成test文件夹
        JavaRDD<String> result = myRDD.map(new Function<Tuple2<ImmutableBytesWritable, Result>, String>() {
            @Override
            public String call(Tuple2<ImmutableBytesWritable, Result> tuple2) throws Exception {
                return Bytes.toString(tuple2._2().getValue(Bytes.toBytes("info"), Bytes.toBytes("name")));
            }
        });

        result.saveAsTextFile("./test");
    }
}
