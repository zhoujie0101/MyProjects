package com.jay.hadoop.fs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by jay on 16/4/15.
 */
public class ShowFileStatusTest {
//    private MiniDFSCluster cluster;
//    private FileSystem fs;

    @Before
    public void setUp() throws IOException {
//        Configuration conf = new Configuration();
//        if(System.getProperty("test.build.data") == null) {
//            System.setProperty("test.build.data", "/tmp");
//        }
//        cluster = new MiniDFSCluster.Builder(conf).build();
//        fs = cluster.getFileSystem();
//        OutputStream out = fs.create(new Path("/dir/file"));
//        out.write("content".getBytes("UTF-8"));
//        out.close();
    }

    @Test(expected = FileNotFoundException.class)
    public void testThrowsFileNotFoundForNonExistentFile() throws IOException {
//        fs.getFileStatus(new Path("no-such-file"));
    }

    @Test
    public void testStatusOfFile() throws IOException {
//        FileStatus stat = fs.getFileStatus(new Path("/dir/file"));
//        assertThat(stat.getPath().toUri().getPath(), is("/dir/file"));
//        assertThat(stat.isDirectory(), is(false));
//        assertThat(stat.getLen(), is(7L));
//        assertThat(stat.getReplication(), is((short)1));
//        assertThat(stat.getBlockSize(), is(128 * 1024 * 1024L));
//        assertThat(stat.getOwner(), is(System.getProperty("user.name")));
//        assertThat(stat.getGroup(), is("supergroup"));
//        assertThat(stat.getPermission().toString(), is("rw-r--r--"));
    }

    @Test
    public void testFileStatusForDirectory() throws IOException {
//        FileStatus stat = fs.getFileStatus(new Path("/dir"));
//        assertThat(stat.getPath().toUri().getPath(), is("/dir"));
//        assertThat(stat.isDirectory(), is(true));
//        assertThat(stat.getLen(), is(0L));
//        assertThat(stat.getReplication(), is((short)0));
//        assertThat(stat.getBlockSize(), is(0L));
//        assertThat(stat.getOwner(), is(System.getProperty("user.name")));
//        assertThat(stat.getGroup(), is("supergroup"));
//        assertThat(stat.getPermission().toString(), is("rwxr-xr-x"));
    }

    @After
    public void tearDown() throws IOException {
//        if(fs != null) {
//            fs.close();
//        }
//        if(cluster != null) {
//            cluster.shutdown();
//        }
    }
}
