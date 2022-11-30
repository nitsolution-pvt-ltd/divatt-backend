package com.divatt.admin.servicesImpl;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.hsnCode.HsnEntity;
import com.divatt.admin.entity.hsnCode.UploadErrorEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.repo.HsnRepo;
import com.divatt.admin.services.HsnService;
import com.divatt.admin.services.SequenceGenerator;
import com.divatt.category.validation.FieldValidation;

@Service
public class HsnServiceImpl implements HsnService {

	@Autowired
	private HsnRepo hsnRepo;

	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private UploadErrorEntity uploadErrorEntity;

	private static final Logger LOGGER = LoggerFactory.getLogger(HsnServiceImpl.class);

	public GlobalResponse postHsnDetails(@RequestBody HsnEntity hsnEntity) {

		LOGGER.info("Inside - HsnServiceImpl.postHsnDetails()");

		try {

			Optional<HsnEntity> hsnlist = hsnRepo.findByhsnCode(hsnEntity.getHsnCode());

			if (hsnlist.isPresent()) {

				HsnEntity entity = hsnRepo.findByHsn(hsnEntity.getHsnCode());
				if (entity.getIsDelete().equals(true)) {
					throw new CustomException("HsnCode already used");
				} else {
					throw new CustomException("HsnCode already exist");
				}

			} else {
				HsnEntity hsn = new HsnEntity();
				hsn.setId(sequenceGenerator.getNextSequence(HsnEntity.SEQUENCE_NAME));
				hsn.setHsnCode(hsnEntity.getHsnCode());
				hsn.setDescription(hsnEntity.getDescription());
				hsn.setTaxValue(hsnEntity.getTaxValue());
				hsn.setSgst(hsnEntity.getTaxValue() / 2);
				hsn.setCgst(hsnEntity.getTaxValue() / 2);
				hsn.setIgst(hsnEntity.getTaxValue());
				hsn.setCess(hsnEntity.getCess());
				hsn.setEffectiveDate(hsnEntity.getEffectiveDate());
				hsn.setRateRevision(hsnEntity.getRateRevision());
				hsn.setIsActive(true);
				hsn.setIsDelete(false);

				hsnRepo.save(hsn);

				return new GlobalResponse("SUCCESS", "Hsn added succesfully", 200);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public GlobalResponse updatetHsnDetailsByHsnCode(@RequestBody HsnEntity hsnEntity, Integer hsnCode) {

		LOGGER.info("Inside - HsnServiceImpl.updatetHsnDetailsByHsnCode()");

		try {
			Optional<HsnEntity> hsnlist = hsnRepo.findByhsnCode(hsnCode);
			if (hsnlist.isPresent()) {
				HsnEntity entity = hsnRepo.findByHsn(hsnCode);

				if (entity.getIsDelete().equals(false)) {
					entity.setId(entity.getId());

					entity.setHsnCode(hsnEntity.getHsnCode());
					entity.setDescription(hsnEntity.getDescription());
					entity.setTaxValue(hsnEntity.getTaxValue());
					entity.setSgst(hsnEntity.getTaxValue() / 2);
					entity.setCgst(hsnEntity.getTaxValue() / 2);
					entity.setIgst(hsnEntity.getTaxValue());
					entity.setIsActive(true);
					entity.setIsDelete(false);
					entity.setCess(hsnEntity.getCess());
					entity.setEffectiveDate(hsnEntity.getEffectiveDate());
					entity.setRateRevision(hsnEntity.getRateRevision());

					hsnRepo.save(entity);
					return new GlobalResponse("SUCCESS", "Hsn update succesfully", 200);

				} else {
					return new GlobalResponse("Failed", "This hsnCode already delete", 404);
				}

			} else {
				throw new CustomException("HsnCode not exist");
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Map<String, Object> getHsnDetails(int page, int limit, String sort, String sortName, Boolean isDelete,
			String keyword, Optional<String> sortBy) {

		LOGGER.info("Inside - HsnServiceImpl.getHsnDetails()");

		try {
			int CountData = (int) hsnRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit).withSort(Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit).withSort(Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<HsnEntity> findAll = null;

			if (keyword.isEmpty()) {
				findAll = hsnRepo.findByIsDelete(isDelete, "0", pagingSort);
			} else {
				findAll = hsnRepo.Search(keyword, isDelete, "0", pagingSort);

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
				throw new CustomException("Hsn Not Found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Optional<HsnEntity> viewByHsnCode(Integer hsnCode) {
		LOGGER.info("Inside - HsnServiceImpl.viewByHsnCode()");

		try {

			Optional<HsnEntity> hsnlist = hsnRepo.findByhsnCode(hsnCode);

			if (hsnlist.isPresent()) {
				return hsnlist;
			} else {
				throw new CustomException("HsnCode not found");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse deleteHsnCode(Integer id) {

		LOGGER.info("Inside- HsnServiceImpl.deleteHsnCode()");
		try {

			Optional<HsnEntity> hsnlist = hsnRepo.findById(id);
			if (hsnlist.isPresent()) {
				HsnEntity entity = hsnRepo.findById(id).get();
				if (entity.getIsDelete().equals(false)) {
					entity.setIsDelete(true);
					hsnRepo.save(entity);
					return new GlobalResponse("SUCCESS", "Hsn delete succesfully", 200);
				} else {
					return new GlobalResponse("Failed", "Hsn already deleted", 404);
				}
			} else {

				return new GlobalResponse("Failed", "HsnCode not found", 404);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse muldeleteHsnCode(List<Integer> hsnId) {
		try {

			for (Integer id : hsnId) {
				HsnEntity entity = hsnRepo.findById(id).get();
				entity.setIsDelete(true);
				hsnRepo.save(entity);
			}

			return new GlobalResponse("SUCCESS", "hsnCode deleted successfully", 200);
		}

		catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse setStatus(Integer id) {
		LOGGER.info("Inside- HsnServiceImpl.setstatus()");
		try {

			HsnEntity entity = hsnRepo.findById(id).get();

			if (entity.getIsActive().equals(true)) {

				entity.setIsActive(false);
				hsnRepo.save(entity);
				return new GlobalResponse("Success", "Hsn active successfully", 200);
			} else {
				entity.setIsActive(true);
				hsnRepo.save(entity);
				return new GlobalResponse("Success", "Hsn deactive successfully", 200);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public List<HsnEntity> getActiveHSNListService(String searchKeyword) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("isActive").is(true).and("isDelete").is(false));
			List<HsnEntity> hsnList = mongoOperations.find(query, HsnEntity.class);
			if (searchKeyword.isEmpty()) {
				return hsnList;
			} else {
				List<HsnEntity> descriptionFiltered = hsnList.stream()
						.filter(e -> searchKeyword.equals(e.getDescription())).collect(Collectors.toList());
				if (descriptionFiltered.isEmpty()) {
					List<HsnEntity> hsnCodeFiltered = hsnList.stream()
							.filter(e -> searchKeyword.equals(e.getHsnCode() + "")).collect(Collectors.toList());
					if (hsnCodeFiltered.isEmpty()) {
						List<HsnEntity> taxValueFiltered = hsnList.stream()
								.filter(e -> searchKeyword.equals(e.getTaxValue() + "")).collect(Collectors.toList());
						if (taxValueFiltered.isEmpty()) {
							throw new CustomException("No data found");
						}
						return taxValueFiltered;
					}
					return hsnCodeFiltered;
				}
				return descriptionFiltered;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public boolean upload(MultipartFile uploadFile) {
		Map<String, String> errorMap = new HashMap<>();
		HsnEntity hsnEntity = new HsnEntity();
		LOGGER.info("Inside  - UploadServiceImpl.upload()");
		DataFormatter dataFormatter = new DataFormatter();
		String sheetName = "Sheet1";
		boolean returnFlag = true;
		File fileName = new File(uploadFile.getOriginalFilename());
		Sheet sheet = loadTemplate(uploadFile, sheetName);
		int minRow = sheet.getFirstRowNum() + 1;
		int maxRow = sheet.getLastRowNum();
		Row row = sheet.getRow(sheet.getFirstRowNum());
		int minCell = row.getFirstCellNum();
		int maxCell = row.getLastCellNum() - 1;
		String cellValue = null;
		for (int i = minRow; i <= maxRow; i++) {
			Row rows = sheet.getRow(i);
			for (int c = minCell; c <= maxCell; c++) {
				returnFlag = false;
				Cell cells = rows.getCell(c);
				LOGGER.info(cells + "Inside Cells");
				cellValue = dataFormatter.formatCellValue(cells);
				LOGGER.info("Value is ==========  " + cellValue);
				if (cellValue.equals(null) || cellValue.equals("")) {
					returnFlag = true;
					uploadErrorEntity.setRowNumber(i);
					uploadErrorEntity.setCellNumber(c);
					uploadErrorEntity.setErrorDescription("Blank Value");
				}
			}
			hsnEntity.setId(sequenceGenerator.getNextSequence(HsnEntity.SEQUENCE_NAME));
			try {
				hsnEntity.setHsnCode((int) (Float.parseFloat(rows.getCell(0).toString())));
				LOGGER.info("HSN code" + (int) (Float.parseFloat(rows.getCell(0).toString())));
			} catch (Exception e) {
				throw new CustomException(
						"Please Fillup Excel HsnCode Fields on row no  " + i + " and cell no  " + 0 + " this Positon");
			}
			try {
				if (!rows.getCell(1).toString().equals(null) || !rows.getCell(1).toString().equals("")) {
					hsnEntity.setDescription(rows.getCell(1).toString());
					LOGGER.info("description" + rows.getCell(1).toString());
				}
			} catch (Exception e) {
				throw new CustomException("Please Fillup Excel Description Fields on row no  " + i + " and cell no  "
						+ 1 + " this Positon");
			}
			try {
				hsnEntity.setTaxValue((double)Float.parseFloat(rows.getCell(2).toString()));
			} catch (Exception e) {
				throw new CustomException(
						"Please Fillup Excel TaxValue Fields on row no  " + i + " and cell no  " + 2 + " this Positon");
			}
			try {
				hsnEntity.setCess(Float.parseFloat(rows.getCell(3).toString()));
			} catch (Exception e) {
				throw new CustomException(
						"Please Fillup Excel Cess Fields on row no  " + i + " and cell no  " + 3 + " this Positon");
			}
			try {
				Date dateCellValue = rows.getCell(4).getDateCellValue();
				LOGGER.info("DateCellValue is" + dateCellValue);
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dateCellValue);
				Date time = calendar.getTime();
				LOGGER.info("time is" + time);
				String format = dateFormat.format(time);
				hsnEntity.setEffectiveDate(format);
			} catch (Exception e) {
				throw new CustomException("Please Fillup Excel EffectiveDate Fields on row no  " + i + " and cell no  "
						+ 4 + " this Positon");
			}
			try {
				hsnEntity.setRateRevision(rows.getCell(5).toString());
			} catch (Exception e) {
				throw new CustomException("Please Fillup Excel RateVision Fields on row no  " + i + " and cell no  " + 5
						+ " this Positon");
			}
			hsnRepo.save(hsnEntity);

		}
		return returnFlag;

	}

	private Sheet loadTemplate(MultipartFile uploadFile, String sheetName) {
		File fileName = new File(uploadFile.getOriginalFilename());
		XSSFWorkbook workbook = null;
		OPCPackage pkg;
		Sheet sheet = null;
		int count = 0;
		File file = null;
		LOGGER.info("1");
		try {
			if (fileName != null) {
				workbook = new XSSFWorkbook(uploadFile.getInputStream());
				count = workbook.getNumberOfSheets();
				if (count < 0) {
				} else {
					sheet = workbook.getSheet(sheetName);
				}
			}
		} catch (Exception e) {
		}
		return sheet;
	}

}
