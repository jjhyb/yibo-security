package com.yibo.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author: huangyibo
 * @Date: 2019/7/29 22:04
 * @Description:
 */
public interface ValidateCodeGenerator {

    /**
     * 验证码生成
     * @param request
     * @return
     */
    ValidateCode generate(ServletWebRequest request);
}
