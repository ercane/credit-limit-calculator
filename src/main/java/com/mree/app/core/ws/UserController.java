package com.mree.app.core.ws;

import com.mree.app.core.common.model.AuthUserInfo;
import com.mree.app.core.common.model.UserInfo;
import com.mree.app.core.common.ws.ServiceUri;
import com.mree.app.core.common.ws.UserServiceUri;
import com.mree.app.core.exception.AppServiceException;
import com.mree.app.core.persist.User;
import com.mree.app.core.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author MREE * *
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = UserServiceUri.USER)
public class UserController extends BaseController<User, UserInfo, IUserService> {

    @Autowired
    private IUserService service;

    @Override
    public IUserService getService() {
        return service;
    }

    @ResponseBody
    @PostMapping(ServiceUri.SIGNUP)
    public AuthUserInfo signup(@RequestBody UserInfo info) throws AppServiceException {
        return getService().signup(info);
    }

    @ResponseBody
    @PostMapping(ServiceUri.LOGIN)
    public AuthUserInfo login(@RequestBody UserInfo info) throws AppServiceException {
        return getService().login(info);
    }


}
