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
            User user = userRepository.findOne(userDTO.getUser_id());
            if (user == null) {// new user
                user = new User(userDTO.getUser_id(), userDTO.getEmail_address(), userDTO.getName(), new String[] {User.ROLES.NORMAL.toString()});
                userRepository.save(user);
            }
            ArrayList<UserSession> userSessions = (ArrayList) userSessionRepository.findByUser(user);
            if (userSessions.size() == 0) {
                UserSession userSession = new UserSession(userDTO.getAccess_token(), userDTO.getExpires_on_date());
                user.addUserSession(userSession);
                userRepository.save(user);
            }
            else {
                // TODO: Handle multiple session instances
            }
            return ResponseEntity.ok(user.getId());
        }
        return ResponseEntity.unprocessableEntity().build();
    }

}
