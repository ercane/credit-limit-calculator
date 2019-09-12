package com.mree.app.core.ws;

import com.mree.app.core.common.model.CustomerInfo;
import com.mree.app.core.common.ws.CustomerServiceUri;
import com.mree.app.core.exception.AppServiceException;
import com.mree.app.core.persist.Customer;
import com.mree.app.core.service.IBaseService;
import com.mree.app.core.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = CustomerServiceUri.CUSTOMER)
public class CustomerController extends BaseController<Customer, CustomerInfo, ICustomerService> {

    @Autowired
    private ICustomerService customerService;

    @Override
    public ICustomerService getService() {
        return customerService;
    }

    @GetMapping(CustomerServiceUri.CALCULATE_CREDIT_LIMIT)
    public Double calculateCreditLimit(@PathVariable Long id) throws AppServiceException {
        return getService().calculateCreditLimit(id).getCreditLimit();
    }
}
