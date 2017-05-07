package com.partytimeline.event;

import com.partytimeline.core.BaseEntity;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Event extends BaseEntity {
    @NotNull
    @Size(min = 2, max = 140)
    private String name;

    @NotNull
    @Size(min = 2, max = 500)
    private String description;

    @NotNull
    private Date start_time;

    @NotNull
    private Date end_time;

    public Event(String name, String description, Date start_time, Date end_time) {
        this.name = name;
        this.description = description;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return start_time;
    }

    public void setStartTime(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEndTime() {
        return end_time;
    }

    public void setEndTime(Date end_time) {
        this.end_time = end_time;
    }
}
