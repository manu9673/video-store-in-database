package com.example.video.service;

import com.example.video.model.Video;
import com.example.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class VideoService {

    @Value("${upload.path}")
    private String uploadDir;

    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    // CREATE - Save video
    public Video save(MultipartFile file, String title, String description, boolean isPrivate) throws IOException {
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);

        // Create uploads directory if it doesn't exist
        if (!Files.exists(Paths.get(uploadDir))) {
            Files.createDirectories(Paths.get(uploadDir));
        }

        // Check if file already exists
        if (Files.exists(filePath)) {
            // Handle existing file (e.g., append a timestamp or UUID to the file name)
            String newFileName = System.currentTimeMillis() + "_" + fileName;
            filePath = Paths.get(uploadDir + newFileName);
        }

        // Save the file to the uploads folder
        Files.copy(file.getInputStream(), filePath);

        Video video = new Video(title, description, filePath.getFileName().toString(), isPrivate);
        return videoRepository.save(video);
    }

    // READ - Find all videos
    public List<Video> findAll() {
        return videoRepository.findAll();
    }

    // READ - Find video by ID
    public Video findById(Long id) {
        return videoRepository.findById(id).orElse(null);
    }

    // UPDATE - Update video details
    public Video updateVideo(Long id, String title, String description) {
        Video video = videoRepository.findById(id).orElseThrow();
        video.setTitle(title);
        video.setDescription(description);
        return videoRepository.save(video);
    }

    // DELETE - Delete video by ID
    public void deleteById(Long id) {
        videoRepository.deleteById(id);
    }

    // TOGGLE - Toggle video privacy
    public void togglePrivacy(Long id) {
        Video video = videoRepository.findById(id).orElseThrow();
        video.setPrivate(!video.isPrivate());
        videoRepository.save(video);
    }
}
