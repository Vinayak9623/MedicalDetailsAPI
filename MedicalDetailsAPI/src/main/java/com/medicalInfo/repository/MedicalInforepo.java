package com.medicalInfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medicalInfo.entity.MedicalInformation;

@Repository
public interface MedicalInforepo extends JpaRepository<MedicalInformation, Long>{

}
