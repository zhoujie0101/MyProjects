package com.jay.rabbitmqdemo.topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by jay on 16/6/28.
 */
public class EmitLogTopic {
    private static final String EXCHANGE_NAME = "topic2_logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String severity = getRouting(args);
        String message = getMessage(args);

        channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }

    private static String getRouting(String[] strings){
        if (strings.length < 1)
            return "anonymous.info";
        return strings[0];
    }

    private static String getMessage(String[] args) {
        if (args.length < 2)
            return "Hello World!";
        return joinArgs(args, " ", 1);
    }

    private static String joinArgs(String[] args, String delimiter, int startIndex) {
        int length = args.length;
        if(length == 0) {
            return "";
        }
        if (length < startIndex ) return "";
        StringBuilder words = new StringBuilder(args[0]);
        for(int i = startIndex + 1; i < length; i++) {
            words.append(delimiter).append(args[i]);
        }
        return words.toString();
    }
}
