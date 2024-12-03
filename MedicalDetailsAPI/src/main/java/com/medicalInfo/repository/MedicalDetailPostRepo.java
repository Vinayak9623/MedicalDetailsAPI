package com.medicalInfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medicalInfo.entity.MedicalDetailsPost;

@Repository
public interface MedicalDetailPostRepo extends JpaRepository<MedicalDetailsPost, Long>{

	

}