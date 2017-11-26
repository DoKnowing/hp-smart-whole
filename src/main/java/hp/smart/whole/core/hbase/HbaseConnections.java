package hp.smart.whole.core.hbase;

import hp.smart.whole.util.SmartConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

/**
 * @author: SMA
 * @date: 2017-10-17 22:23
 * @explain:
 */
public class HbaseConnections {

    public static Connection get() throws IOException {
        return ConnectionFactory.createConnection(SmartConfiguration.getInstance());
    }

    public static Table getTable(Connection connection, String table) throws IOException {
        return connection.getTable(TableName.valueOf(table));
    }

    public static void close(Table table) throws IOException {
        if (table != null) {
            table.close();
        }
    }

    public static void close(Connection connection, Table table) throws IOException {
        close(table);
        if (connection != null) {
            connection.close();
        }
    }
}
