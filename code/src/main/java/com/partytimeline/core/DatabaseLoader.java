package com.partytimeline.core;

import com.partytimeline.event.Event;
import com.partytimeline.event.EventRepository;
import com.partytimeline.user.User;
import com.partytimeline.user.UserRepository;
import com.partytimeline.user_session.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
            log.debug("adding default event and default user");
            User user = new User(10206756772397816L, "partytimeline_app@gmail.com", "Party", (new String[]{User.ROLES.ADMIN.toString()}));
            Event event = new Event(648194968710136L, "Event 1", "Description", new Date(), new Date());
            users.save(user);
            events.save(event);
        }
        catch (Exception e) {
            log.debug("Users Exception = {}", e.toString());
        }
    }

}