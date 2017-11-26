package hp.smart.whole.core.hbase;

import com.alibaba.fastjson.JSON;
import hp.smart.whole.util.SmartConfiguration;
import org.apache.commons.collections.CollectionUtils;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author: SMA
 * @date: 2017-10-17 22:09
 * @explain: 使用Table写入数据
 */
public class SmartHbaseWriter {
    protected static final String TABLE_NAME = "SMART-TOTAL-TABLE";
    protected static final byte[] WB_FAMILY = "wb".getBytes();
    protected static final byte[] WX_FAMILY = "wx".getBytes();
    protected static final byte[] NEWS_FAMILY = "news".getBytes();
    protected static final byte[] FORUM_FAMILY = "forum".getBytes();

    protected static final String ROWKET_TYPE = "pk";

    public static final int HBASE_BATCH_SIZE = SmartConfiguration.getInstance()
            .getInt("hbase.writer.batch.size", 1000);

    private String table;
    private static Connection connection;

    private List<Put> buffer = new LinkedList<>();

    public SmartHbaseWriter(String table) {
        this.table = table;
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void init() throws IOException {
        if (connection == null) {
            synchronized (SmartHbaseWriter.class) {
                if (connection == null) {
                    connection = HbaseConnections.get();
                }
            }
        }
    }

    public void writer(String json) throws IOException {
        Map<String, String> map = (Map<String, String>) JSON.parse(json);
        writer(map);
    }

    public void writer(Collection<String> jsonList) throws IOException {
        if (CollectionUtils.isNotEmpty(jsonList)) {
            for (String json : jsonList) {
                Map<String, String> map = (Map<String, String>) JSON.parse(json);
                writer(map);
            }
        }
    }

    public void writer(Map<String, ? extends Object> one) throws IOException {
        String rowkey = one.get(ROWKET_TYPE).toString();
        one.remove(ROWKET_TYPE);
        buffer.add(HbaseUtils.map2Put(one, rowkey, WB_FAMILY));
        if (buffer.size() > HBASE_BATCH_SIZE) {
            flush();
        }
    }

    public void batchWriter(Collection<? extends Map<String, ? extends Object>> collections) throws IOException {
        if (CollectionUtils.isNotEmpty(collections)) {
            for (Map<String, ? extends Object> c : collections) {
                writer(c);
            }
        }
    }

    public int flush() throws IOException {
        List<Put> puts = null;
        synchronized (buffer) {
            puts = new LinkedList<>(buffer);
            buffer.clear();
        }
        Table hti = HbaseConnections.getTable(connection, table);
        try {
            if (CollectionUtils.isNotEmpty(puts)) {
                hti.put(puts);
                return puts.size();
            }
        } finally {
            HbaseConnections.close(hti);
        }
        return 0;
    }

    public void close() throws IOException {
        flush();
        if (connection != null) {
            connection.close();
        }
    }
}
