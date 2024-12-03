package com.medicalInfo.entity;

import java.util.List;
import com.medicalInfo.interf.IMedicalInformation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="MEDICAL_INFORMATION")
@NamedQuery(name = "MedicalInformation.findAll", query = "SELECT e FROM  MedicalInformation e")
public class MedicalInformation implements IMedicalInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="medicalinformation_id")
	private Long id;

	@Column(name="description")
	private String desc;
	

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="medicalInfoForeginkey_id")
	List<Details> details;

}
