package com.yibo.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author: huangyibo
 * @Date: 2019/7/27 19:32
 * @Description:
 */

@Component
public class MockQueue {

    private Logger logger = LoggerFactory.getLogger(AsyncController.class);

    private String placeOrder;

    private String completeOrder;

    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder){
        new Thread(() -> {
            logger.info("接到下单请求："+placeOrder);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.completeOrder = placeOrder;
            logger.info("下单请求处理完毕："+placeOrder);
        }).start();

    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}
