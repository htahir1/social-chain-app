package com.partytimeline.core;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity {

    public final static String ColumnId = "id";
    public final static String ColumnDateCreated = "date_created";
    public final static String ColumnDateModified = "dateModified";
    public final static String ColumnVersion = "version";

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = ColumnId, nullable = false)
    private final Long id;

    @NotNull
    @Column(name = ColumnDateCreated, nullable = false)
    private Date dateCreated;

    @NotNull
    @Column(name = ColumnDateModified, nullable = false)
    private Date dateModified;

    @Version
    @Column(name = ColumnVersion)
    private Long version;

    protected BaseEntity() {
        id = null;
    }

    public Long getId() {
        return id;
    }

    @PrePersist
    protected void onCreate() {
        Date new_date = new Date();
        this.dateCreated = new_date;
        this.dateModified = new_date;
        this.version = 1L;
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateModified = new Date();
        this.version ++;
    }
}