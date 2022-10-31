package com.divatt.admin.servicesImpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divatt.admin.entity.AdminModule;
import com.divatt.admin.helper.JwtUtil;
import com.divatt.admin.repo.AdminModulesRepo;
import com.divatt.admin.repo.LoginRepository;
import com.divatt.admin.services.RoleAndPermissionService;


@Service
public class RoleAndPermissionServiceImpl implements RoleAndPermissionService{

	
	@Autowired
	private JwtUtil JwtUtil;
	
	@Autowired
	private AdminModulesRepo adminModulesRepo;
	
	@Autowired
	private LoginRepository loginRepository;
	
	public Boolean checkPermission(String token ,String moduleName , String access) {
		
		String extractUsername = JwtUtil.extractUsername(token.substring(7));
		Long role = loginRepository.findByEmail(extractUsername).get().getRole();
		ArrayList<AdminModule> modules = adminModulesRepo.findById(role).get().getModules();
		Boolean haveAccess = false;
		for(AdminModule obj : modules) {
			if(obj.getModName().equals(moduleName)) {
				haveAccess = obj.getModPrivs().get(access);
			}
		}
//		if(!haveAccess)
//			throw new CustomException("Don't have access on this module");
		
		return haveAccess;
	}

}
