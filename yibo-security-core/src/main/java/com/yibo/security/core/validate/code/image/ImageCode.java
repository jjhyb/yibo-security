package com.yibo.security.core.validate.code.image;

import com.yibo.security.core.validate.code.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author: huangyibo
 * @Date: 2019/7/29 18:05
 * @Description:
 *
 * 图形验证码
 *
 * 图形验证码和短信验证码有共同的属性，所以使用继承
 */
public class ImageCode extends ValidateCode {

    private BufferedImage image;

    //expireIn表示过期时间的毫秒值
    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code,expireIn);
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code,expireTime);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
