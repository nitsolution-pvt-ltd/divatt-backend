package com.divatt.admin.contoller;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.admin.constant.MessageConstant;
import com.divatt.admin.entity.AdminModule;
import com.divatt.admin.entity.AdminModules;
import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.LoginEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.repo.AdminModulesRepo;
import com.divatt.admin.services.SequenceGenerator;

@RestController
@RequestMapping("/admin")
public class RoleAndPermission {

	AdminModules adminModules = new AdminModules();

	@Autowired
	private AdminModulesRepo adminModulesRepo;

	@Autowired
	private SequenceGenerator sequenceGenerator;
	
	@Autowired
	private MongoOperations mongoOperations;
	
	
//	@Autowired
//	private RoleAndPermissionService roleAndPermissionService;

	Logger LOGGER = LoggerFactory.getLogger(RoleAndPermission.class);

	@GetMapping("/modules")
	public ResponseEntity<?> getModules(@RequestHeader("Authorization") String token) {
		LOGGER.info("Inside - RoleAndPermission.getModules()");
		try {
//			if(!roleAndPermissionService.checkPermission(token, "module6", "list"))
//				throw new CustomException("Don't have list permission");
			Stream<AdminModules> orElseThrow = Optional
					.of(adminModulesRepo.findAll().stream()
							.filter(e -> e.getMetaKey() != null && e.getMetaKey().equals("admin_modules")))
					.orElseThrow(() -> new CustomException(MessageConstant.NO_DATA.getMessage()));

			orElseThrow.forEach(e -> {
				adminModules = e;
			});

			
			return ResponseEntity.ok(adminModules);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/roles")
	public ResponseEntity<?> getRoles(@RequestHeader("Authorization") String token) {
		LOGGER.info("Inside - RoleAndPermission.getRole()");
		try {
//			if(!roleAndPermissionService.checkPermission(token, "module6", "list"))
//				throw new CustomException("Don't have list permission");
			return ResponseEntity.ok(Optional.of(adminModulesRepo.findByIsDeleted(false).stream().filter(e -> {
				try {
					if (e.getMetaKey().equals("ROLE"))
						return true;
				} catch (Exception o) {

				}
				return false;
			}).map(e -> {
				Map<String, Object> roles = new HashMap<>();
				roles.put("roleKey", e.getId());
				roles.put("roleName", e.getRoleName());
				return roles;
			})).orElseThrow(() -> new CustomException(MessageConstant.NO_DATA.getMessage())));

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/role/{id}")
	public ResponseEntity<?> getRole(@PathVariable("id") Long id,@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - RoleAndPermission.getRole()");
		try {
//			if(!roleAndPermissionService.checkPermission(token, "module6", "list"))
//				throw new CustomException("Don't have list permission");
			Optional<AdminModules> findById = adminModulesRepo.findById(id);
			if (findById.isPresent()) {
				Optional<AdminModules> findByMetaKey = adminModulesRepo.findByMetaKey("admin_modules");
				AdminModules adminModules2 = findById.get();
				ArrayList<AdminModule> modules = adminModules2.getModules();
				for(int i = 0 ; i < modules.size() ; i++) {
					AdminModule adminModule = modules.get(i);
					adminModule.setModDetails(findByMetaKey.get().getAdminModules().get(i));
					modules.set(i, adminModule);
					
				}
				adminModules2.setModules(modules);
				return ResponseEntity.ok(adminModules2);
			}
			else
				throw new CustomException(MessageConstant.NO_DATA.getMessage());
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PostMapping("/role")
	public ResponseEntity<?> addRole(@RequestHeader("Authorization") String token,@RequestBody AdminModules adminModules) {
//		if(!roleAndPermissionService.checkPermission(token, "module6", "create"))
//			throw new CustomException("Don't have add permission");
		List<AdminModules> find = mongoOperations
		.find(query(where("roleName").is(adminModules.getRoleName().toUpperCase())), AdminModules.class);
		if(find.size()>0) {
			throw new CustomException(MessageConstant.ROLE_ALREADY_EXIST.getMessage());
		}
		adminModules.setId((long) sequenceGenerator.getNextSequence(AdminModules.SEQUENCE_NAME));
		adminModules.setMetaKey("ROLE");
		ArrayList<AdminModule> modules = adminModules.getModules();
		for (AdminModule e : modules) {
			HashMap<String, Boolean> modPrivs = e.getModPrivs();
			if (modPrivs.get("create") == null)
				modPrivs.put("create", false);
			if (modPrivs.get("update") == null)
				modPrivs.put("update", false);
			if (modPrivs.get("list") == null)
				modPrivs.put("list", false);
			if (modPrivs.get("delete") == null)
				modPrivs.put("delete", false);
			e.setModPrivs(modPrivs);
		}
		adminModules.setModules(modules);
		adminModules.setRoleName(adminModules.getRoleName().toUpperCase());
		adminModules.setIsDeleted(false);
		adminModulesRepo.save(adminModules);
		return ResponseEntity.ok(new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.ROLE_ADDED.getMessage(), 200));
	}
	
	
	@GetMapping("/list/role")
	public ResponseEntity<?> getListOfRoles(@RequestHeader("Authorization") String token,@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - RoleAndPermission.getListOfRoles()");
		try {
//			if(!roleAndPermissionService.checkPermission(token, "module6", "list"))
//				throw new CustomException("Don't have list permission");
			return this.getRolesForList(page, limit, sort, sortName, isDeleted, keyword, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRoleId(@RequestHeader("Authorization") String token, @PathVariable("id") Long id) {
		LOGGER.info("Inside - RoleAndPermission.deleteRoleId()");
		try {
//			if(!roleAndPermissionService.checkPermission(token, "module6", "delete"))
//				throw new CustomException("Don't have delete permission");
			if (mongoOperations.exists(query(where("id").is(id)), AdminModules.class)) {
				if (mongoOperations.exists(query(where("role").is(id + "")), LoginEntity.class))
					throw new CustomException(MessageConstant.ROLE_GIVEN_TO_ADMIN.getMessage());
				Optional.of(mongoOperations.findAndModify(query(where("id").is(id)),
						new Update().set("isDeleted", true), AdminModules.class))
						.orElseThrow(() -> new RuntimeException(MessageConstant.INTERNAL_SERVER_ERROR.getMessage()));
				return new ResponseEntity<>(new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.DELETED_SUCCESSFULLY.getMessage(), 200), HttpStatus.OK);
			}
			throw new CustomException(MessageConstant.ID_NOT_EXIST.getMessage());

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	
	@PutMapping("/muldelete")
	public GlobalResponse roleMulDelete(@RequestHeader("Authorization") String token,@RequestBody() List<Integer> RolesId) {
		LOGGER.info("Inside - RoleAndPermission.roleMulDelete()");
		try {
//			if(!roleAndPermissionService.checkPermission(token, "module6", "delete"))
//				throw new CustomException("Don't have delete permission");
			if (!RolesId.equals(null)){
				for (Integer obj : RolesId) {

					 Optional<AdminModules> findById = adminModulesRepo.findById(Long.parseLong(obj+""));
					 AdminModules filterCatDetails = findById.get();

					if (filterCatDetails.getId() != null) {
						filterCatDetails.setIsDeleted(true);
						adminModulesRepo.save(filterCatDetails);
					}
				}
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.ROLE_DELETED.getMessage(), 200);
			}else {
				throw new CustomException(MessageConstant.ID_NOT_EXIST.getMessage());
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}	
	}
	
	
	
	
	public ResponseEntity<?> getRolesForList(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy) {
		try {
			int CountData = (int) adminModulesRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<AdminModules> findAll = null;

			if (keyword.isEmpty()) {
				findAll = adminModulesRepo.findByIsDeleted(isDeleted, pagingSort);

			} else {
				findAll = adminModulesRepo.Search(keyword, isDeleted, pagingSort);

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
				return ResponseEntity.ok(response) ;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
