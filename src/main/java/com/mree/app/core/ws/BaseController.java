package com.mree.app.core.ws;

import com.mree.app.core.common.model.BaseInfo;
import com.mree.app.core.common.ws.ServiceUri;
import com.mree.app.core.exception.AppServiceException;
import com.mree.app.core.persist.BaseEntity;
import com.mree.app.core.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** * @author MREE * * */
@Slf4j
@CrossOrigin(origins = "*")
public abstract class BaseController < E extends BaseEntity < I >, I extends BaseInfo, S extends IBaseService < E, I > > {

    public abstract IBaseService < E, I > getService();

    @ResponseBody
    @RequestMapping("/")
    public String home() {
        return "Hello World!";
    }

    @ResponseBody
    @GetMapping(path = ServiceUri.ID_PARAM)
    public I getById(@PathVariable Long id) throws AppServiceException {
        return getService().getById(id);
    }

    @ResponseBody
    @PostMapping(path = ServiceUri.ADD)
    I create(@RequestBody I info) throws AppServiceException {
        return getService().create(info);
    }

    @ResponseBody
    @PutMapping(path = ServiceUri.UPDATE)
    I update(@RequestBody I info) throws AppServiceException {
        return getService().update(info);
    }

    @ResponseBody
    @GetMapping(path = ServiceUri.LIST)
    List < I > getList() throws AppServiceException {
        return getService().getList();
    }

    @ResponseBody
    @DeleteMapping(path = ServiceUri.DELETE_ID)
    void delete(@PathVariable Long id) throws AppServiceException {
        getService().delete(id);
    }

}
