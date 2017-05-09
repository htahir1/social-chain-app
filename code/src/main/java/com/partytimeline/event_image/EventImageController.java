package com.partytimeline.event_image;

import com.partytimeline.event.Event;
import com.partytimeline.event.EventRepository;
import com.partytimeline.user.User;
import com.partytimeline.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.util.List;

@Controller
@RequestMapping(value = "/partytimeline/api/v1/event_image")
public class EventImageController {
    private final UserRepository userRepository;
    private final EventImageRepository eventImageRepository;
    private final EventRepository eventRepository;

    @Autowired
    public EventImageController(UserRepository userRepository, EventImageRepository eventImageRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventImageRepository = eventImageRepository;
        this.eventRepository = eventRepository;
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public ResponseEntity addEventImage(@RequestBody EventImageDTO eventImageDTO) {
        if (eventImageDTO != null) {

            eventImageDTO.toString();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userRepository.findByEmail(auth.getName());

            EventImage eventImage = new EventImage(eventImageDTO.getId(), eventImageDTO.getCaption(), "", "", eventImageDTO.getDate_taken());
            eventImage.setUser(user);

            File image_file = eventImageDTO.getImage_file();
            if (image_file != null) {
                eventImage.setPath(image_file.getAbsolutePath());
                eventImage.setPathSmall(image_file.getPath());
            }

            if (eventImageDTO.getEvent_id() != null) {
                Event event = eventRepository.findOne(eventImageDTO.getEvent_id());
                if (event != null) {
                    eventImage.setEvent(event);
                }
            }

            eventImageRepository.save(eventImage);
            return ResponseEntity.ok(eventImage.getId());
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}
