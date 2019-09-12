package com.mree.app.core.persist;

import com.mree.app.core.common.model.CustomerInfo;
import com.mree.app.core.common.ref.CustomerStatus;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Entity
public class Customer extends BaseEntity<CustomerInfo> {

    private String name;
    private String surname;
    private Double monthlyIncome;
    private String phone;
    private Double creditLimit;

    @Enumerated(value = EnumType.STRING)
    private CustomerStatus status;

    @Override
    public CustomerInfo toInfo() {
        CustomerInfo i=new CustomerInfo();
        i.setId(getId());
        i.setCreatedDate(getCreatedDate());
        i.setUpdatedDate(getUpdatedDate());
        i.setName(getName());
        i.setSurname(getSurname());
        i.setPhone(getPhone());
        i.setMonthlyIncome(getMonthlyIncome());
        i.setCreditLimit(getCreditLimit());
        return i;
    }

    @Override
    public void fromInfo(CustomerInfo info) {
        setCreditLimit(info.getCreditLimit());
        setMonthlyIncome(info.getMonthlyIncome());
        setName(info.getName());
        setSurname(info.getSurname());
        setPhone(info.getPhone());
        setStatus(info.getStatus());
    }
}
