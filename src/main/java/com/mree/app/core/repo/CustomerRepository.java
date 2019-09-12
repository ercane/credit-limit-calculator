package com.mree.app.core.repo;

import com.mree.app.core.common.model.CustomerInfo;
import com.mree.app.core.persist.Customer;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends BaseRepository<Customer, CustomerInfo> {
}
