package org.example.smartinventory.model;

import lombok.Data;

@Data
public class Notification
{
    private String title;
    private String content;
    private String subject;
    private String sender;
    private int priority;
    private boolean isRead;

    public Notification(String title, String content, String subject, String sender, int priority, boolean isRead) {
        this.title = title;
        this.content = content;
        this.subject = subject;
        this.sender = sender;
        this.priority = priority;
        this.isRead = isRead;
    }

    public Notification() {
    }
}
