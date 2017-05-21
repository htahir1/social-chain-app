package com.partytimeline.event_image;

import com.partytimeline.event.Event;
import com.partytimeline.event.EventRepository;
import com.partytimeline.user.User;
import com.partytimeline.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@RepositoryRestController
@RequestMapping(value = "/partytimeline/api/v1/event_image")
public class EventImageController {
    private final UserRepository userRepository;
    private final EventImageRepository eventImageRepository;
    private final EventRepository eventRepository;
    private final String upload_path_original = "images/original_images/";
    private final String upload_path_small = "images/small_images/";

    @Autowired
    public EventImageController(UserRepository userRepository, EventImageRepository eventImageRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventImageRepository = eventImageRepository;
        this.eventRepository = eventRepository;
    }

    @RequestMapping(value="/", method=RequestMethod.POST)
    public ResponseEntity addEventImageMetadata(@RequestBody EventImageDTO eventImageDTO) {
        if (eventImageDTO != null) {
            EventImage eventImage = new EventImage(eventImageDTO.getId(), eventImageDTO.getCaption(), "", "", eventImageDTO.getDate_taken());

            if (eventImageDTO.getEvent_member_id() != null) {
                User user = userRepository.findOne(eventImageDTO.getEvent_member_id());
                if (user != null) {
                    eventImage.setUser(user);
                }
            }

            if (eventImageDTO.getEvent_id() != null) {
                Event event = eventRepository.findOne(eventImageDTO.getEvent_id());
                if (event != null) {
                    eventImage.setEvent(event);
                }
            }
            eventImageRepository.save(eventImage);

            return ResponseEntity.ok(eventImage.getId());
        }
        return ResponseEntity.badRequest().build();
    }


    @RequestMapping(value="/{id}/{event_id}/{event_member_id}/{quality}", method=RequestMethod.POST)
    public ResponseEntity addEventImage(@PathVariable("id") Long event_image_id,
                                        @PathVariable(value="event_id") Long event_id,
                                        @RequestParam(value="event_member_id") Long event_member_id,
                                        @RequestParam(value="quality") String quality,
                                        @RequestParam("event_image_file") MultipartFile file) {
            if (file != null) {
                EventImage eventImage = eventImageRepository.findByUserAndEventAndId(userRepository.findOne(event_member_id),
                        eventRepository.findOne(event_id),
                        event_image_id);

                File new_file = handleFileUpload(file, quality);
                if (new_file != null) {
                    if (quality.equals("small")) {
                        eventImage.setPathSmall(new_file.getPath());
                    }
                    else {
                        eventImage.setPath_original(new_file.getPath());
                    }
                    eventImageRepository.save(eventImage);
                    return ResponseEntity.ok(eventImage.getId());
                }
            }

        return ResponseEntity.badRequest().build();
    }

    private String getFileExtension(MultipartFile file) {
        String name = file.getOriginalFilename();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }

    public File handleFileUpload(MultipartFile file, String quality) {
        if (!file.isEmpty()) {
            String path = upload_path_original;
            try {
                if (quality.equals("small")) {
                    path = upload_path_small;
                }
                File new_file = new File(path + file.getOriginalFilename());
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
