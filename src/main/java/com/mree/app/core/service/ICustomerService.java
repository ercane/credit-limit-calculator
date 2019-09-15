package com.mree.app.core.service;

import com.mree.app.core.common.model.CustomerInfo;
import com.mree.app.core.common.ref.CustomerStatus;
import com.mree.app.core.exception.AppServiceException;
import com.mree.app.core.persist.Customer;

import java.util.List;

public interface ICustomerService extends IBaseService<Customer, CustomerInfo> {
    CustomerInfo calculateCreditLimit(Long id) throws AppServiceException;

    CustomerInfo getByTcNo(String tcNo) throws AppServiceException;

    List<CustomerInfo> getByStatus(CustomerStatus msg) throws AppServiceException;
}
