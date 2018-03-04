package com.jay.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by jay on 16/4/20.
 */
public class HBaseStationQuery extends Configured implements Tool {
    static final byte[] INFO_COLUMNFAMILY = Bytes.toBytes("info");
    static final byte[] NAME_QUALIFIER = Bytes.toBytes("name");
    static final byte[] LOCATION_QUALIFIER = Bytes.toBytes("location");
    static final byte[] DESCRIPTION_QUALIFIER = Bytes.toBytes("description");

    public Map<String, String> getStationInfo(Table table, String stationId) throws IOException {
        Get get = new Get(Bytes.toBytes(stationId));
        Result result = table.get(get);
        if(result == null) {
            return null;
        }
        Map<String, String> resultMap = new LinkedHashMap<>();
        resultMap.put("name", getValue(result, INFO_COLUMNFAMILY, NAME_QUALIFIER));
        resultMap.put("location", getValue(result, INFO_COLUMNFAMILY, LOCATION_QUALIFIER));
        resultMap.put("description", getValue(result, INFO_COLUMNFAMILY, DESCRIPTION_QUALIFIER));

        return resultMap;
    }

    private String getValue(Result result, byte[] family, byte[] qualifier) {
        byte[] bytes = result.getValue(family, qualifier);
        return bytes == null ? "" : Bytes.toString(bytes);
    }

    @Override
    public int run(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: HBaseStationQuery <station_id>");
            return -1;
        }

        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf("stations"));
        try {
            Map<String, String> stationInfo = getStationInfo(table, args[0]);
            if(stationInfo == null) {
                System.err.printf("Station ID %s not found.\n", args[0]);
                return -1;
            }
            for (Map.Entry<String, String> station : stationInfo.entrySet()) {
                System.out.printf("%s\t%s\n", station.getKey(), station.getValue());
            }
            return 0;
        } finally {
            table.close();
        }
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(HBaseConfiguration.create(), new HBaseStationQuery(), args);
        System.exit(exitCode);
    }
}
