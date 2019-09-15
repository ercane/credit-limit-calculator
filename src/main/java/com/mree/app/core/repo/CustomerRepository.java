package com.mree.app.core.repo;

import com.mree.app.core.common.model.CustomerInfo;
import com.mree.app.core.common.ref.CustomerStatus;
import com.mree.app.core.persist.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CustomerRepository extends BaseRepository<Customer, CustomerInfo> {

    List<Customer> findByStatus(CustomerStatus status);

    Customer findByTcNo(String tcNo);
}
