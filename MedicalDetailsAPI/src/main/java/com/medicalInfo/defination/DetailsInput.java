package com.medicalInfo.defination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailsInput {
	
	private Long id;
	
	private String type;
	
	private String value;

}
