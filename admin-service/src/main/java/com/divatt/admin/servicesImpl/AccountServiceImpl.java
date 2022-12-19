package com.divatt.admin.servicesImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.divatt.admin.constant.MessageConstant;
import com.divatt.admin.entity.AccountEntity;
import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.repo.AccountRepo;
import com.divatt.admin.repo.AccountTemplateRepo;
import com.divatt.admin.services.AccountService;
import com.divatt.admin.services.SequenceGenerator;

@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private AccountRepo accountRepo;

	@Autowired
	private AccountTemplateRepo accountTemplateRepo;

	@Autowired
	private SequenceGenerator sequenceGenerator;
	
	@Value("${spring.profiles.active}")
	private String contextPath;

	@Value("${host}")
	private String host;
	
	@Value("${interfaceId}")
	private String interfaceId;

	public GlobalResponse postAccountDetails(@RequestBody AccountEntity accountEntity) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - AccountServiceImpl.postAccountDetails()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - AccountServiceImpl.postAccountDetails()");
		}

		try {

			List<AccountEntity> findByRow = accountRepo.findByOrderIdAndInvoiceId(accountEntity.getOrder_details().get(0).getOrder_id(), accountEntity.getOrder_details().get(0).getInvoice_id());

			if (findByRow.size() > 0) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "account/add", MessageConstant.ACCOUNT_NOT_FOUND.getMessage(),
							HttpStatus.BAD_REQUEST);
				}
				throw new CustomException(MessageConstant.ORDER_ALREADY_EXIST.getMessage());
			}
				accountEntity.set_id(sequenceGenerator.getNextSequence(AccountEntity.SEQUENCE_NAME));
				accountRepo.save(accountEntity);

				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "account/add", "Success", HttpStatus.OK);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "account/add", accountEntity.toString(), HttpStatus.OK);
				}
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.ACCOUNT_ADDED.getMessage(), HttpStatus.OK.value());
			

		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}",
						interfaceId, host + contextPath + "account/list", e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
			}
			throw new CustomException(e.getMessage());
		}

	}
	
	public ResponseEntity<?> viewAccountDetails(long accountId) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - AccountServiceImpl.viewAccountDetails()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - AccountServiceImpl.viewAccountDetails()");
		}

		try {
            List<AccountEntity> findByRow = accountRepo.findById(accountId);
			if (findByRow.size() < 0) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "account/view/"+accountId, MessageConstant.ACCOUNT_NOT_FOUND.getMessage(),
							HttpStatus.BAD_REQUEST);
				}
				return new ResponseEntity<>(MessageConstant.ACCOUNT_NOT_FOUND.getMessage(),HttpStatus.BAD_REQUEST);
			}
				
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "account/view/"+accountId, "Success", HttpStatus.OK);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "account/view/"+accountId, findByRow.toString(), HttpStatus.OK);
				}
				return new ResponseEntity<>(findByRow.get(0), HttpStatus.OK);

		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}",
						interfaceId, host + contextPath + "account/view/"+accountId, e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	public GlobalResponse putAccountDetails(long accountId, @RequestBody AccountEntity accountEntity) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - AccountServiceImpl.putAccountDetails()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - AccountServiceImpl.putAccountDetails()");
		}

		try {

			Optional<AccountEntity> findByRow = accountRepo.findByAccountId(accountId);

			if (!findByRow.isPresent()) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "account/update/"+accountId, MessageConstant.ACCOUNT_NOT_FOUND.getMessage(),
							HttpStatus.BAD_REQUEST);
				}
				throw new CustomException(MessageConstant.ACCOUNT_NOT_FOUND.getMessage());
			}

			accountTemplateRepo.update(accountId, accountEntity);

				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "account/update/"+accountId, "Success", HttpStatus.OK);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "account/update/"+accountId, accountEntity.toString(), HttpStatus.OK);
				}
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.ACCOUNT_UPDATED.getMessage(), HttpStatus.OK.value());
			

		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}",
						interfaceId, host + contextPath + "account/update/"+accountId, e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
			}
			throw new CustomException(e.getMessage());
		}

	}

	public Map<String, Object> getAccountDetails(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - AccountServiceImpl.getAccountDetails()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - AccountServiceImpl.getAccountDetails()");
		}
		try {
			Pageable pagingSort = null;

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<AccountEntity> findAll = null;

			if (keyword.isEmpty()) {
				findAll = accountRepo.findAllByOrderByIdDesc(pagingSort);
			} else {
				findAll = accountTemplateRepo.AccountSearchByKeywords(keyword, pagingSort);
//				findAll = accountRepo.AccountSearchByKeywords(keyword, pagingSort);
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
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "account/list", MessageConstant.ACCOUNT_NOT_FOUND.getMessage(),
							HttpStatus.BAD_REQUEST);
				}
				throw new CustomException(MessageConstant.CATEGORY_NOT_FOUND.getMessage());
			} else {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "account/list", "Success", HttpStatus.OK);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "account/list", response.toString(), HttpStatus.OK);
				}
				return response;
			}

		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}",
						interfaceId, host + contextPath + "account/list", e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
			}
			throw new CustomException(e.getMessage());
		}

	}

}
