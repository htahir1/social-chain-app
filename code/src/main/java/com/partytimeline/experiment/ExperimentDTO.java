//package com.navvis.experiment;
//
//import com.navvis.category.CategoryDTO;
//import com.navvis.tag_key.TagDTO;
//
//import java.util.List;
//
//public class ExperimentDTO {
//    private String name;
//    private String description;
//    private Boolean has_ground_truth_location;
//    private CategoryDTO category;
//    private List<TagDTO> tags;
//    private List<String> sensors;
//
//    public ExperimentDTO() {
//
//    }
//
//    public ExperimentDTO(String name, String description, CategoryDTO category, List<TagDTO> tags, List<String> sensors, boolean has_ground_truth_location) {
//        this.name = name;
//        this.description = description;
//        this.category = category;
//        this.tags = tags;
//        this.sensors = sensors;
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
//    public CategoryDTO getCategory() {
//        return category;
//    }
//
//    public void setCategory(CategoryDTO category) {
//        this.category = category;
//    }
//
//    public List<TagDTO> getTags() {
//        return tags;
//    }
//
//    public void setTags(List<TagDTO> tags) {
//        this.tags = tags;
//    }
//
//    public List<String> getSensors() {
//        return sensors;
//    }
//
//    public void setSensors(List<String> sensors) {
//        this.sensors = sensors;
//    }
//
//    public Boolean getHas_ground_truth_location() {
//        return has_ground_truth_location;
//    }
//
//    public void setHas_ground_truth_location(Boolean has_ground_truth_location) {
//        this.has_ground_truth_location = has_ground_truth_location;
//    }
//
//    @Override
//    public String toString() {
//        String tags_string = "Tags: ";
//        String sensors_string = "Sensors: ";
//        String cat_string = "";
//        String cat_labels_string = "";
//
//        if (category != null && category.getCategory() != null && category.getLabels() != null)
//        {
//            cat_string = category.getCategory();
//            cat_labels_string = category.getLabels().toString();
//        }
//
//        System.out.print(cat_string);
//        System.out.print(cat_labels_string);
//
//
//        if (sensors != null) {
//            for (String sensor : sensors) {
//                if (sensor != null) {
//                    sensors_string += sensor + " - ";
//                }
//            }
//        }
//
//        System.out.print(sensors_string);
//
//        if (tags != null) {
//            for (TagDTO tag : tags) {
//                if (tag.getKey() != null && tag.getDomain() != null) {
//                    tags_string += tag.getKey() + " - Value: " + tag.getDomain().toString();
//                }
//            }
//        }
//
//        System.out.print(tags_string);
//
//        return "Name: " + name + "\n" + "Description : " + description + "\n" + "Category: " + cat_string
//                +  "- Labels: " + cat_labels_string
//                + "\n" + "has GroundTruthLocation: " + has_ground_truth_location
//                + "\n" + tags_string + "\n" + sensors_string;
//    }
//}
