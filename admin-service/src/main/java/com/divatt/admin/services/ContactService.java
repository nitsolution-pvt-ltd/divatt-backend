package com.divatt.admin.services;

import org.springframework.http.ResponseEntity;

import com.divatt.admin.DTO.ContactDTO;
import com.divatt.admin.DTO.FundsDTO;
import com.divatt.admin.DTO.PayOutDTO;
import com.divatt.admin.DTO.RazorpayXPaymentDTO;

public interface ContactService {

public ResponseEntity<?> addcontactsService(ContactDTO contactDTO);
public ResponseEntity<?> addFunds(Long designerId,FundsDTO fundsDto);
public ResponseEntity<?> addPayOut(String token, PayOutDTO payOutDTO);
public ResponseEntity<?> getPayOutListById(String token, Long designerId, String orderId, int productId);
public ResponseEntity<?> getPayOutList(String token);
public ResponseEntity<?> getContactListById(Long designerId);
public ResponseEntity<?> getContactList();
public ResponseEntity<?> getFundsList();
public ResponseEntity<?> getFundsListById(Long designerId);
//public ResponseEntity<?> getdesignerAccNo(Long designerId);
public ResponseEntity<?> postRazorpayXHandle(RazorpayXPaymentDTO xPaymentDTO);


}
