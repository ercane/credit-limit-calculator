package com.mree.app.core.service.impl;

import com.mree.app.core.client.CreditPointClient;
import com.mree.app.core.common.model.CustomerInfo;
import com.mree.app.core.common.ref.CustomerStatus;
import com.mree.app.core.exception.AppServiceException;
import com.mree.app.core.persist.Customer;
import com.mree.app.core.repo.CustomerRepository;
import com.mree.app.core.service.ICustomerService;
import com.mree.app.core.util.CreditLimitCalculator;
import com.mree.app.core.util.GeneralUtils;
import com.mree.app.core.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService extends BaseService<Customer, CustomerInfo> implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CreditPointClient creditPointClient;

    @Override
    public CustomerRepository getRepo() {
        return customerRepository;
    }

    @Override
    public Customer getEntity(CustomerInfo info) {
        if (Objects.nonNull(info.getId())) {
            return getRepo().findById(info.getId()).get();
        } else {
            return new Customer();
        }
    }

    @Override
    public Customer beforeCreate(CustomerInfo info) throws AppServiceException {
        validateFields(info);

        Customer customer = getRepo().findByTcNo(info.getTcNo());
        if (Objects.nonNull(customer)) {
            throw new AppServiceException("There is a customer has same Tc Number");
        }

        return getEntity(info);
    }

    @Override
    public void afterCreate(Customer entity) throws AppServiceException {
        calculateCreditLimit(entity);
    }

    @Override
    public Customer beforeUpdate(CustomerInfo info) throws AppServiceException {
        if (info.getId() == null) {
            throw new AppServiceException("Customer cannot be found. Because there is no id ");
        }

        validateFields(info);
        return getEntity(info);
    }

    @Override
    public void afterUpdate(Customer entity) throws AppServiceException {
        calculateCreditLimit(entity);
    }

    @Override
    public void beforeDelete(Long id) throws AppServiceException {

    }

    @Override
    public CustomerInfo calculateCreditLimit(Long id) throws AppServiceException {
        Optional<Customer> customer = getRepo().findById(id);

        if (!customer.isPresent())
            throw new AppServiceException("Customer cannot be found. Id: " + id);

        return calculateCreditLimit(customer.get());
    }

    @Override
    public CustomerInfo getByTcNo(String tcNo) throws AppServiceException {
        Customer customer = getRepo().findByTcNo(tcNo);
        return customer.toInfo();
    }

    @Override
    public List<CustomerInfo> getByStatus(CustomerStatus status) throws AppServiceException {
        List<Customer> list = getRepo().findByStatus(status);
        List<CustomerInfo> infoList = new ArrayList<>();
        list.stream().forEach(c -> infoList.add(c.toInfo()));
        return infoList;
    }

    public CustomerInfo calculateCreditLimit(Customer entity) throws AppServiceException {
        String creditPoint = "0";
        try {
            creditPoint = creditPointClient.getCreditPoint();
            creditPoint = creditPoint.replace(System.lineSeparator(), ""); //Service send response with line seperator
        } catch (Exception e) {
            creditPoint = "" + GeneralUtils.generateRandom(0, 2000);
        }
        Double creditLimit = CreditLimitCalculator.calculateCreditLimit(Integer.parseInt(creditPoint), entity.getMonthlyIncome());
        entity.setCreditLimit(creditLimit);
        entity = getRepo().save(entity);
        return entity.toInfo();
    }

    private void validateFields(CustomerInfo info) throws AppServiceException {
        if (!StringUtils.hasLength(info.getTcNo())) {
            throw new AppServiceException("Tc No is required!");
        }

        if (!StringUtils.hasLength(info.getName())) {
            throw new AppServiceException("Name is required!");
        }

        if (!StringUtils.hasLength(info.getSurname())) {
            throw new AppServiceException("Surname is required!");
        }

        if (!Objects.nonNull(info.getPhone())) {
            throw new AppServiceException("Phone number is required!");
        }

        if (!ValidationUtils.validatePhoneNumber(info.getPhone())) {
            throw new AppServiceException("Phone number format is invalid!");
        }

        if (!Objects.nonNull(info.getMonthlyIncome())) {
            throw new AppServiceException("Monthly income is required!");
        }
    }


}
