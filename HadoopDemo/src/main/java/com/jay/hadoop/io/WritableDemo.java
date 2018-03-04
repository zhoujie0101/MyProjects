package com.jay.hadoop.io;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by jay on 16/4/17.
 */
public class WritableDemo {
    public static void main(String[] args) throws IOException {
        IntWritable intWritable = new IntWritable();
        intWritable.set(163);

        WritableDemo demo = new WritableDemo();
        byte[] bytes = demo.serialize(intWritable);
        System.out.println(StringUtils.byteToHexString(bytes));

        IntWritable otherIntWritable = new IntWritable();
        demo.deserialize(otherIntWritable, bytes);
        System.out.println(otherIntWritable.get());
    }

    public byte[] serialize(Writable writable) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(out);
        writable.write(dataOut);
        dataOut.close();

        return out.toByteArray();
    }

    public byte[] deserialize(Writable writable, byte[] bytes) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        DataInputStream dataIn = new DataInputStream(in);
        writable.readFields(dataIn);
        dataIn.close();

        return bytes;
    }
}
