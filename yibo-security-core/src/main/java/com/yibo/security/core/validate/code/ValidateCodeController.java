package com.yibo.security.core.validate.code;

import com.yibo.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author: huangyibo
 * @Date: 2019/7/29 18:27
 * @Description:
 */

@RestController
public class ValidateCodeController {


    /**
     * 这里用到了依赖查找
     *
     * 在spring环境开发中,我们对于某个接口又多个实现,我们就可以注入一个Map,key是bean的名称,value是bean的接口形式.
     */
    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeGeneratorMap;

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;


    /**
     * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response,@PathVariable String type) throws Exception {
//        validateCodeGeneratorMap.get(type+"CodeProcessor").create(new ServletWebRequest(request, response));
        validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
    }
}
