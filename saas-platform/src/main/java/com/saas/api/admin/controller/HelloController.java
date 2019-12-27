package com.saas.api.admin.controller;

import com.saas.api.admin.service.sys.auth.UserService;
import com.saas.api.common.res.BaseResponse;
import com.saas.api.common.util.ResultVOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private UserService authAdminService;

    @GetMapping("/hello")
    public BaseResponse hello(
            @RequestParam(value = "offset") Integer offset
            , @RequestParam("offset") Integer limit
    ) {
        return ResultVOUtils.error(1, "ssss");
    }

}
