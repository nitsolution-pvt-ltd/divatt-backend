package com.divatt.admin.contoller;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.validation.Valid;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.cloudfront.model.CustomErrorResponse;
import com.divatt.admin.constant.MessageConstant;
import com.divatt.admin.constant.RestTemplateConstant;
import com.divatt.admin.entity.AdminModule;
import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.LoginEntity;
import com.divatt.admin.entity.SendMail;
import com.divatt.admin.exception.CustomErrorMessage;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.helper.JwtUtil;
import com.divatt.admin.repo.AdminModulesRepo;
import com.divatt.admin.repo.LoginRepository;
import com.divatt.admin.services.S3Service;
import com.divatt.admin.services.SequenceGenerator;

@RestController
@RequestMapping("/admin/profile")

public class ProfileContoller {

	@Autowired
	private S3Service s3Service;

	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Autowired
	private AdminModulesRepo adminModulesRepo;

	@Autowired
	private JwtUtil JwtUtil;

	@Autowired
	private RestTemplate restTemplate;

	Logger LOGGER = LoggerFactory.getLogger(ProfileContoller.class);

	@GetMapping("/test")
	public String test() {
		return "Https is working";
	}

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public Map<String, Object> getAll(@RequestHeader("Authorization") String token,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "DESC") String sort, @RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - ProfileContoller.getAll()");

		try {
			if (!checkPermission(token, "module7", "list"))
				throw new CustomException(MessageConstant.NO_LIST_PERMISSION.getMessage());
			return this.getAdminProfDetails(page, limit, sort, sortName, isDeleted, keyword, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/all")
	public List<LoginEntity> getAllProf(@RequestHeader("Authorization") String token) {
		LOGGER.info("Inside - ProfileContoller.getAllProf()");

		try {
			if (!checkPermission(token, "module7", "list"))
				throw new CustomException(MessageConstant.NO_LIST_PERMISSION.getMessage());
			List<LoginEntity> orElseThrow = Optional
					.of(mongoOperations.find(query(where("is_deleted").is(false)), LoginEntity.class))
					.orElseThrow(() -> new CustomException(MessageConstant.INTERNAL_SERVER_ERROR.getMessage()));
			if (orElseThrow.size() < 1)
				throw new CustomException(MessageConstant.NO_DATA.getMessage());
			return orElseThrow;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/{id}")
	public LoginEntity getProfById(@RequestHeader("Authorization") String token, @PathVariable("id") Long id) {
		LOGGER.info("Inside - ProfileContoller.getProfById()");
		try {
			if (!checkPermission(token, "module7", "list"))
				throw new CustomException(MessageConstant.NO_GET_PERMISSION.getMessage());
			List<LoginEntity> orElseThrow = Optional
					.of(mongoOperations.find(query(where("_id").is(id).andOperator(where("is_deleted").is(false))),
							LoginEntity.class))
					.orElseThrow(() -> new RuntimeException(MessageConstant.INTERNAL_SERVER_ERROR.getMessage()));
			if (orElseThrow.size() < 1)
				throw new CustomException(MessageConstant.NO_DATA.getMessage());
			return orElseThrow.get(0);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PostMapping("/add")
	public ResponseEntity<?> addProfile(@RequestHeader("Authorization") String token,
			@Valid @RequestBody LoginEntity loginEntity, Errors error) {
		LOGGER.info("Inside - ProfileContoller.addProfile()");
		String pass = loginEntity.getPassword();
		try {
			if (error.hasErrors()) {
				throw new CustomException(MessageConstant.CHECK_ALL_FIELDS.getMessage());
			}
			ResponseEntity<String> forEntity = restTemplate.getForEntity(
					RestTemplateConstant.AUTH_PRESENT.getMessage() + loginEntity.getEmail(), String.class);
			JSONObject jsonObject = new JSONObject(forEntity.getBody());
			if ((boolean) jsonObject.get("isPresent"))
				throw new CustomException(MessageConstant.EMAIL_ALREADY_PRESENT.getMessage());
			if (!checkPermission(token, "module7", "create"))
				throw new CustomException(MessageConstant.NO_CREATE_PERMISSION.getMessage());
			Optional<LoginEntity> findByEmail = loginRepository.findByEmail(loginEntity.getEmail());
			if (findByEmail.isPresent()) {
				throw new CustomException(MessageConstant.EMAIL_ALREADY_PRESENT.getMessage());
			}

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
			Date date = new Date();
			formatter.format(date);
			loginEntity.setPassword(passwordEncoder.encode(loginEntity.getPassword()));
			loginEntity.setUid((long) sequenceGenerator.getNextSequence(LoginEntity.SEQUENCE_NAME));
			loginEntity.setRole(loginEntity.getRole());
			loginEntity.setRoleName(adminModulesRepo.findById(loginEntity.getRole()).get().getRoleName().toUpperCase());
			loginEntity.setActive(true);
			loginEntity.setDeleted(false);
			loginEntity.setCreatedOn(date.toString());
			loginEntity.setModifiedOn(date.toString());

			SendMail mail = new SendMail(loginEntity.getEmail(), MessageConstant.REGISTER_SUCCESSFULL.getMessage(),
					MessageConstant.WELCOME.getMessage() + loginEntity.getFirstName() + "" + ",\n   "
							+ MessageConstant.ACCOUNT_CREATED_AND_LOGIN.getMessage() + MessageConstant.NAME.getMessage()
							+ loginEntity.getEmail() + MessageConstant.PASSWORD.getMessage() + pass,
					false);

			try {
				ResponseEntity<String> response = restTemplate
						.postForEntity(RestTemplateConstant.AUTH_SEND_MAIL.getMessage(), mail, String.class);
			} catch (Exception e) {
				throw new CustomException(e.getMessage());
			}

			loginRepository.save(loginEntity);
			return new ResponseEntity<>(new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
					MessageConstant.SUB_ADMIN_ADDED.getMessage(), 200), HttpStatus.OK);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@SuppressWarnings("unlikely-arg-type")
	@PutMapping("/update")
	public ResponseEntity<?> updateProfile(@RequestHeader("Authorization") String token,
			@Valid @RequestBody LoginEntity loginEntity, Errors error) {
		LOGGER.info("Inside - ProfileContoller.updateProfile()");
		try {

			if (error.hasErrors()) {
				throw new CustomException(MessageConstant.CHECK.getMessage());
			}
			if (!checkPermission(token, "module7", "update"))
				throw new CustomException(MessageConstant.NO_UPDATE_PERMISSION.getMessage());
			if (loginEntity.getUid() == null || loginEntity.getUid().equals(""))
				throw new CustomException(MessageConstant.ID_NOT_EXIST.getMessage());
			if (!loginRepository.findByEmail(loginEntity.getEmail()).stream()
					.anyMatch(e -> e.getUid() == loginEntity.getUid()))
				throw new CustomException(MessageConstant.EMAIL_ALREADY_PRESENT.getMessage());
			if (!mongoOperations.exists(query(where("uid").is(loginEntity.getUid())), LoginEntity.class)) {
				throw new CustomException(MessageConstant.ID_NOT_EXIST.getMessage());
			}
//			loginRepository.findByRole(loginEntity.getRole()).ifPresentOrElse((value)->{throw new CustomException("This Role is Already Present");} , ()->{});
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
			Date date = new Date();
			formatter.format(date);
			LoginEntity findById = loginRepository.findById(loginEntity.getUid()).get();
			loginEntity.setUid(findById.getUid());
			loginEntity.setActive(findById.isActive());
			loginEntity.setDeleted(findById.isDeleted());
			loginEntity.setCreatedOn(findById.getCreatedOn());
			loginEntity.setModifiedOn(date.toString());
			loginEntity.setRole(loginEntity.getRole());
			loginEntity.setRoleName(adminModulesRepo.findById(loginEntity.getRole()).get().getRoleName().toUpperCase());
			loginRepository.save(loginEntity);
			return new ResponseEntity<>(new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
					MessageConstant.UPDATED_SUCCESSFULLY.getMessage(), 200), HttpStatus.OK);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProfById(@RequestHeader("Authorization") String token, @PathVariable("id") Long id) {
		LOGGER.info("Inside - ProfileContoller.deleteProfById()");
		try {
			if (!checkPermission(token, "module7", "delete"))
				throw new CustomException(MessageConstant.NO_DELETE_PERMISSION.getMessage());
			if (mongoOperations.exists(query(where("uid").is(id)), LoginEntity.class)) {
				Optional.of(mongoOperations.findAndModify(query(where("uid").is(id)),
						new Update().set("is_deleted", true), LoginEntity.class))
						.orElseThrow(() -> new RuntimeException(MessageConstant.INTERNAL_SERVER_ERROR.getMessage()));
				return new ResponseEntity<>(new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.DELETED_SUCCESSFULLY.getMessage(), 200), HttpStatus.OK);
			}
			throw new CustomException(MessageConstant.ID_NOT_EXIST.getMessage());

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PutMapping("/{id}/{status}")
	public ResponseEntity<?> changeStatusById(@RequestHeader("Authorization") String token, @PathVariable("id") Long id,
			@PathVariable("status") Boolean status) {
		LOGGER.info("Inside - ProfileContoller.changeStatusById()");
		try {
			if (!checkPermission(token, "module7", "update"))
				throw new CustomException(MessageConstant.NO_UPDATE_PERMISSION.getMessage());
			if (mongoOperations.exists(query(where("uid").is(id)), LoginEntity.class)) {
				Optional.of(mongoOperations.findAndModify(query(where("uid").is(id)),
						new Update().set("is_active", status), LoginEntity.class))
						.orElseThrow(() -> new RuntimeException(MessageConstant.INTERNAL_SERVER_ERROR.getMessage()));
				return new ResponseEntity<>(new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.STATUS_CHANGED_SUCCESSFULLY.getMessage(), 200), HttpStatus.OK);
			}
			throw new CustomException(MessageConstant.ID_NOT_EXIST.getMessage());

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PutMapping("/muldelete")
	public GlobalResponse subAdminMulDelete(@RequestHeader("Authorization") String token,
			@RequestBody() List<Integer> CateID) {
		LOGGER.info("Inside - ProfileContoller.subAdminMulDelete()");
		try {
			if (!checkPermission(token, "module7", "delete"))
				throw new CustomException(MessageConstant.NO_DELETE_PERMISSION.getMessage());
			if (!CateID.equals(null)) {
				for (Integer CateIdRowId : CateID) {

					Optional<LoginEntity> findById = loginRepository.findById(CateIdRowId);
					LoginEntity filterCatDetails = findById.get();

					if (filterCatDetails.getUid() != null) {
						filterCatDetails.setDeleted(true);
						filterCatDetails.setCreatedOn(new Date().toLocaleString());
						loginRepository.save(filterCatDetails);
					}
				}
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.SUB_ADMIN_DELETED.getMessage(), 200);
			} else {
				throw new CustomException(MessageConstant.ID_NOT_EXIST.getMessage());
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Map<String, Object> getAdminProfDetails(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy) {
		try {
			int CountData = (int) loginRepository.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<LoginEntity> findAll = null;

			if (keyword.isEmpty()) {
				findAll = loginRepository.findByIsDeleted(isDeleted, pagingSort);

			} else {
				findAll = loginRepository.Search(keyword, isDeleted, pagingSort);

			}

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

			if (findAll.getSize() <= 1) {
				throw new CustomException(MessageConstant.NO_DATA.getMessage());
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	Boolean checkPermission(String token, String moduleName, String access) {
		System.out.println(token);
		String extractUsername = JwtUtil.extractUsername(token.substring(7));
		Long role = loginRepository.findByEmail(extractUsername).get().getRole();
		ArrayList<AdminModule> modules = adminModulesRepo.findById(role).get().getModules();
		Boolean haveAccess = false;
		for (AdminModule obj : modules) {
			if (obj.getModName().equals(moduleName)) {
				haveAccess = obj.getModPrivs().get(access);
			}
		}
//		if(!haveAccess)
//			throw new CustomException("Don't have access on this module");

		return haveAccess;
	}

	@GetMapping("/s3/getFiles")
	public ResponseEntity<?> getFiles() {
		return ResponseEntity.ok(s3Service.listFiles());
	}

	@GetMapping("/s3/getBuckets")
	public ResponseEntity<?> getBuckets() {
		return ResponseEntity.ok(s3Service.getAllBuckets());
	}

	@PostMapping("/s3/upload")
	public ResponseEntity<?> uploadFiles(@RequestPart(value = "file", required = false) MultipartFile file)
			throws IOException{
		if(file.getSize()>10681340)
			throw new CustomException("File size not excepted....");
		return ResponseEntity.ok(s3Service.uploadFile(file.getOriginalFilename(), file.getBytes()));
		
	}

	@GetMapping("/testResize")
	public String testResize() throws IOException {
		File input = new File("/home/soumen/Downloads/soumen.jpg");
		BufferedImage image = ImageIO.read(input);

		BufferedImage resized = resize(image, 500, 500);

		File output = new File("/home/soumen/Downloads/soumen_500_500.jpg");
		ImageIO.write(resized, "png", output);
		return "Success";
	}

	private static BufferedImage resize(BufferedImage img, int height, int width) {
		Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return resized;
	}

}
