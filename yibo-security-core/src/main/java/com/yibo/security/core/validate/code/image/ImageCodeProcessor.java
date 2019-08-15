package com.yibo.security.core.validate.code.image;

import com.yibo.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * @author: huangyibo
 * @Date: 2019/7/30 23:43
 * @Description:
 */


/**
 * 图片验证码处理器
 */
//@Component("imageCodeProcessor")
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    /**
     * 发送图形验证码，将其写到响应中
     */
    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
        ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
