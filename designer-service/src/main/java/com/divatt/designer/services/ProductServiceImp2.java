package com.divatt.designer.services;

import java.util.Date;
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
        	LOGGER.info("Product data by product ID = {}",productRepo2.findById(productId).get().toString());
            return productRepo2.findById(productId).get();
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public Map<String, Object> getProductDetailsallStatus(String adminStatus, int page, int limit, String sort,
            String sortName, Boolean isDeleted, String keyword, Optional<String> sortBy) {
        try {
            int Count = (int) productRepo2.count();
            Pageable pagingSort = null;
            if (limit == 0) {
                limit = Count;
            }

            if (sort.equals("ASC")) {
                pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
            } else {
                pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
            }

            Page<ProductMasterEntity2> findAll = null;
            Integer live = 0;
            Integer pending = 0;
            Integer approved = 0;
            Integer rejected = 0;

            live = productRepo2.countByIsDeleted(isDeleted);
            pending = productRepo2.countByIsDeletedAndAdminStatus(isDeleted, "Pending");
            approved = productRepo2.countByIsDeletedAndAdminStatus(isDeleted, "Approved");
            rejected = productRepo2.countByIsDeletedAndAdminStatus(isDeleted, "Rejected");

            LOGGER.info(adminStatus);
            if (keyword.isEmpty()) {
                if (adminStatus.equals("live")) {
                    LOGGER.info("Behind live");
                    findAll = productRepo2.findByIsDeleted(isDeleted, pagingSort);
                } else if (adminStatus.equals("pending")) {
                    LOGGER.info("Behind pending");
                    findAll = productRepo2.findByIsDeletedAndAdminStatus(isDeleted, "Pending", pagingSort);
                } else if (adminStatus.equals("approved")) {
                    LOGGER.info("Behind approved");
                    findAll = productRepo2.findByIsDeletedAndAdminStatus(isDeleted, "Approved", pagingSort);
                } else if (adminStatus.equals("rejected")) {
                    LOGGER.info("Behind rejected");
                    findAll = productRepo2.findByIsDeletedAndAdminStatus(isDeleted, "Rejected", pagingSort);
                }
            } else {
                if (adminStatus.equals("live")) {
                    LOGGER.info("Behind into else live");
                    findAll = productRepo2.SearchAndfindByIsDeleted(keyword, isDeleted, pagingSort);
                } else if (adminStatus.equals("pending")) {
                    LOGGER.info("Behind into else pending");
                    findAll = productRepo2.SearchAndfindByIsDeletedAndAdminStatus(keyword, isDeleted, "Pending",
                            pagingSort);
                } else if (adminStatus.equals("approved")) {
                    LOGGER.info("Behind into else approved");
                    findAll = productRepo2.SearchAppAndfindByIsDeletedAndAdminStatus(keyword, isDeleted, "Approved",
                            pagingSort);
                } else if (adminStatus.equals("rejected")) {
                    LOGGER.info("Behind into else rejected");
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
            response.put("live", live);
            response.put("pending", pending);
            response.put("approved", approved);
            response.put("rejected", rejected);

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
    public Map<String, Object> getDesignerProductByDesignerId(Integer designerId, String adminStatus, Boolean isActive,
            int page, int limit, String sort, String sortName, Boolean isDeleted,
            String keyword, Optional<String> sortBy) {
        try {
            int Count = (int) productRepo2.count();
            Pageable pagingSort = null;
            if (limit == 0) {
                limit = Count;
            }

            if (sort.equals("ASC")) {
                pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
            } else {
                pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
            }

            Page<ProductMasterEntity2> findAll = null;
            Integer live = 0;
            Integer pending = 0;
            Integer reject = 0;
            Integer ls = 0;
            Integer oos = 0;

            live = productRepo2.countByIsDeletedAndDesignerIdAndIsActiveAndAdminStatus(isDeleted, designerId, isActive,
                    "Approved");
            LOGGER.info("Behind live " + live);
            pending = productRepo2.countByIsDeletedAndDesignerIdAndIsActiveAndAdminStatus(isDeleted, designerId,
                    isActive, "Pending");
            LOGGER.info("Behind Pending " + pending);
            reject = productRepo2.countByIsDeletedAndDesignerIdAndIsActiveAndAdminStatus(isDeleted, designerId,
                    isActive,
                    "Rejected");
            LOGGER.info("Behind Reject " + reject);
            oos = productRepo2.countByIsDeletedAndDesignerIdAndIsActiveAndAdminStatus(isDeleted, designerId, false,
                    "oos");
            if (keyword.isEmpty()) {
                if (adminStatus.equals("live")) {
                    findAll = productRepo2.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId,
                            "Approved", isActive, pagingSort);
                } else if (adminStatus.equals("pending")) {
                    findAll = productRepo2.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId,
                            "Pending", isActive, pagingSort);
                } else if (adminStatus.equals("reject")) {
                    findAll = productRepo2.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId,
                            "Rejected", isActive, pagingSort);
                } else if (adminStatus.equals("ls")) {
                    findAll = productRepo2.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId,
                            "ls", isActive, pagingSort);
                } else if (adminStatus.equals("oos")) {
                    findAll = productRepo2.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId,
                            "oos", false, pagingSort);
                }
            } else {
                if (adminStatus.equals("live")) {
                    findAll = productRepo2.listDesignerProductsearchByAdminStatus(keyword, isDeleted, designerId,
                            "Approved", pagingSort);
                } else if (adminStatus.equals("pending")) {
                    findAll = productRepo2.listDesignerProductsearchByAdminStatus(keyword, isDeleted, designerId,
                            "Pending", pagingSort);
                } else if (adminStatus.equals("reject")) {
                    findAll = productRepo2.listDesignerProductsearchByAdminStatus(keyword, isDeleted, designerId,
                            "Reject", pagingSort);
                } else if (adminStatus.equals("ls")) {
                    findAll = productRepo2.listDesignerProductsearchByAdminStatus(keyword, isDeleted, designerId,
                            "notify",
                            pagingSort);
                } else if (adminStatus.equals("oos")) {
                    findAll = productRepo2.listDesignerProductsearchByAdminStatusForOos(keyword, isDeleted, designerId,
                            "oos", false, pagingSort);
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
            response.put("live", live);
            response.put("pending", pending);
            response.put("reject", reject);
            response.put("ls", ls);
            response.put("oos", oos);

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
    public GlobalResponce productDeleteByproductId(Integer productId) {
        try {
            LOGGER.info("Inside - ProductServiceImp2.productDeleteByproductId()");
            if (productRepo2.existsById(productId)) {
                Boolean isDelete = false;
                Optional<ProductMasterEntity2> productData = productRepo2.findById(productId);
                ProductMasterEntity2 productMasterEntity2 = productData.get();
                if (productMasterEntity2.getIsDeleted().equals(false)) {
                    isDelete = true;
                } else {
                    return new GlobalResponce("Bad request!!", "Product allready deleted", 400);
                }
                productMasterEntity2.setIsDeleted(isDelete);
                productMasterEntity2.setUpdatedBy(productMasterEntity2.getDesignerId().toString());
                productMasterEntity2.setUpdatedOn(new Date());
                productRepo2.save(productMasterEntity2);
                return new GlobalResponce("Success", "Deleted successfully", 200);
            } else {
                return new GlobalResponce("Bad request", "Product does not exist", 400);
            }
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }

    }
}
