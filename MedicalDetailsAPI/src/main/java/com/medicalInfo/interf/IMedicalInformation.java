package com.medicalInfo.interf;

import java.util.List;

import com.medicalInfo.entity.Details;
import com.medicalInfo.entity.MedicalDetailsPost;

public interface IMedicalInformation {
	
	public Long getId();

	//public MedicalDetailsPost getMedicalDetailsPost();

	public List<Details> getDetails();
	
	
	
}
