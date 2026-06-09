package com.resume.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resume.entity.Resume;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
   
}
