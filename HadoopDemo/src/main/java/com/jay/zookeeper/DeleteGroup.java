package com.jay.zookeeper;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.List;

/**
 * Created by jay on 16/4/21.
 */
public class DeleteGroup extends ConnectionWatcher {

    public void delete(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        try {
            List<String> childs = zk.getChildren(path, false);
            for(String children : childs) {
                zk.delete(path + "/" + children, -1);
            }
            zk.delete(path, -1);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        DeleteGroup group = new DeleteGroup();
        group.connect(args[0]);
        group.delete(args[1]);
        group.close();
    }
}
