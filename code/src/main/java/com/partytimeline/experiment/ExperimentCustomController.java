//package com.navvis.experiment;
//
//import com.navvis.category.Category;
//import com.navvis.category.CategoryDTO;
//import com.navvis.category.CategoryRepository;
//import com.navvis.label.Label;
//import com.navvis.sensor.Sensor;
//import com.navvis.sensor.SensorRepository;
//import com.navvis.tag_domain.Value;
//import com.navvis.tag_key.TagDTO;
//import com.navvis.tag_key.Tag;
//import com.navvis.user.User;
//import com.navvis.user.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import java.util.List;
//
//@Controller
//@RequestMapping(value = "/sensor-app/api/v1/experiment")
//public class ExperimentCustomController {
//    private final ExperimentRepository experimentRepository;
//    private final SensorRepository sensorRepository;
//    private final UserRepository userRepository;
//    private final CategoryRepository categoryRepository;
//
//    @Autowired
//    public ExperimentCustomController(ExperimentRepository experimentRepository, SensorRepository sensorRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
//        this.experimentRepository = experimentRepository;
//        this.sensorRepository = sensorRepository;
//        this.userRepository = userRepository;
//        this.categoryRepository = categoryRepository;
//    }
//
//    @RequestMapping(path = "/", method = RequestMethod.POST)
//    public ResponseEntity addExperiment(@RequestBody ExperimentDTO experimentDTO) {
//        if (experimentDTO != null) {
//
//            experimentDTO.toString();
//
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            User user = userRepository.findByUsername(auth.getName());
//
//            Experiment experiment = new Experiment(experimentDTO.getName(), experimentDTO.getDescription(), experimentDTO.getHas_ground_truth_location());
//
//            experiment.setUser(user);
//
//            CategoryDTO experiment_category_dto = experimentDTO.getCategory();
//
//            if (experiment_category_dto != null) {
//                String cat_name = experiment_category_dto.getCategory();
//
//                Category category = categoryRepository.findByCategoryIgnoreCase(cat_name);
//
//                if (category == null) {
//                    category = new Category(cat_name);
//                    for (String l : experiment_category_dto.getLabels()) {
//                        category.addLabel(new Label(l));
//                    }
//                }
//
//                experiment.setCategory(category);
//            }
//
//            List<TagDTO> experiment_tag_dto = experimentDTO.getTags();
//
//            if (experiment_tag_dto != null) {
//                for (TagDTO tag_dto : experiment_tag_dto) {
//                    Tag tag_key = new Tag(tag_dto.getKey());
//                    for (String value : tag_dto.getDomain()) {
//                        tag_key.addValue(new Value(value));
//                    }
//                    experiment.addTag(tag_key);
//                }
//            }
//
//            List<String> experiment_sensors_dto = experimentDTO.getSensors();
//
//            if (experiment_sensors_dto != null) {
//                for (String sensor_dto : experiment_sensors_dto) {
//                    Sensor sensor = sensorRepository.findByNameIgnoreCase(sensor_dto);
//                    if (sensor == null) {
//                        sensor = new Sensor(sensor_dto);
//                    }
//                    experiment.addSensor(sensor);
//                }
//            }
//
//            experimentRepository.save(experiment);
//
//            return ResponseEntity.ok(experiment.getId());
//        } else {
//            return ResponseEntity.unprocessableEntity().build();
//        }
//    }
//}
