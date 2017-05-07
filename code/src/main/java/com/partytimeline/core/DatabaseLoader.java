package com.partytimeline.core;

import com.partytimeline.event.Event;
import com.partytimeline.event.EventRepository;
import com.partytimeline.user.User;
import com.partytimeline.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DatabaseLoader implements ApplicationRunner {
    private final UserRepository users;
    private final EventRepository events;

    Logger log = (Logger) LoggerFactory.getLogger(DatabaseLoader.class);

    @Autowired
    public DatabaseLoader(UserRepository users, EventRepository events) {
        this.users = users;
        this.events = events;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            User user = new User("partytimeline_app@gmail.com", "Party", "QUxg3jiQ3132J7T6380650NH6n89YT", new String[] {User.ROLES.ADMIN.toString()});
            Event event = new Event("Event 1", "Description", new Date(), new Date());
            event.addUser(user);
            events.save(event);
            users.save(user);
        }
        catch (Exception e) {
            log.debug("Users Exception = {}", e.toString());
        }
    }

}