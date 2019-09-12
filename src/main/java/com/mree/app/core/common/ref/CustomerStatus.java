
package com.mree.app.core.common.ref;

public enum CustomerStatus {
    ACTIVE(0, "Active"),
    PASSIVE(1, "Passive");

    private Integer code;
    private String desc;

    CustomerStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static CustomerStatus get(Integer code) {
        for (CustomerStatus status : CustomerStatus.values()) {
            if (status.getCode().intValue() == code.intValue())
                return status;
        }

        throw new IllegalArgumentException();
    }
}
