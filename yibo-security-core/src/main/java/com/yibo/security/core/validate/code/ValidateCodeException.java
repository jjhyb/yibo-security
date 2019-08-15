package com.yibo.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * @author: huangyibo
 * @Date: 2019/7/29 19:10
 * @Description:
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
