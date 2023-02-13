package com.divatt.admin.contoller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.HsnEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.services.HsnService;

@RestController
@RequestMapping("/hsn")
public class HsnController {

	@Autowired
	private HsnService hsnService;
	private static final Logger LOGGER = LoggerFactory.getLogger(HsnController.class);

	@PostMapping("/add")
	public GlobalResponse postHsnDetails(@Valid @RequestBody HsnEntity hsnEntity) {
		LOGGER.info("Inside - HsnController.postHsnDetails()");

		try {
			return this.hsnService.postHsnDetails(hsnEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PutMapping("/update/{hsnCode}")
	public GlobalResponse updatetHsnDetailsByHsnCode(@Valid @RequestBody HsnEntity hsnEntity,
			@PathVariable(value = "hsnCode") Integer hsnCode) {
		LOGGER.info("Inside - HsnController.updatetHsnDetailsByHsnCode()");

		try {
			return this.hsnService.updatetHsnDetailsByHsnCode(hsnEntity, hsnCode);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/list")
	public Map<String, Object> getHsnDetails(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "hsnCode") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDelete, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {

		LOGGER.info("Inside - HsnController.getHsnDetailse()");

		try {
			return this.hsnService.getHsnDetails(page, limit, sort, sortName, isDelete, keyword, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/view/{hsnCode}")
	public Optional<HsnEntity> viewByHsnCode(@PathVariable(value = "hsnCode") Integer hsnCode) {
		LOGGER.info("Inside - HsnController.viewByHsnCode()");

		try {
			return this.hsnService.viewByHsnCode(hsnCode);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PutMapping("/deleteHsnCode")
	public GlobalResponse deleteHsnCode(@RequestBody() HsnEntity hsnEntity) {
		LOGGER.info("Inside - HsnController.deleteHsnCode()");

		try {
			if (hsnEntity.getId() != null) {
				return this.hsnService.deleteHsnCode(hsnEntity.getId());
			} else {
				throw new CustomException("Hsn not found");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PutMapping("/muldeleteHsnCode")
	public GlobalResponse muldeleteHsnCode(@RequestBody() List<Integer> hsnId) {
		LOGGER.info("Inside - HsnController.muldeleteHsnCode()");

		try {
			if (!hsnId.equals(null)) {
				return this.hsnService.muldeleteHsnCode(hsnId);
			} else {
				throw new CustomException("Hsn id not found");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PutMapping("/setStatus")
	public GlobalResponse setStatus(@RequestBody() HsnEntity hsnEntity) {
		LOGGER.info("Inside - HsnController.setStatus()");

		try {

			if (hsnEntity.getId() != null) {
				return this.hsnService.setStatus(hsnEntity.getId());
			} else {
				throw new CustomException("Hsn Not Found");
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/getactiveHSNList")
	public List<HsnEntity> getactiveHSNList(@RequestParam(defaultValue = "") String searchKeyword) {
		try {
			return this.hsnService.getActiveHSNListService(searchKeyword);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PostMapping("/upload")
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile uploadFile) {
		LOGGER.info(uploadFile.getContentType());
		LOGGER.info(uploadFile.getName());
		LOGGER.info(uploadFile.getOriginalFilename());

		LOGGER.info("Inside - UploadController.upload()");
		try {
//			byte[] bytes;
			if (uploadFile.isEmpty()) {
				LOGGER.info("File is Empty");
				throw new CustomException("File is Empty");

			} else {
				String originalFilename = uploadFile.getOriginalFilename();
				String substring = originalFilename.substring(uploadFile.getOriginalFilename().lastIndexOf(".") + 1,
						uploadFile.getOriginalFilename().length());
				if (substring.toLowerCase().equals("xls") || (substring.toLowerCase().equals("xlsb"))
						|| substring.toLowerCase().equals("xlsm") || substring.toLowerCase().equals("xlsx")
						|| substring.toLowerCase().equals("ods")) {

				} else {

					throw new CustomException("File Type Should be xls/xlsb/xlsm/xlsx/ods");
				}

			}

			Timestamp currentTimeStamp = new Timestamp(new Date().getTime());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date fetchNueva = format.parse(currentTimeStamp.toString());
			format = new SimpleDateFormat("dd MMM YYY HH:mm");

			LOGGER.info("Time Stamp - " + fetchNueva);

			String uploadRefID = uploadFile.getOriginalFilename() + "_" + format.format(fetchNueva);
			LOGGER.info("Ref ID  - " + uploadRefID);

			boolean flag = hsnService.upload(uploadFile);
			LOGGER.info("return Flag  - " + flag);

			return "Excel Added";
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

}
