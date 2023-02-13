package com.divatt.admin.services;

public interface RoleAndPermissionService {

	public Boolean checkPermission(String token ,String moduleName , String access);
}
