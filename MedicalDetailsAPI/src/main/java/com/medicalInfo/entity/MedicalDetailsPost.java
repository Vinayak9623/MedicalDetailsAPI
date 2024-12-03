package com.medicalInfo.entity;
import java.util.List;

import com.medicalInfo.interf.IMedicalDetailsPost;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MEDICAL_DETAILS")
@NamedQuery(name = "MedicalDetailsPost.findAll", query = "SELECT e FROM  MedicalDetailsPost e")
public class MedicalDetailsPost implements IMedicalDetailsPost {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "medicalDetailsPost_id")
	private Long id;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="medicalPostForeignkey_id")
	private List<MedicalInformation> medicalInformation;

}
