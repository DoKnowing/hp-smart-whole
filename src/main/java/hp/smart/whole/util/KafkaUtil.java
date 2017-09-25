package hp.smart.whole.util;

import hp.smart.whole.consts.SmartConsts;
import kafka.consumer.ConsumerConfig;
import kafka.producer.ProducerConfig;

import java.util.Map;
import java.util.Properties;

/**
 * @Author: SMA
 * @Date: 2017-09-24 17:39
 * @Explain:
 */
public class KafkaUtil {

    public static ConsumerConfig createKafkaConConfig(Map<String, String> customizedConf) {
        Properties props = new Properties();
        props.put("group.id", SmartConfiguration.getInstance().get(SmartConsts.GROUP_ID));
        props.put("zookeeper.connect", SmartConfiguration.getInstance().get(SmartConsts.KAFKA_ZOOKEEPER_CONNECT, "4000"));
        props.put("zookeeper.session.timeout.ms", SmartConfiguration.getInstance().get(SmartConsts.KAFKA_ZOOKEEPER_SESSION_TIMEOUT));
        props.put("zookeeper.sync.time.ms", SmartConfiguration.getInstance().get(SmartConsts.KAFKA_ZOOKEEPER_SYNC_TIME, "200"));
        props.put("auto.commit.interval.ms", SmartConfiguration.getInstance().get(SmartConsts.KAFKA_AUTO_COMMIT_INTERVAL, "500"));
        if (customizedConf != null)
            for (Map.Entry<String, String> e : customizedConf.entrySet()) {
                props.put(e.getKey(), e.getValue());
            }
        return new ConsumerConfig(props);
    }

    public static ProducerConfig createKafkaProConfig(Map<String, String> customizedConf) {
        Properties props = new Properties();
        props.put("serializer.class", SmartConfiguration.getInstance().get(SmartConsts.KAFKA_SERIALIZER_CLASS));
        props.put("metadata.broker.list", SmartConfiguration.getInstance().get(SmartConsts.KAFKA_METADATA_BROKER_LIST));
        if (customizedConf != null)
            for (Map.Entry<String, String> e : customizedConf.entrySet()) {
                props.put(e.getKey(), e.getValue());
            }
        return new ProducerConfig(props);
    }

    public static void main(String[] args) {
        System.out.println(createKafkaConConfig(null));
        System.out.println(createKafkaProConfig(null));
    }
}
