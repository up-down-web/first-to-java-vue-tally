package com.homkai.tally.repository;

import com.homkai.tally.model.Payment;
import com.homkai.tally.model.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<PaymentType> findDistinctBy();
}
