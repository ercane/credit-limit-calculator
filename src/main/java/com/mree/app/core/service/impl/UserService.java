package com.mree.app.core.service.impl;

import com.mree.app.core.common.model.AuthUserInfo;
import com.mree.app.core.common.model.UserInfo;
import com.mree.app.core.common.ref.Role;
import com.mree.app.core.common.ref.UserStatus;
import com.mree.app.core.config.JwtTokenProvider;
import com.mree.app.core.exception.AppServiceException;
import com.mree.app.core.persist.User;
import com.mree.app.core.repo.UserRepository;
import com.mree.app.core.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * @author MREE * *
 */

@Service
public class UserService extends BaseService<User, UserInfo> implements IUserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserRepository getRepo() {
        return repo;
    }

    @PostConstruct
    public void init() throws AppServiceException {
        /*First user*/

        List<User> system = getRepo().findByUsername("system");
        if (system == null || system.isEmpty()) {
            UserInfo i = new UserInfo();
            i.setName("Test");
            i.setSurname("User");
            i.setUsername("test");
            i.setPassword("test");
            i.setStatus(UserStatus.ACTIVE);
            signup(i);
        }
    }

    @Override
    public User getEntity(UserInfo info) {
        User entity;
        if (info.getId() == null) {
            entity = new User();
        } else {
            entity = getRepo().findById(info.getId()).get();
        }

        return entity;
    }

    @Override
    public User beforeCreate(UserInfo info) throws AppServiceException {
        validateFields(info);

        List<User> list = getRepo().findByUsername(info.getUsername());

        if (!list.isEmpty()) {
            throw new AppServiceException("There is already a username as " + info.getUsername());
        }

        return getEntity(info);
    }

    @Override
    public void afterCreate(User entity) throws AppServiceException {

    }

    @Override
    public User beforeUpdate(UserInfo info) throws AppServiceException {
        if (info.getId() == null) {
            throw new AppServiceException("User cannot be found. Because there is no id ");
        }

        validateFields(info);
        return getEntity(info);
    }

    @Override
    public void afterUpdate(User entity) throws AppServiceException {

    }

    @Override
    public void beforeDelete(Long id) throws AppServiceException {
        if (id == null) {
            throw new AppServiceException("User cannot be found. Because there is no id ");
        }

        User entity = getRepo().findById(id).get();

        if (entity == null) {
            throw new AppServiceException("There is no user with id " + id);
        }
    }

    private void validateFields(UserInfo info) throws AppServiceException {
        if (!StringUtils.hasLength(info.getName())) {
            throw new AppServiceException("Name is required!");
        }

        if (!StringUtils.hasLength(info.getSurname())) {
            throw new AppServiceException("Surname is required!");
        }

        if (!StringUtils.hasLength(info.getUsername())) {
            throw new AppServiceException("Username is required!");
        }

        if (!StringUtils.hasLength(info.getPassword())) {
            throw new AppServiceException("Password is required!");
        }
    }

    @Override
    public User getByUsername(String username) throws AppServiceException {
        return getRepo().findByUsername(username).get(0);
    }

    @Override
    public AuthUserInfo signup(UserInfo info) throws AppServiceException {
        info.setPassword(passwordEncoder.encode(info.getPassword()));
        UserInfo create = create(info);
        String token = jwtTokenProvider.createToken(create.getUsername(), Arrays.asList(Role.ROLE_CLIENT));
        AuthUserInfo authUserInfo = new AuthUserInfo();
        authUserInfo.setUser(create);
        authUserInfo.setToken(token);
        return authUserInfo;
    }

    @Override
    public AuthUserInfo login(UserInfo info) throws AppServiceException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(info.getUsername(), info.getPassword()));
            User user = getByUsername(info.getUsername());
            String token = jwtTokenProvider.createToken(info.getUsername(), Arrays.asList(Role.ROLE_CLIENT));
            AuthUserInfo authUserInfo = new AuthUserInfo();
            authUserInfo.setUser(user.toInfo());
            authUserInfo.setToken(token);
            return authUserInfo;
        } catch (AuthenticationException e) {
            throw new AppServiceException("Invalid username/password supplied " + HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


}
