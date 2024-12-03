package com.medicalInfo.defination;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalInformationInput {

	private Long id;
	
    private String desc;
    
    private List<DetailsInput> detailsInput;

}
