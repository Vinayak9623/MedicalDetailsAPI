package com.medicalInfo.interf;

import java.util.List;

import com.medicalInfo.entity.MedicalInformation;

public interface IMedicalDetailsPost {
	
 Long getId();
 
 List<MedicalInformation> getMedicalInformation();
 
}
