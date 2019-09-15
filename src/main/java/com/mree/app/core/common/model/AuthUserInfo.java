package com.mree.app.core.common.model;

import lombok.Data;

@Data
public class AuthUserInfo {
    private UserInfo user;
    private String token;

}
