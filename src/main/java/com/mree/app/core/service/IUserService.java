package com.mree.app.core.service;

import com.mree.app.core.common.model.AuthUserInfo;
import com.mree.app.core.common.model.UserInfo;
import com.mree.app.core.exception.AppServiceException;
import com.mree.app.core.persist.User;

/**
 * @author MREE * *
 */
public interface IUserService extends IBaseService<User, UserInfo> {

    User getByUsername(String username) throws AppServiceException;

    AuthUserInfo signup(UserInfo info) throws AppServiceException;

    AuthUserInfo login(UserInfo info) throws AppServiceException;

}
