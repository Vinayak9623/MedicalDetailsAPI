package com.medicalInfo.defination;

import java.util.List;

import com.medicalInfo.entity.Details;
import com.medicalInfo.entity.MedicalInformation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalDetailPostInput {

	private Long id;
	
	private List<MedicalInformationInput> medicalInformationInput;
	
}
