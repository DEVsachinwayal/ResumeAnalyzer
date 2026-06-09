package com.resume.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import com.resume.entity.Resume;
import com.resume.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.resume.dto.ResumeResponse;
import com.resume.service.ResumeService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/resume")
@CrossOrigin("*")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private ResumeRepository resumeRepository;
    
    @GetMapping("/all")
    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }
    
    @GetMapping("/test")
    public String test() {
        return "Backend Working";
    }
    @DeleteMapping("/delete/{id}")
    public String deleteResume(@PathVariable Long id) {

        resumeRepository.deleteById(id);

        return "Deleted Successfully";
    }
    

    @PostMapping("/upload")
    public ResumeResponse uploadResume(
            @RequestParam("jobDescription")
            String jobDescription,

            @RequestParam("file")
            MultipartFile file)

            throws IOException {

        return resumeService
                .analyzeResume(jobDescription,
                        file);
    }
}