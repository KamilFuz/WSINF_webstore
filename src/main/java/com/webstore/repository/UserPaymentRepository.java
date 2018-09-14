package com.webstore.repository;

import com.webstore.domain.UserPayment;
import org.springframework.data.repository.CrudRepository;

public interface UserPaymentRepository extends CrudRepository<UserPayment, Long> {

}
