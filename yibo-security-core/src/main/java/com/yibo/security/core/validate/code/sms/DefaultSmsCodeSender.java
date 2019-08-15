package com.yibo.security.core.validate.code.sms;

/**
 * @author: huangyibo
 * @Date: 2019/7/30 21:46
 * @Description:
 *
 * 短信验证码默认发送实现类
 */
public class DefaultSmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String telephone, String code) {
        System.out.println("向"+telephone+"发送手机验证码["+code+"]");
    }
}
