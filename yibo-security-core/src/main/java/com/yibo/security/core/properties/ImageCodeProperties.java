package com.yibo.security.core.properties;

/**
 * @author: huangyibo
 * @Date: 2019/7/29 21:26
 * @Description:
 *
 * 图片验证码配置类
 */
public class ImageCodeProperties extends SmsCodeProperties {

    public ImageCodeProperties(){
        setLength(4);
    }

    private int width = 67;

    private int height = 23;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
