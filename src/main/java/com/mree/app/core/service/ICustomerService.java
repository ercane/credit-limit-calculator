package com.mree.app.core.service;

import com.mree.app.core.common.model.CustomerInfo;
import com.mree.app.core.exception.AppServiceException;
import com.mree.app.core.persist.Customer;

public interface ICustomerService extends IBaseService<Customer, CustomerInfo> {
    CustomerInfo calculateCreditLimit(Long id) throws AppServiceException;
}