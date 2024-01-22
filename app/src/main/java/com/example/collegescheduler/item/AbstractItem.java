package com.example.collegescheduler.item;

import java.time.LocalDateTime;

public abstract class AbstractItem implements Comparable<AbstractItem>{
    private LocalDateTime localDateTime;
    private boolean isTodo;

    public boolean isTodo(){
        return this.isTodo;
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
}
