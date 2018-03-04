package com.jay.hadoop.fs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * Created by jay on 16/4/15.
 */
public class FileCopyWithProgress {

    public static void main(String[] args) throws Exception {
        String localStr = args[0];
        String dest = args[1];

        InputStream in = new BufferedInputStream(new FileInputStream(localStr));

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(dest), conf);
        OutputStream out = fs.create(new Path(dest), new Progressable() {
            @Override
            public void progress() {
                System.out.print(".");
            }
        });
        IOUtils.copyBytes(in, out , 4096, true);
    }
}
