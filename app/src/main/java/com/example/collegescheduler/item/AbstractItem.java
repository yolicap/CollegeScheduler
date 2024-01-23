package com.example.collegescheduler.item;

import java.time.LocalDateTime;

public class AbstractItem implements Comparable<AbstractItem>{

    private final String id;
    private String content;
    private String details;

    private LocalDateTime localDateTime;
    private boolean isTodo;

    public AbstractItem(String id, String content, String details) {
        this.id = id;
        this.content = content;
        this.details = details;
    }

    public int compareTo(AbstractItem o) {
        // compare this.localDataTime w o.getLocalDateTime()
        return 0;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
    @Override
    public String toString() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getDetails() {
        return details;
    }

    public boolean isTodo() {
        return isTodo;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setTodo(boolean todo) {
        isTodo = todo;
    }
}
