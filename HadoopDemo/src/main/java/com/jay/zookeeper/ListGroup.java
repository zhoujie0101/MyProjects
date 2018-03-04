package com.jay.zookeeper;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.List;

/**
 * Created by jay on 16/4/21.
 */
public class ListGroup extends ConnectionWatcher {

    public void list(String groupName) {
        String path = "/" + groupName;
        try {
            List<String> childs = zk.getChildren(path, false);
            if(childs.isEmpty()) {
                System.err.printf("No members in group %s", groupName);
                System.exit(1);
            }
            for(String children : childs) {
                System.out.println(children);
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ListGroup group = new ListGroup();
        group.connect(args[0]);
        group.list(args[1]);
        group.close();
    }
}
