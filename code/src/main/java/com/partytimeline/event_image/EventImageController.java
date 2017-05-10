package com.partytimeline.event_image;

import com.partytimeline.event.Event;
import com.partytimeline.event.EventRepository;
import com.partytimeline.user.User;
import com.partytimeline.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

@RepositoryRestController
@RequestMapping(value = "/partytimeline/api/v1/event_image")
public class EventImageController {
    private final UserRepository userRepository;
    private final EventImageRepository eventImageRepository;
    private final EventRepository eventRepository;
    private final String path = "images/";
    @Autowired
    public EventImageController(UserRepository userRepository, EventImageRepository eventImageRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventImageRepository = eventImageRepository;
        this.eventRepository = eventRepository;
    }

    @RequestMapping(value="/event_image_metadata", method=RequestMethod.POST)
    public ResponseEntity addEventImageMetadata(@RequestBody EventImageDTO eventImageDTO) {
        if (eventImageDTO != null) {
            EventImage eventImage = new EventImage(eventImageDTO.getCaption(), "", "", eventImageDTO.getDate_taken());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userRepository.findByEmail(auth.getName());
            eventImage.setUser(user);

            if (eventImageDTO.getEvent_id() != null) {
                Event event = eventRepository.findOne(eventImageDTO.getEvent_id());
                if (event != null) {
                    eventImage.setEvent(event);
                }
            }
            eventImageRepository.save(eventImage);

            return ResponseEntity.ok(eventImage.getId());
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @RequestMapping(value="/upload_image/{id}", method=RequestMethod.POST)
    public ResponseEntity addEventImage(@PathVariable("id") Long event_image_id, @RequestParam("event_image_file") MultipartFile file) {
            if (file != null) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                User user = userRepository.findByEmail(auth.getName());
                String username = user.getEmail();
                File new_file = handleFileUpload(file, username, String.valueOf(event_image_id));
                if (new_file != null) {
                    EventImage eventImage = eventImageRepository.findOne(Long.valueOf(event_image_id));
                    eventImage.setPath(new_file.getAbsolutePath());
                    eventImage.setPathSmall(new_file.getPath());
                    eventImageRepository.save(eventImage);
                    return ResponseEntity.ok(eventImage.getId());
                }
            }

        return ResponseEntity.unprocessableEntity().build();
    }

    private String getFileExtension(MultipartFile file) {
        String name = file.getOriginalFilename();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }

    public File handleFileUpload(MultipartFile file, String username, String event_image_id) {
        if (!file.isEmpty()) {
            try {
                File new_file = new File(path + username + "_" + event_image_id + "." + getFileExtension(file));
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new_file));
                stream.write(bytes);
                stream.close();
                return new_file;
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }
}
