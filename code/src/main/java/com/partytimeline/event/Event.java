package com.partytimeline.event;

import com.partytimeline.core.BaseEntity;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Event extends BaseEntity {
    @NotNull
    @Size(min = 2, max = 140)
    private String name;

    @NotNull
    @Size(min = 2, max = 500)
    private String description;

    @NotNull
    private Long start_time;

    @NotNull
    private Long end_time;

    public Event(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
