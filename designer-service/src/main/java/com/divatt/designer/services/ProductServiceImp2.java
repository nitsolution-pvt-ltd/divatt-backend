package com.divatt.designer.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.divatt.designer.entity.product.ProductMasterEntity;
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
            Integer Live = 0;
            Integer Pending = 0;
            Integer Approved = 0;
            Integer Rejected = 0;

            Live = productRepo2.countByIsDeleted(isDeleted);
            Pending = productRepo2.countByIsDeletedAndAdminStatus(isDeleted, "Pending");
            Approved = productRepo2.countByIsDeletedAndAdminStatus(isDeleted, "Approved");
            Rejected = productRepo2.countByIsDeletedAndAdminStatus(isDeleted, "Rejected");

            LOGGER.info(adminStatus);
            if (keyword.isEmpty()) {
                if (adminStatus.equals("Live")) {
                    LOGGER.info("Behind Live");
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
                if (adminStatus.equals("Live")) {
                    LOGGER.info("Behind into else Live");
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
            response.put("Live", Live);
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

    @Override
    public Map<String, Object> getDesignerProductByDesignerId(Integer designerId, String adminStatus,Boolean isActive,
            int page,  int limit,String sort, String sortName, Boolean isDeleted,
            String keyword,  Optional<String> sortBy) {
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
            Integer Live = 0;
            Integer Pending = 0;
            Integer Reject = 0;
            Integer ls = 0;
            Integer oos = 0;

            Live = productRepo2.countByIsDeletedAndDesignerIdAndIsActiveAndAdminStatus(isDeleted, designerId, isActive,
                    "Approved");
            Pending = productRepo2.countByIsDeletedAndDesignerIdAndIsActiveAndAdminStatus(isDeleted, designerId,
                    isActive, "Pending");
            Reject = productRepo2.countByIsDeletedAndDesignerIdAndIsActiveAndAdminStatus(isDeleted, designerId, isActive,
                    "Rejected");
            oos = productRepo2.countByIsDeletedAndDesignerIdAndIsActiveAndAdminStatus(isDeleted, designerId, false,
                    "Approved");
            // need to do get count for low stock and out of stock
            if (keyword.isEmpty()) {
                if (StringUtils.equals(adminStatus, "Live")) {
                    findAll = productRepo2.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId,
                            "Approved", isActive, pagingSort);
                } else if (StringUtils.equals(adminStatus, "Pending")) {
                    LOGGER.info("Status Datata={}", adminStatus);
                    findAll = productRepo2.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId,
                            "Pending", isActive, pagingSort);
                    LOGGER.info("Data for find all={}", findAll.getContent());
                } else if (StringUtils.equals(adminStatus, "Reject")) {
                    findAll = productRepo2.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId,
                            "Rejected", isActive, pagingSort);
                } else if (StringUtils.equals(adminStatus, "ls")) {
                    findAll = productRepo2.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId,
                            adminStatus, isActive, pagingSort);
                } else if (StringUtils.equals(adminStatus, "oos")) {
                    findAll = productRepo2.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId,
                            "Approved", false, pagingSort);
                }
            } else {
                if (StringUtils.equals(adminStatus, "Live")) {
                    findAll = productRepo2.listDesignerProductsearchByAdminStatus(keyword, isDeleted, designerId,
                            "Approved", pagingSort);
                } else if (StringUtils.equals(adminStatus, "Pending")) {
                    findAll = productRepo2.listDesignerProductsearchByAdminStatus(keyword, isDeleted, designerId,
                            "Pending", pagingSort);
                } else if (StringUtils.equals(adminStatus, "Reject")) {
                    findAll = productRepo2.listDesignerProductsearchByAdminStatus(keyword, isDeleted, designerId,
                            "Reject", pagingSort);
                } else if (StringUtils.equals(adminStatus, "ls")) {
                    findAll = productRepo2.listDesignerProductsearchByAdminStatus(keyword, isDeleted, designerId, "Ls",
                            pagingSort);
                } else if (StringUtils.equals(adminStatus, "oos")) {
                    findAll = productRepo2.listDesignerProductsearchByAdminStatusForOos(keyword, isDeleted, designerId,
                            "Approved", false, pagingSort);
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
            response.put("Live", Live);
            response.put("Pending", Pending);
            response.put("Reject", Reject);
            response.put("ls", ls);
            response.put("oos", oos);

            if (findAll.getSize() <= 1) {
                throw new CustomException("Product not found!");
            } else {
                return response;
            }
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }



}
