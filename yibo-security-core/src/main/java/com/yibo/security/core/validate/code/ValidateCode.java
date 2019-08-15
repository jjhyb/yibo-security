package com.yibo.security.core.validate.code;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: huangyibo
 * @Date: 2019/7/29 18:05
 * @Description:
 *
 * 短信验证码
 */
public class ValidateCode implements Serializable {

    private String code;

    private LocalDateTime expireTime;

    //expireIn表示过期时间的毫秒值
    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
