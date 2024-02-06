package com.example.collegescheduler.item;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class AbstractItem implements Comparable<AbstractItem>, EditableItemInterface {

    private final UUID id;
    private String name;
    private String details;
    private LocalDateTime comparableTime;
    private boolean isTodo;

    public AbstractItem(UUID id, String name, String details) {
        this.id = id;
        this.name = name;
        this.details = details;
    }

    public AbstractItem(String name, String details) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.details = details;
    }

    public boolean equals(AbstractItem o) {
        return compareTo(o) == 0;
    }

    public int compareTo(AbstractItem o) {
        // compare this.localDataTime w o.getLocalDateTime()
        return 0;
    }

    public LocalDateTime getLocalDateTime() {
        return comparableTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.comparableTime = localDateTime;
    }
    @Override
    public String toString() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public boolean isTodo() {
        return isTodo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setTodo(boolean todo) {
        isTodo = todo;
    }

    // Used in ItemListAdapter... I'm sorry I think this doesn't go here
    // TODO : extract class
    public static final DiffUtil.ItemCallback<AbstractItem> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<AbstractItem>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull AbstractItem oldItem, @NonNull AbstractItem newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull AbstractItem oldItem, @NonNull AbstractItem newItem) {
                    return oldItem.equals(newItem);
                }
    };
}
