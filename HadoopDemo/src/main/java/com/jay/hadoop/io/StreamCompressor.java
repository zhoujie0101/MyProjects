package com.jay.hadoop.io;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;

/**
 * Created by jay on 16/4/16.
 */
public class StreamCompressor {
    public static void main(String[] args) throws Exception {
        String codecClassName = args[0];
        Configuration conf = new Configuration();
        Class<?> clazz = Class.forName(codecClassName);

        CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(clazz, conf);
        CompressionOutputStream out = codec.createOutputStream(System.out);
        IOUtils.copyBytes(System.in, out, 4096);
        out.finish();
    }
}
