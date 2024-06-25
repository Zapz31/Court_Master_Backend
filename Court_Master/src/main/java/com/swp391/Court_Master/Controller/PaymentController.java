package com.swp391.Court_Master.Controller;

import com.swp391.Court_Master.Entities.ResponseObject;
import com.swp391.Court_Master.Payment.PaymentDTO;
import com.swp391.Court_Master.Payment.PaymentService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @GetMapping("/vn-pay")
    public ResponseObject<PaymentDTO.VNPayResponse> pay(HttpServletRequest request) {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
    }
    @GetMapping("/vn-pay-callback")
    public ResponseObject<PaymentDTO.VNPayResponse> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            return new ResponseObject<PaymentDTO.VNPayResponse>(HttpStatus.OK, "Success", new PaymentDTO.VNPayResponse("00", "Success", ""));
        } else {
            return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Failed", null);
        }
    }
    @PostMapping("/payment-handle")
    public ResponseEntity<Map<String, String>> postMethodName(@RequestBody Map<String, String> paymentData) {
        int amount = Integer.parseInt(paymentData.get("vnp_Amount"))/100;
        String amountString = String.valueOf(amount);
        String bankCode = paymentData.get("vnp_BankCode");
        String payDate = paymentData.get("vnp_PayDate");
        String responseCode = paymentData.get("vnp_ResponseCode");
        String TransactionNo = paymentData.get("vnp_TransactionNo");
        Map<String, String> response = new HashMap<>();
        response.put("amount", amountString);
        response.put("bankCode", bankCode);
        response.put("payDate", payDate);
        response.put("responseCode", responseCode);
        response.put("transactionNo", TransactionNo);
        
        return ResponseEntity.ok().body(response);
    }
    
}

