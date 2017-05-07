//package com.navvis.experiment;
//
//import com.navvis.category.Category;
//import com.navvis.core.BaseEntity;
//import com.navvis.sensor.Sensor;
//import com.navvis.tag_key.Tag;
//import com.navvis.user.User;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//public class Experiment extends BaseEntity {
//    @NotNull
//    @Size(min = 2, max = 140)
//    private String name;
//
//    private String description;
//
//    @NotNull
//    private Boolean has_ground_truth_location;
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    private User user;
//
//    @ManyToMany(mappedBy = "experiments", cascade = CascadeType.ALL)
//    private Set<Sensor> sensors;
//
//    @OneToMany(mappedBy = "experiment", cascade = CascadeType.ALL)
//    private Set<Tag> tags;
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    private Category category;
//
//    protected Experiment() {
//        super();
//        sensors = new HashSet<>();
//        tags = new HashSet<>();
//    }
//
//    public Experiment(String name, String description, boolean has_ground_truth_location) {
//        this();
//        this.name = name;
//        this.description = description;
//        this.has_ground_truth_location = has_ground_truth_location;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public Boolean hasGroundTruthLocation() {
//        return has_ground_truth_location;
//    }
//
//    public void setHasGroundTruthLocation(Boolean has_ground_truth_location) {
//        this.has_ground_truth_location = has_ground_truth_location;
//    }
//
//
//    public User getUser()
//    {
//        return user;
//    }
//
//    public void setUser(User user)
//    {
//        this.user = user;
//        if (!user.getExperiments().contains(this))
//        {
//            user.addExperiment(this);
//        }
//    }
//
//    public Set<Sensor> getSensors()
//    {
//        return sensors;
//    }
//
//    public void addSensor(Sensor sensor)
//    {
//        sensors.add(sensor);
//        if (!sensor.getExperiments().contains(this)) {
//            sensor.addExperiment(this);
//        }
//    }
//
//    public Set<Tag> getTags()
//    {
//        return tags;
//    }
//
//    public void addTag(Tag tag)
//    {
//        tags.add(tag);
//        if (tag.getExperiment() != this)
//        {
//            tag.setExperiment(this);
//        }
//    }
//
//    public Category getCategory()
//    {
//        return category;
//    }
//
//    public void setCategory(Category category)
//    {
//        this.category = category;
//        if (!category.getExperiments().contains(this))
//        {
//            category.addExperiment(this);
//        }
//    }
//}
