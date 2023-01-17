package com.divatt.admin.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.HsnEntity;

public interface HsnService {

	GlobalResponse postHsnDetails(@Valid HsnEntity hsnEntity);

	GlobalResponse updatetHsnDetailsByHsnCode(@Valid HsnEntity hsnEntity, Integer hsnCode);

	Map<String, Object> getHsnDetails(int page, int limit, String sort, String sortName, Boolean isDelete,
			String keyword, Optional<String> sortBy);

	Optional<HsnEntity> viewByHsnCode(Integer hsnCode);

	GlobalResponse deleteHsnCode(Integer id);

	GlobalResponse muldeleteHsnCode(List<Integer> hsnId);

	GlobalResponse setStatus(Integer id);

	List<HsnEntity> getActiveHSNListService(String searchKeyword);

	boolean upload(MultipartFile uploadFile);

}
