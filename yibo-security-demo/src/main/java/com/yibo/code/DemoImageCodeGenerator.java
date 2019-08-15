package com.yibo.code;

import com.yibo.security.core.validate.code.image.ImageCode;
import com.yibo.security.core.validate.code.ValidateCodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author: huangyibo
 * @Date: 2019/7/29 22:27
 * @Description:
 */

//为了演示其他功能这里先注释掉
//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

    private Logger logger = LoggerFactory.getLogger(DemoImageCodeGenerator.class);

    @Override
    public ImageCode generate(ServletWebRequest request) {
        //这里只为证明当名称为imageCodeGenerator的bean存在时,不会调用yibo-security-core工程中的ImageCodeGenerator类
        logger.info("更高级的图形验证码生成代码");
        return null;
    }
}
