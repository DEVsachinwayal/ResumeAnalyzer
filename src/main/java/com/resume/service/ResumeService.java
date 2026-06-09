package com.resume.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.resume.dto.ResumeResponse;
import com.resume.entity.Resume;
import com.resume.repository.ResumeRepository;

import com.resume.util.ResumeParserUtil;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    private static final Set<String> STOP_WORDS =
            new HashSet<>(Arrays.asList(

                    "the", "a", "an", "and", "or", "for",
                    "with", "from", "that", "this",
                    "are", "is", "was", "were",
                    "have", "has", "had",
                    "will", "shall", "can", "could",
                    "may", "might", "must",
                    "job", "role", "position",
                    "candidate", "candidates",
                    "looking", "experience",
                    "required", "requirements",
                    "skill", "skills",
                    "good", "strong",
                    "knowledge", "ability",
                    "responsible", "responsibilities",
                    "work", "working", "team",
                    "years", "year", "months",
                    "our", "your", "their",
                    "you", "we", "they",
                    "company", "organization",
                    "to", "of", "in", "on",
                    "at", "by", "as", "be",
                    "into", "using", "used",
                    "intern",
                    "oriented",
                    "motivated",
                    "join",
                    "excellent",
                    "opportunity",
                    "hands",
                    "closely",
                    "applications",
                    "mobile",
                    "functionality",
                    "processes",
                    "detail",
                    "quality",
                    "ensure"
            ));

    public ResumeResponse analyzeResume(
            String jobDescription,
            MultipartFile file)
            throws IOException {

        // Validation

        if (file == null || file.isEmpty()) {
            throw new RuntimeException(
                    "Please upload a resume file.");
        }

        if (jobDescription == null
                || jobDescription.trim().isEmpty()) {

            throw new RuntimeException(
                    "Please enter a Job Description.");
        }

        String resumeText =
                ResumeParserUtil.extractText(file)
                        .toLowerCase();
        jobDescription = jobDescription.toLowerCase();

            // Remove special characters

            resumeText = resumeText.replaceAll(
                    "[^a-zA-Z0-9 ]",
                    " ");

            jobDescription = jobDescription.replaceAll(
                    "[^a-zA-Z0-9 ]",
                    " ");

            String[] jdWords =
                    jobDescription.split("\\s+");

            int totalKeywords = 0;
            int matchedSkills = 0;

            StringBuilder missingSkills =
                    new StringBuilder();

            Set<String> processedKeywords =
                    new HashSet<>();

            for (String word : jdWords) {

                word = word.trim();

                if (word.length() < 3) {
                    continue;
                }

                if (STOP_WORDS.contains(word)) {
                    continue;
                }

                if (processedKeywords.contains(word)) {
                    continue;
                }

                processedKeywords.add(word);

                totalKeywords++;

                if (resumeText.contains(word)) {

                    matchedSkills++;

                } else {

                    missingSkills
                            .append(word)
                            .append(", ");
                }
            }

            int score = 0;

            if (totalKeywords > 0) {

                score =
                        (matchedSkills * 100)
                                / totalKeywords;
            }

            String finalMissingSkills =
                    missingSkills.toString();

            if (finalMissingSkills.endsWith(", ")) {

                finalMissingSkills =
                        finalMissingSkills.substring(
                                0,
                                finalMissingSkills.length() - 2
                        );
            }

            String suggestions;

            if (score < 40) {

                suggestions =
                        "Resume needs significant improvement. Add more relevant keywords.";

            } else if (score < 70) {

                suggestions =
                        "Add missing skills from the Job Description.";

            } else if (score < 90) {

                suggestions =
                        "Good Resume. Minor improvements recommended.";

            } else {

                suggestions =
                        "Excellent ATS Match.";
            }

            // Save to Database

            Resume resume = new Resume();
            resume.setFileName(file.getOriginalFilename());

            resume.setScore(score);
            resume.setMatchedSkills(matchedSkills);
            resume.setMissingSkills(finalMissingSkills);
            resume.setSuggestions(suggestions);
            resume.setUploadedAt(LocalDateTime.now());

            resumeRepository.save(resume);

            return new ResumeResponse(
                    score,
                    matchedSkills,
                    finalMissingSkills,
                    suggestions
            );
        }
    }
