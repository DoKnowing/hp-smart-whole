package hp.smart.whole.core.hbase;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.client.Put;

import java.util.Map;

/**
 * @author: SMA
 * @date: 2017-10-17 22:43
 * @explain:
 */
public class HbaseUtils {

    public static Put map2Put(Map<String, ? extends Object> map, String rowValue, byte[] family) {
        if (MapUtils.isNotEmpty(map) && StringUtils.isNotEmpty(rowValue)) {
            Put put = new Put(rowValue.getBytes());
            for (Map.Entry<String, ? extends Object> entry : map.entrySet()) {
                put.addColumn(family, entry.getKey().getBytes(), String.valueOf(entry.getValue()).getBytes());
            }
            return put;
        }
        return null;
    }

    public static Put json2Put(String json, String rowValue, byte[] family) {
        if (StringUtils.isNotEmpty(json) && StringUtils.isNotEmpty(rowValue)) {
            return map2Put((Map<String, ? extends Object>) JSON.parse(json), rowValue, family);
        }
        return null;
    }
}
