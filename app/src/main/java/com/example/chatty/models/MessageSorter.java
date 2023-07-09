package com.example.chatty.models;

import android.os.Build;

import java.util.Comparator;
import java.util.List;
import java.util.Collections;

public class MessageSorter {
    public static List<Message> sortMessagesByTimestamp(List<Message> messages) {
        Collections.sort(messages, new Comparator<Message>() {
            @Override
            public int compare(Message m1, Message m2) {
                return Long.compare(m1.getTimestamp(), m2.getTimestamp());
            }
        });
        return messages;
    }
}