//package com.navvis.category;
//
//import com.navvis.label.Label;
//import com.navvis.user.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//@Controller
//@RequestMapping(value = "/sensor-app/api/v1/category")
//public class CategoryCustomController {
//    private final CategoryRepository categoryRepository;
//    private final UserRepository userRepository;
//
//    @Autowired
//    public CategoryCustomController(CategoryRepository categoryRepository, UserRepository userRepository) {
//        this.categoryRepository = categoryRepository;
//        this.userRepository = userRepository;
//    }
//
//    @RequestMapping(path = "/add", method = RequestMethod.POST)
//    public ResponseEntity addExperiment(@RequestBody CategoryDTO categoryDTO) {
//        if (categoryDTO != null) {
//            String name = categoryDTO.getCategory();
//            Category category = new Category(name);
//
//            for (String label : categoryDTO.getLabels()) {
//                category.addLabel(new Label(label));
//            }
//
//            categoryRepository.save(category);
//
//            return ResponseEntity.ok(category.getId());
//        } else {
//            return ResponseEntity.unprocessableEntity().build();
//        }
//    }
//}
