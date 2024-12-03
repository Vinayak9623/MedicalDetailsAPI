package com.medicalInfo.Control;

import java.security.Provider.Service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medicalInfo.defination.MedicalDetailPostInput;
import com.medicalInfo.entity.MedicalDetailsPost;
import com.medicalInfo.entity.MedicalInformation;
import com.medicalInfo.interf.IMedicalDetailsPost;
import com.medicalInfo.service.MedicalDetailsService;

@RestController
public class MedicalDetailsControler {

	@Autowired
	MedicalDetailsService service;

	@PostMapping("/createMedicalRequest")
	public void createMedicalRequest(@RequestBody List<MedicalDetailPostInput> mpost) {
		try {
			service.createAll(mpost);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@PostMapping("/addDetail")
	public void addDetail(@RequestBody MedicalDetailPostInput mpost) {
		
		service.createDetails(mpost);
		
	}
	@GetMapping("/getAllRequest")
	public ResponseEntity<List<MedicalDetailPostInput>> getAllRequest() {

		List<MedicalDetailPostInput> list = service.findAllDetails();

		if (list.size() <= 0) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {

			
			return ResponseEntity.ok(list);

		}

	}
	


	@DeleteMapping("/deleteMedicalDetails/{id}")
	public String deleteById(@PathVariable Long id) {

		return service.deleteMedicalDetails(id);
	}

	@GetMapping("/getValuesOfMedical/{value}")
	public List<IMedicalDetailsPost> getValuesOfMedical(@PathVariable String value){
		
		return service.getDetailsByValue(value);
	}

	@GetMapping("/getDetailByType/{type}")
	public List<IMedicalDetailsPost> getdetail(@PathVariable String type) {


		return service.findByDetails(type);

	}


}
