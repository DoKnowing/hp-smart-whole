package hp.smart.whole.core.kafka;

import hp.smart.whole.util.KafkaUtil;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: SMA
 * @Date: 2017-09-24 17:07
 * @Explain:
 */
public class SmartConsumerV1 implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(SmartConsumerV1.class);

    private static ConsumerConnector singletonConsumer = null;

    private static ConsumerConnector getInstance() {
        if (singletonConsumer == null) {
            synchronized (SmartConsumerV1.class) {
                if (singletonConsumer == null) {
                    singletonConsumer = Consumer.createJavaConsumerConnector(KafkaUtil.createKafkaConConfig(null));
                }
            }
        }
        return singletonConsumer;
    }

    /**
     * Test
     *
     * @param topic
     * @throws UnsupportedEncodingException
     */
    public void consumer(String topic) throws UnsupportedEncodingException {
        Map<String, Integer> topicMap = new HashMap<String, Integer>();
        // 目的是创建一个线程池,topic对应线程数
        topicMap.put(topic, 1);
        // topic -- > numberStream List<value>
        Map<String, List<KafkaStream<byte[], byte[]>>> streams = getInstance().createMessageStreams(topicMap);
        List<KafkaStream<byte[], byte[]>> streamList = streams.get(topic);
        LOG.info("[SmartConsumerV1] 消费线程数: {}", streamList.size());
        // 这里要消费多个通道的数据,应该得写多线程了. 这里只取一个通道的
        KafkaStream<byte[], byte[]> stream = streamList.get(0);
        ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
        LOG.info("[SmartConsumerV1] 接收到数据,开始消费 ...");
        int i = 1;
        while (iterator.hasNext() && i > 0) {
            i--;
            System.out.println("[SmartConsumerV1] 重新消费数据 ");
            String message = new String(iterator.next().message(), "utf-8");
            System.out.println("[SmartConsumerV1] 这次有数据吗? : " + message);
        }
    }


    public void createRdd() {
        JavaSparkContext jsc = new JavaSparkContext();
    }
}
