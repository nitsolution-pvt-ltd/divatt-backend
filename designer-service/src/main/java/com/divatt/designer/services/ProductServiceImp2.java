package com.divatt.designer.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.divatt.designer.entity.product.ProductMasterEntity2;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.helper.CustomFunction;
import com.divatt.designer.repo.ProductRepo2;
import com.divatt.designer.response.GlobalResponce;

@Service
public class ProductServiceImp2 implements ProductService2 {
    @Autowired
    private ProductRepo2 productRepo2;

    @Autowired
    private CustomFunction customFunction;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImp2.class);

    @Override
    public GlobalResponce addProductData(ProductMasterEntity2 productMasterEntity2) {
        try {
            LOGGER.info("Inside-ProductServiceImp2.addProductMasterData()");
            productRepo2.save(customFunction.addProductMasterData(productMasterEntity2));
            return new GlobalResponce("Success", "Product added successfully", 200);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public GlobalResponce updateProduct(ProductMasterEntity2 productMasterEntity2, Integer productId) {
        try {
            LOGGER.info("Inside-ProductServiceImp2.updateProduct()");
            if (productRepo2.existsById(productId)) {

                LOGGER.info("inside if");
                productRepo2.save(customFunction.updateProductData(productMasterEntity2, productId));

                return new GlobalResponce("Success", "Product updated successfully", 200);
            } else {
                throw new CustomException("Product Not Found");
            }
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }

    }

    @Override
    public Map<String, Object> getAllProduct(int page, int limit, String sort, String sortName, Boolean isDeleted,
            String keyword, Optional<String> sortBy) {
        try {
            int Count = (int) productRepo2.count();
            Pageable pageable = null;
            if (limit == 0) {
                limit = Count;
            }

            if (sort.equals("ASC")) {
                pageable = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
            } else {
                pageable = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
            }

            Page<ProductMasterEntity2> findall = null;

            if (keyword.isEmpty()) {
                findall = productRepo2.findByIsDeleted(isDeleted, pageable);
            } else {
                findall = productRepo2.findByKeyword(keyword, isDeleted, pageable);
            }
            int totalPage = findall.getTotalPages() - 1;
            if (totalPage < 0) {
                totalPage = 0;
            }
            Map<String, Object> response = new HashMap<>();
            response.put("data", findall.getContent());
            response.put("currentPage", findall.getNumber());
            response.put("total", findall.getTotalElements());
            response.put("totalPage", totalPage);
            response.put("perPage", findall.getSize());
            response.put("perPageElement", findall.getNumberOfElements());

            if (findall.getSize() < 1) {
                throw new CustomException("Product not found!");
            } else {
                return response;
            }
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public ProductMasterEntity2 getProduct(Integer productId) {
        try {
            return productRepo2.findById(productId).get();
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public Map<String, Object> getProductDetailsallStatus(String adminStatus, int page, int limit, String sort,
            String sortName, Boolean isDeleted, String keyword, Optional<String> sortBy) {
        try {
            int CountData = (int) productRepo2.count();
            Pageable pagingSort = null;
            if (limit == 0) {
                limit = CountData;
            }

            if (sort.equals("ASC")) {
                pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
            } else {
                pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
            }

            Page<ProductMasterEntity2> findAll = null;
            Integer All = 0;
            Integer Pending = 0;
            Integer Approved = 0;
            Integer Rejected = 0;

            All = productRepo2.countByIsDeleted(isDeleted);
            Pending = productRepo2.countByIsDeletedAndAdminStatus(isDeleted, "Pending");
            Approved = productRepo2.countByIsDeletedAndAdminStatus(isDeleted, "Approved");
            Rejected = productRepo2.countByIsDeletedAndAdminStatus(isDeleted, "Rejected");

            LOGGER.info(adminStatus);
            if (keyword.isEmpty()) {
                if (adminStatus.equals("All")) {
                    LOGGER.info("BehindAll");
                    findAll = productRepo2.findByIsDeleted(isDeleted, pagingSort);
                } else if (adminStatus.equals("Pending")) {
                    LOGGER.info("Behind Pending");
                    findAll = productRepo2.findByIsDeletedAndAdminStatus(isDeleted, "Pending", pagingSort);
                } else if (adminStatus.equals("Approved")) {
                    LOGGER.info("Behind Approved");
                    findAll = productRepo2.findByIsDeletedAndAdminStatus(isDeleted, "Approved", pagingSort);
                } else if (adminStatus.equals("Rejected")) {
                    LOGGER.info("Behind Rejected");
                    findAll = productRepo2.findByIsDeletedAndAdminStatus(isDeleted, "Rejected", pagingSort);
                }
            } else {
                if (adminStatus.equals("All")) {
                    LOGGER.info("Behind into else All");
                    findAll = productRepo2.SearchAndfindByIsDeleted(keyword, isDeleted, pagingSort);
                } else if (adminStatus.equals("Pending")) {
                    LOGGER.info("Behind into else Pending");
                    findAll = productRepo2.SearchAndfindByIsDeletedAndAdminStatus(keyword, isDeleted, "Pending",
                            pagingSort);
                } else if (adminStatus.equals("Approved")) {
                    LOGGER.info("Behind into else Approved");
                    findAll = productRepo2.SearchAppAndfindByIsDeletedAndAdminStatus(keyword, isDeleted, "Approved",
                            pagingSort);
                } else if (adminStatus.equals("Rejected")) {
                    LOGGER.info("Behind into else Rejected");
                    findAll = productRepo2.SearchAndfindByIsDeletedAndAdminStatus(keyword, isDeleted, "Rejected",
                            pagingSort);
                }

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
            response.put("All", All);
            response.put("Pending", Pending);
            response.put("Approved", Approved);
            response.put("Rejected", Rejected);

            if (findAll.getSize() < 1) {
                throw new CustomException("Product not found!");
            } else {
                return response;
            }
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }

    }

}
