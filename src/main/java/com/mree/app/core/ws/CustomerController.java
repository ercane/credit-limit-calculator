package com.mree.app.core.ws;

import com.mree.app.core.common.model.CustomerInfo;
import com.mree.app.core.common.ref.CustomerStatus;
import com.mree.app.core.common.ws.CustomerServiceUri;
import com.mree.app.core.exception.AppServiceException;
import com.mree.app.core.persist.Customer;
import com.mree.app.core.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = CustomerServiceUri.CUSTOMER)
public class CustomerController extends BaseController<Customer, CustomerInfo, ICustomerService> {

    @Autowired
    private ICustomerService customerService;

    @Override
    public ICustomerService getService() {
        return customerService;
    }

    @GetMapping(CustomerServiceUri.STATUS + CustomerServiceUri.MSG_PARAM)
    public List<CustomerInfo> getByStatus(@PathVariable CustomerStatus msg) throws AppServiceException {
        return getService().getByStatus(msg);
    }

    @GetMapping(CustomerServiceUri.CALCULATE_CREDIT_LIMIT)
    public Double calculateCreditLimit(@PathVariable Long id) throws AppServiceException {
        return getService().calculateCreditLimit(id).getCreditLimit();
    }
}
