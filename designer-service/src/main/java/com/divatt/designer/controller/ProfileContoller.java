package com.divatt.designer.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.divatt.designer.config.JWTConfig;
import com.divatt.designer.entity.Measurement;
import com.divatt.designer.entity.SendMail;
import com.divatt.designer.entity.profile.DesignerLoginEntity;
import com.divatt.designer.entity.profile.DesignerPersonalInfoEntity;
import com.divatt.designer.entity.profile.DesignerProfile;
import com.divatt.designer.entity.profile.DesignerProfileEntity;
import com.divatt.designer.entity.profile.ProfileImage;
import com.divatt.designer.entity.profile.SocialProfile;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.helper.CustomFunction;
import com.divatt.designer.repo.DatabaseSeqRepo;
import com.divatt.designer.repo.DesignerLoginRepo;
import com.divatt.designer.repo.DesignerPersonalInfoRepo;
import com.divatt.designer.repo.DesignerProfileRepo;
import com.divatt.designer.repo.MeasurementRepo;
import com.divatt.designer.repo.ProductRepo2;
import com.divatt.designer.repo.ProductRepository;
import com.divatt.designer.response.GlobalResponce;
import com.divatt.designer.services.SequenceGenerator;

@RestController
@RequestMapping("/designer")
public class ProfileContoller {

	@Autowired
	private SequenceGenerator sequenceGenarator;

	@Autowired
	private DesignerProfileRepo designerProfileRepo;

	@Autowired
	private DesignerLoginRepo designerLoginRepo;

	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DesignerPersonalInfoRepo designerPersonalInfoRepo;

	@Autowired
	private ProductRepository productRepo;

//	@Autowired
//	private DesignerLogRepo designerLogRepo;

	@Autowired
	private DatabaseSeqRepo databaseSeqRepo;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ProductRepo2 productRepo2;

//	@Autowired
//	private MeasurementMenRepo measurementMenRepo;

	@Autowired
	private MeasurementRepo measurementRepo;

	@Autowired
	private MongoOperations mongoOperations;
	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileContoller.class);

	@Autowired
	private JWTConfig jwtConfig;
	
	@Autowired
	private CustomFunction customFunction;

	@GetMapping("/{id}")
	public ResponseEntity<?> getDesigner(@PathVariable Long id) {
		try {
			Optional<DesignerProfileEntity> findById = designerProfileRepo.findBydesignerId(id);

			if (findById.isPresent())
				;
			DesignerProfileEntity designerProfileEntity = findById.get();
			try {
				if (designerProfileEntity.getSocialProfile() == null)
					designerProfileEntity.setSocialProfile(new SocialProfile());
			} catch (Exception e) {
				designerProfileEntity.setSocialProfile(new SocialProfile());
			}
			try {
				DesignerLoginEntity designerLoginEntity = designerLoginRepo.findById(id).get();
				// LOGGER.info(designerLoginEntity.getDesignerCurrentStatus());
				designerProfileEntity.setAccountStatus(designerLoginEntity.getAccountStatus());
				designerProfileEntity.setProfileStatus(designerLoginEntity.getProfileStatus());
				designerProfileEntity.setIsDeleted(designerLoginEntity.getIsDeleted());
				designerLoginEntity.setDesignerCurrentStatus(designerLoginEntity.getDesignerCurrentStatus());
				designerProfileEntity
						.setDesignerPersonalInfoEntity(designerPersonalInfoRepo.findByDesignerId(id).get());
				designerProfileEntity
						.setProductCount(productRepo2.countByIsDeletedAndAdminStatusAndDesignerIdAndIsActive(false,
								"Approved", id.intValue(), true));
				designerProfileEntity.setDesignerCurrentStatus(designerLoginEntity.getDesignerCurrentStatus());
				org.json.simple.JSONObject countData = countData(id);
				String followerCount = countData.get("FollowersData").toString();
				designerProfileEntity.setFollowerCount(Integer.parseInt(followerCount));

			} catch (Exception e) {

			}
			List<Measurement> findByDesignerId = measurementRepo.findByDesignerId(id.intValue());
			if (findByDesignerId.size() > 0) {
				findByDesignerId.stream().forEach(measurement -> {
					if (measurement.getMeasurementsMen() != null) {
						designerProfileEntity.setMenChartData(measurement);
					} else if (measurement.getMeasurementsWomen() != null) {
						designerProfileEntity.setWomenChartData(measurement);
					}
				});
			} else {
				designerProfileEntity.setWomenChartData(null);
				designerProfileEntity.setMenChartData(null);
			}
			return ResponseEntity.ok(designerProfileEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUserDesigner(@PathVariable Long id) {
		try {
			DesignerLoginEntity designerLoginEntity = new DesignerLoginEntity();
			Optional<DesignerLoginEntity> findById = designerLoginRepo.findById(id);
			LOGGER.info("Dta By id for designer = {}",findById.get());
			if(findById.get().getIsProfileCompleted()==null) {
				findById.get().setIsProfileCompleted(true);
			}
			if (!findById.isPresent())
				throw new CustomException("This designer profile is not completed");
			if (findById.get().getIsProfileCompleted()) {
				designerLoginEntity = findById.get();
				designerLoginEntity.setDesignerProfileEntity(designerProfileRepo
						.findBydesignerId(Long.parseLong(designerLoginEntity.getdId().toString())).get());
				designerLoginEntity.setProductCount(productRepo2.countByIsDeletedAndAdminStatusAndDesignerIdAndIsActive(
						false, "Approved", findById.get().getdId().intValue(), true));
			}
			return ResponseEntity.ok(designerLoginEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PostMapping("/add")
	public ResponseEntity<?> addDesigner(@Valid @RequestBody DesignerProfileEntity designerProfileEntity) {
		try {
			LOGGER.info("TEST" + designerProfileEntity.getBoutiqueProfile().getBoutiqueName());

			Optional<DesignerProfileEntity> findByBoutiqueName = designerProfileRepo
					.findByBoutiqueName(designerProfileEntity.getBoutiqueProfile().getBoutiqueName());
			LOGGER.info("	TEST" + findByBoutiqueName);
			if (!findByBoutiqueName.isPresent()) {

				Optional<DesignerLoginEntity> findByEmail = designerLoginRepo
						.findByEmail(designerProfileEntity.getDesignerProfile().getEmail());

				ResponseEntity<String> forEntity = restTemplate
						.getForEntity("https://localhost:8080/dev/auth/Present/DESIGNER/"
								+ designerProfileEntity.getDesignerProfile().getEmail(), String.class);
				DesignerLoginEntity designerLoginEntity = new DesignerLoginEntity();
				JSONObject jsObj = new JSONObject(forEntity.getBody());
				if ((boolean) jsObj.get("isPresent") && jsObj.get("role").equals("DESIGNER"))
					throw new CustomException("Email already present");
				if ((boolean) jsObj.get("isPresent") && jsObj.get("role").equals("USER")) {
					ResponseEntity<String> forEntity2 = restTemplate
							.getForEntity("https://localhost:8080/dev/auth/info/USER/"
									+ designerProfileEntity.getDesignerProfile().getEmail(), String.class);
					designerLoginEntity.setUserExist(forEntity2.getBody());
				}

				designerLoginEntity.setdId((long) sequenceGenerator.getNextSequence(DesignerLoginEntity.SEQUENCE_NAME));
				designerLoginEntity.setEmail(designerProfileEntity.getDesignerProfile().getEmail());
				designerLoginEntity.setPassword(
						bCryptPasswordEncoder.encode(designerProfileEntity.getDesignerProfile().getPassword()));
				designerLoginEntity.setIsDeleted(false);
				designerLoginEntity.setAccountStatus("INACTIVE");
				designerLoginEntity.setProfileStatus("waitForApprove");
				designerLoginEntity.setIsProfileCompleted(false);
				if (designerLoginRepo.save(designerLoginEntity) != null) {
					designerProfileEntity.setDesignerId(Long.parseLong(designerLoginEntity.getdId().toString()));
					designerProfileEntity
							.setId((long) sequenceGenerator.getNextSequence(DesignerProfileEntity.SEQUENCE_NAME));
					designerProfileEntity.setIsProfileCompleted(false);
					DesignerProfile designerProfile = designerProfileEntity.getDesignerProfile();
					designerProfile.setPassword(
							bCryptPasswordEncoder.encode(designerProfileEntity.getDesignerProfile().getPassword()));
					designerProfileEntity.setDesignerProfile(designerProfile);
					designerProfileRepo.save(designerProfileEntity);
				}

				SendMail mail = new SendMail(designerProfileEntity.getDesignerProfile().getEmail(),
						"Successfully Registration",
						"Welcome " + designerProfileEntity.getDesignerName() + "" + ",\n   "
								+ ",\n                           "
								+ " you have been register successfully. Please active your account by clicking the bellow link "
								+ URI.create("https://65.1.190.195:8083/dev/designer/redirect/" + Base64.getEncoder()
										.encodeToString(designerLoginEntity.getEmail().toString().getBytes()))
								+ " . We will verify your details and come back to you soon.",
						false);

				try {
					ResponseEntity<String> response = restTemplate
							.postForEntity("https://65.1.190.195:8080/dev/auth/sendMail", mail, String.class);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				return ResponseEntity.ok(new GlobalResponce("SUCCESS", "Registered successfully", 200));
			} else {
				throw new CustomException("This Boutique Name allready present!");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PutMapping("/update")
	public ResponseEntity<?> updateDesigner(@RequestBody DesignerLoginEntity designerLoginEntity) {

		Optional<DesignerLoginEntity> findById = designerLoginRepo.findById(designerLoginEntity.getdId());
		if (!findById.isPresent())
			throw new CustomException("Designer details not found");
		else {
			DesignerLoginEntity designerLoginEntityDB = findById.get();
			if (designerLoginEntity.getProfileStatus().equals("REJECTED")) {
				designerLoginEntityDB.setAdminComment(designerLoginEntity.getAdminComment());
			}

//			designerLoginEntityDB.setIsProfileCompleted(designerLoginEntity.getIsProfileCompleted());

			LOGGER.info("Inside Update");
			LOGGER.info("Designer profile status = {}",designerLoginEntity.getProfileStatus());
			LOGGER.info("Designer profile status = {}",designerLoginEntity.getIsProfileCompleted());
			designerProfileRepo.save(customFunction.designerProfileEntity(designerLoginEntity));
			//Old
			designerLoginEntityDB.setProfileStatus(designerLoginEntity.getProfileStatus());
			designerLoginEntityDB.setCategories(designerLoginEntity.getCategories());
			designerLoginEntityDB.setAccountStatus("ACTIVE");
			designerLoginEntityDB.setIsDeleted(designerLoginEntity.getIsDeleted());
			designerLoginEntityDB.setIsProfileCompleted(designerLoginEntity.getIsProfileCompleted());
			designerLoginRepo.save(designerLoginEntityDB);
			LOGGER.info(designerLoginEntityDB + "Inside designerLoginEntityDb");
			
		}
		return ResponseEntity.ok(new GlobalResponce("SUCCESS", "Updated successfully", 200));
	}

	@PutMapping("/profile/update")
	public ResponseEntity<?> updateDesignerProfile(@Valid @RequestBody DesignerProfileEntity designerProfileEntity) {
		try {
			@Valid
			DesignerPersonalInfoEntity designerPersonalInfoEntity = designerProfileEntity
					.getDesignerPersonalInfoEntity();
			Optional<DesignerPersonalInfoEntity> findByDesignerId = designerPersonalInfoRepo
					.findByDesignerId(designerProfileEntity.getDesignerId());
			if (findByDesignerId.isPresent()) {
				designerPersonalInfoEntity.setId(findByDesignerId.get().getId());
			} else {
				designerPersonalInfoEntity
						.setId((long) sequenceGenerator.getNextSequence(DesignerPersonalInfoEntity.SEQUENCE_NAME));
				designerPersonalInfoEntity.setDesignerId(designerProfileEntity.getDesignerId());

			}

			designerPersonalInfoRepo.save(designerPersonalInfoEntity);

			LOGGER.info(designerProfileEntity + "Inside DesignerProfileEntity");
			// start designer measurement
			LOGGER.info("Men Chart data is ={}", designerProfileEntity.getMenChartData());
			LOGGER.info("Women Chart data is ={}", designerProfileEntity.getWomenChartData());

			Measurement menChartData = designerProfileEntity.getMenChartData();
			menChartData.set_id(sequenceGenarator.getNextSequence(Measurement.SEQUENCE_NAME));
			menChartData.setCreatedOn(new Date());
			Measurement womenChartData = designerProfileEntity.getWomenChartData();
			womenChartData.set_id(sequenceGenarator.getNextSequence(Measurement.SEQUENCE_NAME));
			womenChartData.setCreatedOn(new Date());
			measurementRepo.save(menChartData);
			measurementRepo.save(womenChartData);
//			Measurement save = measurementRepo.save(menChartData);
//			
//			LOGGER.info("After save the data in database for Men={}",save);
//			
//			Measurement save2 = measurementRepo.save(womenChartData);
//			LOGGER.info("After save the data in database for Women={}",save2);
			// End designer measurement
		} catch (Exception e) {
			throw new CustomException("Please check the fields");
		}

		Optional<DesignerLoginEntity> findById = designerLoginRepo.findById(designerProfileEntity.getDesignerId());
		if (!findById.isPresent())
			throw new CustomException("Designer details not found");
		else {

			Optional<DesignerProfileEntity> findBydesignerId = designerProfileRepo
					.findBydesignerId(findById.get().getdId());
			if (!findBydesignerId.isPresent())
				throw new CustomException("Designer profile not found");

			DesignerProfile designerProfile = designerProfileEntity.getDesignerProfile();
			designerProfile.setEmail(findById.get().getEmail());
			designerProfile.setPassword(findById.get().getPassword());
			designerProfile.setProfilePic(designerProfileEntity.getDesignerProfile().getProfilePic());

			DesignerProfileEntity designerProfileEntityDB = findBydesignerId.get();

			designerProfileEntityDB.setBoutiqueProfile(designerProfileEntity.getBoutiqueProfile());
			designerProfileEntityDB.setDesignerProfile(designerProfile);
			designerProfileEntityDB.setSocialProfile(designerProfileEntity.getSocialProfile());
//			designerProfileEntityDB.setDesignerLevel(designerProfile.getDesignerCategory());

			designerProfileRepo.save(designerProfileEntityDB);
			DesignerLoginEntity designerLoginEntityDB = findById.get();
			designerLoginEntityDB.setProfileStatus(designerProfileEntity.getProfileStatus());
			designerLoginRepo.save(designerLoginEntityDB);
		}

		return ResponseEntity.ok(new GlobalResponce("SUCCESS", "Updated successfully", 200));
	}

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public Map<String, Object> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted,
			@RequestParam(defaultValue = "") String profileStatus, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {

		try {
			return this.getDesignerProfDetails(page, limit, sort, sortName, isDeleted, keyword, sortBy, profileStatus);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@RequestMapping(value = "/redirect/{email}", method = RequestMethod.GET)
	public void method(HttpServletResponse httpServletResponse, @PathVariable("email") String email) {
		Optional<DesignerLoginEntity> findByEmail = designerLoginRepo
				.findByEmail(new String(Base64.getDecoder().decode(email)));
		LOGGER.info("Get data by mail id = {}",findByEmail);
		if (findByEmail.isPresent()) {
			DesignerLoginEntity designerLoginEntity = findByEmail.get();
			if (designerLoginEntity.getAccountStatus().equals("INACTIVE"))
				designerLoginEntity.setAccountStatus("ACTIVE");
			designerLoginRepo.save(designerLoginEntity);
		}

		httpServletResponse.setHeader("Location", "https://dev.divatt.com/designer/");
		httpServletResponse.setStatus(302);
	}

	@GetMapping("/userDesignerList")
	public ResponseEntity<?> userDesignertList() {
		try {
			long count = databaseSeqRepo.findById(DesignerLoginEntity.SEQUENCE_NAME).get().getSeq();
			Random rd = new Random();
			List<DesignerLoginEntity> designerLoginEntity = new ArrayList<>();
			List<DesignerLoginEntity> findAll = designerLoginRepo.findByIsDeletedAndProfileStatusAndAccountStatus(false,
					"COMPLETED", "ACTIVE");
			List<Integer> lst = new ArrayList<>();
			if (findAll.size() <= 15) {
				designerLoginEntity = findAll;

			} else {
				Boolean flag = true;

				while (flag) {
					int nextInt = rd.nextInt((int) count);
					for (DesignerLoginEntity obj : findAll) {

						if (obj.getdId() == nextInt && !lst.contains(nextInt)) {
							lst.add(nextInt);
							designerLoginEntity.add(obj);

						}
						if (designerLoginEntity.size() > 14)
							flag = false;
					}

				}

			}

			Stream<DesignerLoginEntity> map = designerLoginEntity.stream().map(e -> {
				try {
					e.setProductCount(productRepo.countByIsDeletedAndAdminStatusAndDesignerIdAndIsActive(false,
							"Approved", e.getdId(), true));
					e.setDesignerProfileEntity(
							designerProfileRepo.findBydesignerId(Long.parseLong(e.getdId().toString())).get());
				} catch (Exception o) {

				}

				return e;
			});
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	//listing Logic
	public Map<String, Object> getDesignerProfDetails(int page, int limit, String sort, String sortName,
			Boolean isDeleted, String keyword, Optional<String> sortBy, String profileStatus) {
		try {
			int CountData = (int) designerLoginRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<DesignerLoginEntity> findAll = null;

			if (!profileStatus.isEmpty()) {
				if(profileStatus.equals("changeRequest")) {
					findAll = designerLoginRepo.findByIsDeletedAndIsProfileCompletedAndProfileStatus(isDeleted, true, "SUBMITTED", pagingSort);
				} else {
					findAll = designerLoginRepo.findByIsDeletedAndProfileStatusAndAcountStatus(isDeleted, profileStatus, "ACTIVE", pagingSort);
				}
			} else if (profileStatus.isEmpty() || keyword.isEmpty()) {
				findAll = designerLoginRepo.findDesignerisDeleted(isDeleted, pagingSort);

			} else {
				findAll = designerLoginRepo.SearchByDeletedAndProfileStatus(keyword, isDeleted, profileStatus,
						pagingSort);

			}

			if (findAll.getSize() <= 1)
				throw new CustomException("Designer not found!");

			findAll.map(e -> {
				try {
					e.setDesignerProfileEntity(
							designerProfileRepo.findBydesignerId(Long.parseLong(e.getdId().toString())).get());
				} catch (Exception o) {

				}

				return e;
			});

			int totalPage = findAll.getTotalPages() - 1;
			if (totalPage < 0) {
				totalPage = 0;
			}

			Map<String, Object> response = new HashMap<>();
			response.put("data", findAll.getContent());
			response.put("currentPage", findAll.getNumber());
			response.put("total", findAll.getTotalElements());
			response.put("totalPage", totalPage);
			response.put("perPage", findAll.getSize());
			response.put("perPageElement", findAll.getNumberOfElements());
			response.put("waitingForApproval", designerLoginRepo.findByProfileStatusAndAccountStatus("waitForApprove", "ACTIVE").size());
			response.put("waitingForSubmit", designerLoginRepo.findByProfileStatusAndAccountStatus("APPROVE", "ACTIVE").size());
			response.put("submitted", designerLoginRepo.findByProfileStatusAndAccountStatus("SUBMITTED", "ACTIVE").size());
			response.put("completed", designerLoginRepo.findByProfileStatusAndAccountStatus("COMPLETED", "ACTIVE").size());
			response.put("rejected", designerLoginRepo.findByProfileStatusAndAccountStatus("REJECTED", "ACTIVE").size());
			response.put("deleted", designerLoginRepo.findByDeleted(true).size());
			response.put("changeRequest", designerLoginRepo.findByDeletedAndIsProfileCompletedAndProfileStatus(false, true, "SAVED").size());

			return response;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/countData/{designerId}")
	public org.json.simple.JSONObject countData(@PathVariable Long designerId) {
		try {
			org.json.simple.JSONObject response = new org.json.simple.JSONObject();
			ResponseEntity<GlobalResponce> userData = restTemplate
					.getForEntity("https://localhost:9095/dev/user/followerCount/" + designerId, GlobalResponce.class);
			String followersData = userData.getBody().getMessage();
			response.put("FollowersData", followersData);
			response.put("Products", productRepo2.countByIsDeletedAndAdminStatusAndDesignerIdAndIsActive(false,
					"Approved", designerId.intValue(), true));
			return response;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/getDesignerCategory")
	public List<Object> getDesignerCategory() {
		try {
			List<DesignerLoginEntity> designerProfileList = designerLoginRepo
					.findByIsDeletedAndProfileStatusAndAccountStatus(false, "COMPLETED", "ACTIVE");
			org.json.simple.JSONObject response = new org.json.simple.JSONObject();
			List<Object> designercategories = new ArrayList<Object>();
			for (int i = 0; i < designerProfileList.size(); i++) {
				if (designerProfileList.get(i).getCategories() != null) {
					org.json.simple.JSONObject jsonObject = new org.json.simple.JSONObject();
					jsonObject.put("Name", designerProfileList.get(i).getCategories());
					if (!designercategories.contains(jsonObject)) {
						designercategories.add(jsonObject);
					}
				}

			}
			return designercategories;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/getDesignerDetails/{designerCategories}")
	public List<DesignerLoginEntity> getDesignerDetails(@RequestParam(defaultValue = "") String usermail,
			@PathVariable String designerCategories) {
		try {

			// LOGGER.info(asList+"");
			if (!designerCategories.equals("all")) {
				Query query = new Query();
				query.addCriteria(Criteria.where("categories").is(designerCategories));
				List<DesignerLoginEntity> designerData = mongoOperations.find(query, DesignerLoginEntity.class);
				for (int i = 0; i < designerData.size(); i++) {
					Query query2 = new Query();
					query2.addCriteria(Criteria.where("designerId").is(designerData.get(i).getdId()));
					DesignerProfileEntity designerProfileData = mongoOperations.findOne(query2,
							DesignerProfileEntity.class);
					designerData.get(i).setDesignerProfileEntity(designerProfileData);
					org.json.simple.JSONObject countData = countData(designerData.get(i).getdId());
					String productCount = countData.get("Products").toString();
					String followerCount = countData.get("FollowersData").toString();
					designerData.get(i).setProductCount(Integer.parseInt(productCount));
					designerData.get(i).setFollwerCount(Integer.parseInt(followerCount));
				}
				if (usermail.isBlank()) {
					return designerData;
				} else {
					DesignerProfileEntity[] body = restTemplate
							.getForEntity("https://localhost:8082/dev/user/followedDesigner/" + usermail,
									DesignerProfileEntity[].class)
							.getBody();
					List<DesignerProfileEntity> designerList = Arrays.asList(body);
					for (int a = 0; a < designerData.size(); a++) {
						for (int i = 0; i < designerList.size(); i++) {
							if (designerList.get(i).getDesignerId()
									.equals(designerData.get(a).getDesignerProfileEntity().getDesignerId())) {
								designerData.get(a).setIsFollowing(true);
							}
						}
					}
					return designerData;
				}

			} else {
				List<DesignerLoginEntity> designerData = designerLoginRepo.findAll();
				for (int i = 0; i < designerData.size(); i++) {
					Query query2 = new Query();
					query2.addCriteria(Criteria.where("designerId").is(designerData.get(i).getdId()));
					DesignerProfileEntity designerProfileData = mongoOperations.findOne(query2,
							DesignerProfileEntity.class);
					designerData.get(i).setDesignerProfileEntity(designerProfileData);
					org.json.simple.JSONObject countData = countData(designerData.get(i).getdId());
					
					if(designerData.get(i).getdId() == 264) {
						LOGGER.info("Count data is = {}",countData);
					}
					String productCount = countData.get("Products").toString();
					String followerCount = countData.get("FollowersData").toString();
					designerData.get(i).setProductCount(Integer.parseInt(productCount));
					designerData.get(i).setFollwerCount(Integer.parseInt(followerCount));
				}
				if (usermail.isBlank()) {
					return designerData;
				} else {
					DesignerProfileEntity[] body = restTemplate
							.getForEntity("https://localhost:8082/dev/user/followedDesigner/" + usermail,
									DesignerProfileEntity[].class)
							.getBody();
					List<DesignerProfileEntity> designerList = Arrays.asList(body);
					for (int a = 0; a < designerData.size(); a++) {
						for (int i = 0; i < designerList.size(); i++) {
							if (designerList.get(i).getDesignerId()
									.equals(designerData.get(a).getDesignerProfileEntity().getDesignerId())) {
								designerData.get(a).setIsFollowing(true);
							}
						}
					}
					return designerData;
				}
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/designerIdList")
	public List<DesignerLoginEntity> getDesignerIdList() {
		try {
			return designerLoginRepo.findByIsDeletedAndProfileStatusAndAccountStatus(false, "COMPLETED", "ACTIVE");
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/designerStatusInformation")
	public Map<String, Object> getTotalActiveDesigner() {

		try {
			LOGGER.info("Inside - ProductController.getAllProductDetails()");
			return this.getDesignerInformation();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	private Map<String, Object> getDesignerInformation() {
		try {
			LOGGER.info("Inside - ProductController.getDesignerInformation()");
			Pageable pagingSort = PageRequest.of(0, 10);
			Page<DesignerLoginEntity> findAllCompleted = designerLoginRepo.findDesignerProfileStatus("COMPLETED",
					pagingSort);
			Page<DesignerLoginEntity> findAllApproved = designerLoginRepo.findDesignerProfileStatus("APPROVE",
					pagingSort);
			Page<DesignerLoginEntity> findAllRejected = designerLoginRepo.findDesignerProfileStatus("REJECTED",
					pagingSort);
			Page<DesignerLoginEntity> findAllSubmitted = designerLoginRepo.findDesignerProfileStatus("SUBMITTED",
					pagingSort);
			Page<DesignerLoginEntity> findAllWaitForApprove = designerLoginRepo
					.findDesignerProfileStatus("waitForApprove", pagingSort);
			Page<DesignerLoginEntity> findAllDeleted = designerLoginRepo.findDesignerisDeleted(true, pagingSort);
			Map<String, Object> response = new HashMap<>();
			response.put("Completed", findAllCompleted.getTotalElements());
			response.put("Approve", findAllApproved.getNumberOfElements());
			response.put("Rejected", findAllRejected.getNumberOfElements());
			response.put("Submitted", findAllSubmitted.getNumberOfElements());
			response.put("WaitForApprove", findAllWaitForApprove.getNumberOfElements());
			response.put("Deleted", findAllDeleted.getNumberOfElements());
			return response;

		} catch (Exception e) {

			throw new CustomException(e.getMessage());

		}
	}

	@PutMapping("/designerCurrentStatus/{status}")
	public GlobalResponce changeDesignerStatus(@RequestHeader("Authorization") String token,
			@PathVariable String status) {
		try {
			LOGGER.info(jwtConfig.extractUsername(token.substring(7)));
			LOGGER.info(token.substring(7));
			Optional<DesignerLoginEntity> findByEmail = designerLoginRepo
					.findByEmail(jwtConfig.extractUsername(token.substring(7)));
			DesignerLoginEntity designerEntity = new DesignerLoginEntity();
			if (!findByEmail.isEmpty()) {
				BeanUtils.copyProperties(findByEmail.get(), designerEntity);
				designerEntity.setDesignerCurrentStatus(status);
				designerLoginRepo.save(designerEntity);
				return new GlobalResponce("Success", "Designer current status", 200);
			} else {
				throw new CustomException("User not found");
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@SuppressWarnings("unused")
	@PostMapping("/profilePicUpdate")
	public GlobalResponce imageUpload(@RequestBody ProfileImage profileimage) {
		try {
			Long designerId = profileimage.getDesignerId();
			String image = profileimage.getImage();
			LOGGER.info(image);
			LOGGER.info(designerId.toString());

			if (designerId != null) {
				DesignerProfileEntity findBydesignerId = designerProfileRepo.findBydesignerId(designerId).get();
				LOGGER.info(findBydesignerId + "Inside findBydesignerId");
				DesignerProfileEntity designerProfileEntity = new DesignerProfileEntity();
				DesignerProfile designerProfile = new DesignerProfile();
				designerProfileEntity.setDesignerId(profileimage.getDesignerId());
				designerProfileEntity.setBoutiqueProfile(findBydesignerId.getBoutiqueProfile());
				designerProfileEntity.setDesignerPersonalInfoEntity(findBydesignerId.getDesignerPersonalInfoEntity());
				designerProfileEntity.setSocialProfile(findBydesignerId.getSocialProfile());
				designerProfileEntity.setAccountStatus(findBydesignerId.getAccountStatus());
				designerProfileEntity.setDesignerLevel(findBydesignerId.getDesignerLevel());
				designerProfileEntity.setFollowerCount(findBydesignerId.getFollowerCount());
				designerProfileEntity.setId(findBydesignerId.getId());
				designerProfileEntity.setDesignerName(findBydesignerId.getDesignerName());
				designerProfileEntity.setIsDeleted(findBydesignerId.getIsDeleted());
				designerProfileEntity.setMenChartData(findBydesignerId.getMenChartData());
				designerProfileEntity.setWomenChartData(findBydesignerId.getWomenChartData());
				designerProfileEntity.setProductCount(findBydesignerId.getProductCount());
				designerProfileEntity.setProfileStatus(findBydesignerId.getProfileStatus());
				designerProfileEntity.setDesignerCurrentStatus(findBydesignerId.getDesignerCurrentStatus());
				designerProfileEntity.setIsProfileCompleted(findBydesignerId.getIsProfileCompleted());
				designerProfile.setAltMobileNo(findBydesignerId.getDesignerProfile().getAltMobileNo());
				designerProfile.setCity(findBydesignerId.getDesignerProfile().getCity());
				designerProfile.setCountry(findBydesignerId.getDesignerProfile().getCountry());
				designerProfile.setDesignerCategory(findBydesignerId.getDesignerProfile().getDesignerCategory());
				designerProfile.setDigitalSignature(findBydesignerId.getDesignerProfile().getDigitalSignature());
				designerProfile.setDisplayName(findBydesignerId.getDesignerProfile().getDisplayName());
				designerProfile.setDob(findBydesignerId.getDesignerProfile().getDob());
				designerProfile.setEmail(findBydesignerId.getDesignerProfile().getEmail());
				designerProfile.setFirstName1(findBydesignerId.getDesignerProfile().getFirstName1());
				designerProfile.setLastName1(findBydesignerId.getDesignerProfile().getLastName1());
				designerProfile.setFirstName2(findBydesignerId.getDesignerProfile().getFirstName2());
				designerProfile.setLastName2(findBydesignerId.getDesignerProfile().getLastName2());
				designerProfile.setGender(findBydesignerId.getDesignerProfile().getGender());
				designerProfile.setMobileNo(findBydesignerId.getDesignerProfile().getMobileNo());
				designerProfile.setPassword(findBydesignerId.getDesignerProfile().getPassword());
				designerProfile.setPinCode(findBydesignerId.getDesignerProfile().getPinCode());
				designerProfile.setProfilePic(profileimage.getImage());
				LOGGER.info(profileimage.getImage());
				designerProfile.setState(findBydesignerId.getDesignerProfile().getState());
				designerProfileEntity.setDesignerProfile(designerProfile);

				designerProfileRepo.save(designerProfileEntity);
				LOGGER.info(designerProfileEntity + "inside profileentity");

				return new GlobalResponce("Success", "Profile Image Updated Sucessfully", 200);
			} else {
				throw new CustomException("DesignerId Not Found");
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
