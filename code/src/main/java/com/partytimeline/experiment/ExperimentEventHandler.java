//package com.navvis.experiment;
//
//import com.navvis.user.User;
//import com.navvis.user.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
//import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//@Component
//@RepositoryEventHandler(Experiment.class)
//public class ExperimentEventHandler {
//    private final UserRepository users;
//
//    @Autowired
//    public ExperimentEventHandler(UserRepository users) {
//        this.users = users;
//    }
//
//    @HandleBeforeCreate
//    public void addRecordingBasedOnLoggedInUser(Experiment experiment) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User user = users.findByUsername(username);
//        experiment.setUser(user);
//    }
//}