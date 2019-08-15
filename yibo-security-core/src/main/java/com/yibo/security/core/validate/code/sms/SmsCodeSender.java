package com.yibo.security.core.validate.code.sms;

/**
 * @author: huangyibo
 * @Date: 2019/7/30 21:44
 * @Description:
 *
 * 短信验证码发送接口
 */
public interface SmsCodeSender {

    void send(String telephone,String code);
}
