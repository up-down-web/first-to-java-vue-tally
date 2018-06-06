package com.homkai.tally.controller;

import com.homkai.tally.model.ListRequest;
import com.homkai.tally.model.Payment;
import com.homkai.tally.model.PaymentType;
import com.homkai.tally.model.ListResponse;
import com.homkai.tally.repository.PaymentRepository;
import com.homkai.tally.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.Valid;
import java.util.List;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:8111")
@RequestMapping("/api")
public class TallyController {

    @Autowired
    PaymentRepository paymentRepository;

    // Get All Payment
    @GetMapping("/payment")
    public ListResponse getAllPayments(
            @RequestParam String params
    ) {
        ObjectMapper mapper = new ObjectMapper();
        ListRequest request;
        try {
            request = mapper.readValue(params, ListRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Sort sort = request.getSort();
        Pageable pageable = sort == null
                ? PageRequest.of(request.getPageNumber(), request.getPageSize())
                : PageRequest.of(request.getPageNumber(), request.getPageSize(), request.getSort());
        Page page = paymentRepository.findAll(pageable);
        return new ListResponse(request, page);
    }

    // Create a new Payment
    @PostMapping("/payment")
    public Payment createPayment(@Valid @RequestBody Payment payment) {
        return paymentRepository.save(payment);
    }

    // Get a Single Payment
    @GetMapping("/payment/type")
    public List<PaymentType> getAllPaymentType() {
        return paymentRepository.findDistinctBy();
    }

    // Update a Payment
    @PutMapping("/payment/{id}")
    public Payment updatePayment(@PathVariable(value = "id") Long paymentId,
                           @Valid @RequestBody Payment paymentDetails) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", paymentId));

        payment.setName(paymentDetails.getName());
        payment.setCharge(paymentDetails.getCharge());
        payment.setSite(paymentDetails.getSite());
        payment.setRemark(paymentDetails.getRemark());
        payment.setPaymentTime(paymentDetails.getPaymentTime());

        Payment updatedPayment = paymentRepository.save(payment);
        return updatedPayment;
    }

    // Delete a Payment
    @DeleteMapping("/payment/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable(value = "id") Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", paymentId));

        paymentRepository.delete(payment);

        return ResponseEntity.ok().build();
    }
}
