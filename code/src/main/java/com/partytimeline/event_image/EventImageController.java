package com.partytimeline.event_image;

import com.partytimeline.event.Event;
import com.partytimeline.event.EventRepository;
import com.partytimeline.helper.S3Wrapper;
import com.partytimeline.helper.Sha1Hex;
import com.partytimeline.user.User;
import com.partytimeline.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@RepositoryRestController
@RequestMapping(value = "/partytimeline/api/v1/event_image")
public class EventImageController {
    private final UserRepository userRepository;
    private final EventImageRepository eventImageRepository;
    private final EventRepository eventRepository;
    private final String upload_path_original = "event_images/original/";
    private final String upload_path_small = "event_images/small/";
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private S3Wrapper s3Wrapper;


    @Autowired
    public EventImageController(UserRepository userRepository, EventImageRepository eventImageRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventImageRepository = eventImageRepository;
        this.eventRepository = eventRepository;
    }

    @RequestMapping(value="", method=RequestMethod.POST)
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

            log.info("addEventImageMetadata succeeded with event id: {}", eventImageDTO.getEvent_id());

            return ResponseEntity.ok(eventImage.getId());
        }
        log.info("addEventImageMetadata failed with event id: {}", eventImageDTO.getEvent_id());
        return ResponseEntity.badRequest().build();
    }


    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public ResponseEntity addEventImage(@RequestParam("id") Long event_image_id,
                                        @RequestParam(value="event_id") Long event_id,
                                        @RequestParam(value="event_member_id") Long event_member_id,
                                        @RequestParam(value="quality") String quality,
                                        @RequestParam("event_image_file") MultipartFile file) {
            if (file != null) {
                User user = userRepository.findOne(event_member_id);
                Event event = eventRepository.findOne(event_id);

                EventImage eventImage = eventImageRepository.findOne(event_image_id);
                eventImage.setOriginal_name(file.getOriginalFilename());

                String path = upload_path_original;
                if (quality.equals("small")) {
                    path = upload_path_small;
                }

                path = path + generateUniqueImagePathHash(user.getName() + event.getName() + quality);
                String key = path + "/" + file.getOriginalFilename();

                s3Wrapper.upload(new MultipartFile[]{file}, key);

                if (quality.equals("small")) {
                    eventImage.setPath_small(s3Wrapper.getResourceURL(key));
                }
                else {
                    eventImage.setPath_original(s3Wrapper.getResourceURL(key));
                }

                log.info("addEventImage succeeded for event_image_id: {} with image path: {}", event_image_id, path);
                eventImageRepository.save(eventImage);
                return ResponseEntity.ok(eventImage.getId());
            }

            log.info("addEventImage failed with event_image_id: {}", event_image_id);
        return ResponseEntity.badRequest().build();
    }

    private String generateUniqueImagePathHash(String input) {
        String hash = null;
        try {
            hash = new Sha1Hex().makeSHA1Hash(input);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return hash;
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

                Boolean mkdirs = new_file.mkdirs();
                log.info("handleFileUpload mkdirs: {}", mkdirs);

                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new_file));
                stream.write(bytes);
                stream.close();
                log.info("handleFileUpload file created successfully after upload");
                return new_file;
            } catch (Exception e) {
                log.info("handleFileUpload file creation failed with exception: {}", e.toString());
                return null;
            }
        } else {
            log.info("handleFileUpload file creation failed");
            return null;
        }
    }
}
