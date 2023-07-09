package com.example.chatty.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MessageSorting {

    public static List<Message> getSortedMessagesByTimestamp(List<Message> messages) {
        List<Message> sortedMessages = new ArrayList<>(messages);
        Collections.sort(sortedMessages, new Comparator<Message>() {
            @Override
            public int compare(Message message1, Message message2) {
                long timestamp1 = message1.getTimestamp();
                long timestamp2 = message2.getTimestamp();
                return Long.compare(timestamp1, timestamp2);
            }
        });
        return sortedMessages;
    }

}