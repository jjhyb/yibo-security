package com.yibo.security.core.validate.code.sms;

import com.yibo.security.core.properties.SecurityProperties;
import com.yibo.security.core.validate.code.ValidateCode;
import com.yibo.security.core.validate.code.ValidateCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author: huangyibo
 * @Date: 2019/7/29 22:05
 * @Description:
 */

/**
 * @Component
 * 不用@Component的用意在于，ValidateBeanConfig配置类中使用了@ConditionalOnMissingBean(name = "imageCodeGenerator")
 */

//@Component("smsCodeGenerator")
@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new ValidateCode(code,securityProperties.getCode().getSms().getExpireIn());
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
