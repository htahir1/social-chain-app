package com.partytimeline.user;

import com.partytimeline.user_session.UserSession;
import com.partytimeline.user_session.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

@RepositoryRestController
@RequestMapping(value = "/partytimeline/api/v1/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserSessionRepository userSessionRepository;
    private final String path = "images/";
    @Autowired
    public UserController(UserRepository userRepository, UserSessionRepository userSessionRepository) {
        this.userRepository = userRepository;
        this.userSessionRepository = userSessionRepository;
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public ResponseEntity login(@RequestBody UserDTO userDTO) {
        if (userDTO != null) {
            User user = userRepository.findOne(userDTO.getId());
            if (user == null) {// new user
                user = new User(user.getId(), user.getEmail(), user.getName(), "", new String[] {User.ROLES.NORMAL.toString()});
            }
            ArrayList<UserSession> userSessions = (ArrayList) userSessionRepository.findByUser(user);
            if (userSessions.size() == 0) {
                UserSession userSession = new UserSession(userDTO.getAccess_token(), user, userDTO.getExpires_on_date());
                user.addUserSession(userSession);
            }
            return ResponseEntity.ok(user.getId());
        }
        return ResponseEntity.unprocessableEntity().build();
    }

}
