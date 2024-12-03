package com.medicalInfo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.hibernate.grammars.hql.HqlParser.IsEmptyPredicateContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.medicalInfo.defination.DetailsInput;
import com.medicalInfo.defination.MedicalDetailPostInput;
import com.medicalInfo.defination.MedicalInformationInput;
import com.medicalInfo.entity.Details;
import com.medicalInfo.entity.MedicalDetailsPost;
import com.medicalInfo.entity.MedicalInformation;
import com.medicalInfo.entity.MedicalInformation.MedicalInformationBuilder;
import com.medicalInfo.interf.IMedicalDetailsPost;
import com.medicalInfo.repository.DetailsRepo;
import com.medicalInfo.repository.MedicalDetailPostRepo;
import com.medicalInfo.repository.MedicalInforepo;

@Service
public class MedicalDetailsService {

	private MedicalDetailPostRepo mediRepository;

	private DetailsRepo detailRepo;

	private MedicalInforepo medicalInfoRepo;

	@Autowired
	public MedicalDetailsService(MedicalDetailPostRepo mediRepository, DetailsRepo detailRepo,
			MedicalInforepo medicalInfoRepo) {
		super();
		this.mediRepository = mediRepository;
		this.detailRepo = detailRepo;
		this.medicalInfoRepo = medicalInfoRepo;
	}

	public void create(MedicalDetailPostInput mpost) {

		MedicalDetailsPost medicalPost;
		if (Objects.nonNull(mpost.getId())) {

			medicalPost = mediRepository.findById(mpost.getId()).get();

			medicalPost.setMedicalInformation(buildMedicalInformationForUpdate(mpost, medicalPost));

		}

		else {

			medicalPost = MedicalDetailsPost.builder().medicalInformation(buildMedicalInformation(mpost)).build();

		}

		mediRepository.saveAndFlush(medicalPost);
	}

	private List<MedicalInformation> buildMedicalInformation(MedicalDetailPostInput mpost) {
		return mpost.getMedicalInformationInput().stream().map(minfo -> MedicalInformation.builder()
				.desc(minfo.getDesc()).details(buildMedicalDetailsLst(minfo.getDetailsInput())).build())
				.collect(Collectors.toList());
	}

	private List<Details> buildMedicalDetailsLst(List<DetailsInput> detailsLst) {

		Map<Long, Details> existingDetailsMap = detailRepo.findAll().stream()
				.collect(Collectors.toMap(Details::getId, detail -> detail));

		return detailsLst.stream().map(detail -> {
			Details existingDetail = existingDetailsMap.get(detail.getId());

			if (Objects.nonNull(existingDetail)) {

				existingDetail.setType(detail.getType());
				existingDetail.setValue(detail.getValue());

				return existingDetail;

			}

			else {

				return Details.builder().type(detail.getType()).value(detail.getValue()).build();

			}

		}).collect(Collectors.toList());

	}

	private List<MedicalInformation> buildMedicalInformationForUpdate(MedicalDetailPostInput mpost,
			MedicalDetailsPost existingPost) {

		Map<Long, MedicalInformation> existingInfoMap = existingPost.getMedicalInformation().stream()
				.collect(Collectors.toMap(MedicalInformation::getId, mi -> mi));

		return mpost.getMedicalInformationInput().stream().map(medicalInput -> {

			MedicalInformation mInfo = existingInfoMap.get(medicalInput.getId());

			if (Objects.nonNull(mInfo)) {

				mInfo.setDesc(medicalInput.getDesc());
				mInfo.setDetails(buildMedicalDetailsLst(medicalInput.getDetailsInput()));

				return mInfo;
			}

			else {

				throw new RuntimeException(medicalInput.getId() + ": Can not be updated");
			}
		}).collect(Collectors.toList());
	}

	public void createDetails(MedicalDetailPostInput mpost) {

		MedicalInformation medicalInfo = medicalInfoRepo.findById(mpost.getId()).get();

		List<Details> detailsList = mpost.getMedicalInformationInput().stream()
				.flatMap(info -> info.getDetailsInput().stream())
				.map(detail -> Details.builder().type(detail.getType()).value(detail.getValue()).build())
				.collect(Collectors.toList());

		medicalInfo.getDetails().addAll(detailsList);

		medicalInfoRepo.saveAndFlush(medicalInfo);

	}

	public List<MedicalDetailPostInput> findAllDetails() {
		List<MedicalDetailsPost> medicalDetailsPost = mediRepository.findAll();
		return medicalDetailsPost.stream().map(post -> MedicalDetailPostInput.builder().id(post.getId())
				.medicalInformationInput(post.getMedicalInformation().stream()
						.map(info -> MedicalInformationInput.builder().id(info.getId()).desc(info.getDesc())
								.detailsInput(info.getDetails().stream()
										.map(detail -> DetailsInput.builder().id(detail.getId()).type(detail.getType())
												.value(detail.getValue()).build())
										.collect(Collectors.toList()))
								.build())
						.collect(Collectors.toList()))
				.build()).collect(Collectors.toList());
	}

	public String deleteMedicalDetails(Long id) {

		mediRepository.deleteById(id);
		return "Deleted" + id;
	}

	public List<IMedicalDetailsPost> findByDetails(String type) {

		List<MedicalDetailsPost> medicalInfo = mediRepository.findAll();

		return medicalInfo.stream().map(medicalPost -> {

			List<MedicalInformation> filteredMedicalInfo = medicalPost.getMedicalInformation().stream()
					.map(medicalInfos -> {

						List<Details> filteredDetails = medicalInfos.getDetails().stream()
								.filter(detail -> type.equals(detail.getType())).collect(Collectors.toList());

						if (!filteredDetails.isEmpty()) {
							MedicalInformation filteredMedicalInfoItem = new MedicalInformation();
							filteredMedicalInfoItem.setId(medicalInfos.getId());
							filteredMedicalInfoItem.setDesc(medicalInfos.getDesc());
							filteredMedicalInfoItem.setDetails(filteredDetails);
							return filteredMedicalInfoItem;
						} else {
							return null;
						}
					}).filter(medicalInfos -> medicalInfos != null) // Remove null entries
					.collect(Collectors.toList());

			if (!filteredMedicalInfo.isEmpty()) {
				MedicalDetailsPost filteredMedicalPost = new MedicalDetailsPost();
				filteredMedicalPost.setId(medicalPost.getId());
				filteredMedicalPost.setMedicalInformation(filteredMedicalInfo);
				return filteredMedicalPost;
			} else {
				return null;
			}
		}).filter(medicalPost -> medicalPost != null).collect(Collectors.toList());
	}

	public List<IMedicalDetailsPost> getDetailsByValue(String value) {

		List<MedicalDetailsPost> medicalPosts = mediRepository.findAll();

		return medicalPosts.stream().map(medicalInfo -> {

			List<MedicalInformation> filterMedicalInformation = medicalInfo.getMedicalInformation().stream()
					.map(details -> {

						List<Details> filterDetails = details.getDetails().stream()
								.filter(detail -> value.equals(detail.getValue())).collect(Collectors.toList());

						if (!filterDetails.isEmpty()) {

							MedicalInformation medicalInformation = new MedicalInformation();

							medicalInformation.setId(details.getId());
							medicalInformation.setDesc(details.getDesc());
							medicalInformation.setDetails(filterDetails);

							return medicalInformation;
						}

					else {

							return null;
						}

					}).filter(details -> details != null).collect(Collectors.toList());

			if (!filterMedicalInformation.isEmpty()) {

				MedicalDetailsPost medicalDetailPost = new MedicalDetailsPost();

				medicalDetailPost.setId(medicalInfo.getId());

				medicalDetailPost.setMedicalInformation(filterMedicalInformation);

				return medicalDetailPost;
			}

			else {

				return null;
			}
		}).filter(medicalInfo -> medicalInfo != null).collect(Collectors.toList());

	}

	public void createAll(List<MedicalDetailPostInput> medicalDetailPostInputs) {

		for (MedicalDetailPostInput mpost : medicalDetailPostInputs) {

			create(mpost);
		}

	}

}
