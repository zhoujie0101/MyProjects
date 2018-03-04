package com.jay.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * Created by jay on 16/4/20.
 */
public class ExampleClient {
    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
//        HBaseAdmin admin = new HBaseAdmin(conf);
        Connection conn = ConnectionFactory.createConnection(conf);
        Admin admin = conn.getAdmin();
        try {
            TableName tableName = TableName.valueOf("test");
            HTableDescriptor htd = new HTableDescriptor(tableName);
            HColumnDescriptor hcd = new HColumnDescriptor("data");
            htd.addFamily(hcd);
            admin.createTable(htd);

            HTableDescriptor[] tables = admin.listTables();
            if(tables.length != 1 && tableName.equals(tables[0].getTableName())) {
                throw new IOException("Failed to create table!!!");
            }

            Table table = conn.getTable(tableName);
            try {
                for(int i = 1; i <= 3; i++) {
                    byte[] row = Bytes.toBytes("row" + i);
                    Put put = new Put(row);
                    byte[] columnFamily = Bytes.toBytes("data");
                    byte[] qulifier = Bytes.toBytes(i);
                    byte[] value = Bytes.toBytes("value" + i);
                    put.addColumn(columnFamily, qulifier, value);
                    table.put(put);
                }

                Get get = new Get(Bytes.toBytes("row1"));
                Result result = table.get(get);
                System.out.println("Get: " + result);

                Scan scan = new Scan();
                ResultScanner resultScanner = table.getScanner(scan);
                try {
                    for(Result scanResult : resultScanner) {
                        System.out.println("Scan: " + scanResult);
                    }
                } finally {
                    resultScanner.close();
                }
                admin.disableTable(tableName);
                admin.deleteTable(tableName);
            } finally {
                table.close();
            }
        } finally {
            admin.close();
            conn.close();
        }
    }
}
