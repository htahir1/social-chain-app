package com.partytimeline.core;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

import static com.partytimeline.core.Constants.DATE_FORMAT_STRING;

@MappedSuperclass
public abstract class BaseEntity {

    public final static String ColumnId = "id";
    public final static String ColumnDateCreated = "date_created";
    public final static String ColumnDateModified = "date_modified";
    public final static String ColumnVersion = "version";

    @Column(name = ColumnDateCreated, nullable = false)
    @JsonFormat(pattern=DATE_FORMAT_STRING)
    private Date date_created;

    @Column(name = ColumnDateModified, nullable = false)
    @JsonFormat(pattern=DATE_FORMAT_STRING)
    private Date date_modified;

    @Version
    @Column(name = ColumnVersion)
    private Long version;

    @PrePersist
    protected void onCreate() {
        Date new_date = new Date();
        this.date_created = new_date;
        this.version = 1L;
    }

    @PreUpdate
    protected void onUpdate() {
        this.date_modified = new Date();
        this.version ++;
    }
}