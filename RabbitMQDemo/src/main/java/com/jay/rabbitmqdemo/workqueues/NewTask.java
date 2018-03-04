package com.jay.rabbitmqdemo.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * Created by jay on 16/6/28.
 */
public class NewTask {
    private static final String TASK_QUEUE_NAME = "task_queue_test";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

        String message = getMessage(args);

        channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        System.out.println(" [x] EmitLogTopic '" + message + "'");

        channel.close();
        connection.close();
    }

    private static String getMessage(String[] args) {
        if(args.length < 1) {
            return "Hello World!";
        }
        return joinArgs(args, " ");
    }

    private static String joinArgs(String[] args, String delimiter) {
        int length = args.length;
        if(length == 0) {
            return "";
        }
        StringBuilder words = new StringBuilder(args[0]);
        for(int i = 1; i < length; i++) {
            words.append(delimiter).append(args[i]);
        }
        return words.toString();
    }
}
