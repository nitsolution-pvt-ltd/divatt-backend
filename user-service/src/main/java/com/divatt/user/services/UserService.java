package com.divatt.user.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.divatt.user.entity.StateEntity;
import com.divatt.user.entity.UserDesignerEntity;
import com.divatt.user.entity.UserLoginEntity;
import com.divatt.user.entity.PCommentEntity.ProductCommentEntity;
import com.divatt.user.entity.cart.UserCartEntity;
import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.entity.order.OrderSKUDetailsEntity;
import com.divatt.user.entity.wishlist.WishlistEntity;
import com.divatt.user.response.GlobalResponse;

public interface UserService {

    public GlobalResponse postWishlistService(ArrayList<WishlistEntity> wishlistEntity);

    public GlobalResponse deleteWishlistService(Integer productId, Integer userId);

    public Map<String, Object> getWishlistDetails(int page, int limit, String sort, String sortName, String keyword,
            Optional<String> sortBy);

    public ResponseEntity<?> getUserWishlistDetails(Integer userId, Integer page, Integer limit);

    public GlobalResponse postCartDetailsService(List<UserCartEntity> userCartEntity);

    public ResponseEntity<?> putCartDetailsService(UserCartEntity userCartEntity);

    public GlobalResponse deleteCartService(Integer pId);

    public ResponseEntity<?> getUserCartDetailsService(Integer userId, Integer page, Integer limit);

    public GlobalResponse postProductCommentService(ProductCommentEntity<?> productCommentEntity);

    public GlobalResponse putProductCommentService(ProductCommentEntity<?> productCommentEntity);

    public GlobalResponse putProductCommentStatusService(ProductCommentEntity<?> productCommentEntity);

    public GlobalResponse deleteProductCommentService(Integer Id);

    public ResponseEntity<?> getProductUser();

    public ResponseEntity<?> postfollowDesignerService(@Valid UserDesignerEntity userDesignerEntity);

    public ResponseEntity<?> getDesignerDetails(int page, int limit, String sort, String sortName, Boolean isDeleted,
            String keyword, Optional<String> sortBy);

    public ResponseEntity<?> productDetails(Integer productId, String userId);

    public ResponseEntity<?> getDesignerUser();

    public ResponseEntity<?> getDesignerProfileDetailsService(Integer designerId, Long userId);

    public ResponseEntity<?> getPerDesignerProductListService(int page, int limit, String sort, String sortName,
            Boolean isDeleted, String keyword, Optional<String> sortBy, Integer designerId);

    public GlobalResponse multipleDelete(Integer userId);

    public List<Integer> viewProductService(String orderId);

    public Map<String, Object> getUserListService(int page, int limit, String sort, String sortName, Boolean isDeleted,
            String keyword, Optional<String> sortBy);

    public List<UserDesignerEntity> followedUserListService(Integer designerIdvalue);

    public UserLoginEntity getUserById(Long userId);

    public GlobalResponse getCountFollowers(Long designerId);

    public UserLoginEntity getUserDetailsService(String token);

    public Map<String, Object> getUserStatus();

    public List<StateEntity> getStateDataService();

    public String complaintMail(String token, Integer productId);

    
    public List<Object> getListDesignerData(String userEmail);



    Page<OrderDetailsEntity> findByOrder(int page, int limit, String orderId, String orderId2, Boolean isDeleted, Optional<String> sortBy);

    public Map<String, Object> find(int page, int limit, String sort, String orderItemStatus, Boolean isDeleted,
            Optional<String> sortBy);

    public List<OrderSKUDetailsEntity> findByorderID(String orderId);
    public List<UserDesignerEntity> getUserDesignerDetails(String userEmail);
    
}
