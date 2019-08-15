package com.yibo.web.async;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

/**
 * @author: huangyibo
 * @Date: 2019/7/27 19:21
 * @Description:
 */

@RestController
public class AsyncController {

    private Logger logger = LoggerFactory.getLogger(AsyncController.class);

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @GetMapping("/order1")
    public Callable<String> order1() throws InterruptedException {
        logger.info("主线程开始");
        Callable<String> result = new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info("副线程开始");
                Thread.sleep(1000);
                logger.info("副线程返回");
                return "success";
            }
        };

        logger.info("主线程返回");
        return result;
    }

    @GetMapping("/order2")
    public DeferredResult<String> order2() throws InterruptedException {
        logger.info("主线程开始");

        //模拟处理订单
        //生成订单号
        String orderNumber = RandomStringUtils.random(16);
        //将订单消息放入消息队列
        mockQueue.setPlaceOrder(orderNumber);


        DeferredResult result = new DeferredResult();
        deferredResultHolder.getMap().put(orderNumber,result);

        logger.info("主线程返回");
        return result;
    }
}
