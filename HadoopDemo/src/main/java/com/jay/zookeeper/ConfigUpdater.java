package com.jay.zookeeper;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by jay on 16/4/21.
 */
public class ConfigUpdater {
    public static final String PATH = "/config";

    private ActiveKeyValueStore store;
    private Random rand = new Random();

    public ConfigUpdater(String hosts) throws IOException, InterruptedException {
        this.store = new ActiveKeyValueStore();
        store.connect(hosts);
    }

    public void run() throws KeeperException, InterruptedException {
        while(true) {
            String value = rand.nextInt(100) + "";
            store.write(PATH, value);
            System.out.printf("Set %s to %s.\n", PATH, value);
            TimeUnit.SECONDS.sleep(rand.nextInt(10));
        }
    }

    public static void main(String[] args) throws Exception {
        ConfigUpdater updater = new ConfigUpdater(args[0]);
        updater.run();
    }
}
