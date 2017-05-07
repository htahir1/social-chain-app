package com.partytimeline.core;

import com.partytimeline.user.User;
import com.partytimeline.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements ApplicationRunner {
    private final UserRepository users;

    Logger log = (Logger) LoggerFactory.getLogger(DatabaseLoader.class);

    @Autowired
    public DatabaseLoader(UserRepository users) {
        this.users = users;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            users.save(new User("partytimeline_app", "Party", "Animal", "QUxg3jiQ3132J7T6380650NH6n89YT", new String[] {User.ROLES.ADMIN.toString()}));
        }
        catch (Exception e) {
            log.debug("Users Exception = {}", e.toString());
        }
    }

}