package com.test.springbootDemo.service.impl;

import com.test.springbootDemo.service.TUserService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by Administrator on 2018/3/22.
 */
@Component("tUserService")
public class TUserServiceImpl implements TUserService{
    @Override
    public String test(String str) {
        if (StringUtils.isEmpty(str)){
            return "";
        }
        return str+str;
    }
}
