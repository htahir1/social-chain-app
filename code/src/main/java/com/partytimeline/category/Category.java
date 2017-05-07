//package com.navvis.category;
//
//import com.navvis.core.BaseEntity;
//import com.navvis.experiment.Experiment;
//import com.navvis.label.Label;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.OneToMany;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//public class Category extends BaseEntity {
//    @NotNull
//    @Size(min = 2, max = 140)
//    private String category;
//
//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
//    private Set<Experiment> experiments;
//
//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
//    private Set<Label> labels;
//
//    protected Category() {
//        super();
//        experiments = new HashSet<>();
//        labels = new HashSet<>();
//    }
//
//    public Category(String category) {
//        this();
//        this.category = category;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public Set<Experiment> getExperiments() {
//        return experiments;
//    }
//
//    public void addExperiment(Experiment experiment) {
//        experiments.add(experiment);
//        if (experiment.getCategory() != this) {
//            experiment.setCategory(this);
//        }
//    }
//
//    public Set<Label> getLabels() {
//        return labels;
//    }
//
//    public void addLabel(Label label) {
//        labels.add(label);
//        if (label.getCategory() != this) {
//            label.setCategory(this);
//        }
//    }
//}
