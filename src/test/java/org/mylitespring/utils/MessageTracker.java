package org.mylitespring.utils;

import java.util.ArrayList;
import java.util.List;

public class MessageTracker {

    private static final List<String> messages = new ArrayList<>();

    public static void addMessage(String message) {
        messages.add(message);
    }

    public static List<String> getMessages() {
        return messages;
    }

    public static void clear() {
        messages.clear();
    }


}
