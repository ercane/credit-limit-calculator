package com.mree.app.core.common.model;

import com.mree.app.core.common.ref.CustomerStatus;
import lombok.Data;

@Data
public class CustomerInfo extends BaseInfo {
    private String name;
    private String surname;
    private Double monthlyIncome;
    private String phone;
    private Double creditLimit;
    private CustomerStatus status;

}
