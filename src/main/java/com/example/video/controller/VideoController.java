package com.example.video.controller;

import com.example.video.model.Video;
import com.example.video.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    // CREATE - Show upload form
    @GetMapping("/upload")
    public String uploadForm() {
        return "upload";
    }

    // CREATE - Upload video
    @PostMapping("/upload")
    public String uploadVideo(@RequestParam("file") MultipartFile file, @RequestParam String title,
                              @RequestParam String description, @RequestParam(required = false) Boolean isPrivate) throws IOException {
        boolean privateFlag = (isPrivate != null && isPrivate);
        videoService.save(file, title, description, privateFlag);
        return "redirect:/videos";
    }

    // READ - List all videos
    @GetMapping
    public String listVideos(Model model) {
        model.addAttribute("videos", videoService.findAll());
        return "list";
    }

    // READ - View single video
    @GetMapping("/{id}")
    public String viewVideo(@PathVariable Long id, Model model) {
        model.addAttribute("video", videoService.findById(id));
        return "video";
    }

    // UPDATE - Show edit form
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("video", videoService.findById(id));
        return "update";
    }

    // UPDATE - Update video
    @PostMapping("/{id}/update")
    public String updateVideo(@PathVariable Long id, @RequestParam String title, @RequestParam String description) {
        videoService.updateVideo(id, title, description);
        return "redirect:/videos";
    }

    // DELETE - Delete video
    @GetMapping("/{id}/delete")
    public String deleteVideo(@PathVariable Long id) {
        videoService.deleteById(id);
        return "redirect:/videos";
    }

    // TOGGLE - Toggle privacy
    @PostMapping("/{id}/togglePrivacy")
    public String togglePrivacy(@PathVariable Long id) {
        videoService.togglePrivacy(id);
        return "redirect:/videos";
    }
}
