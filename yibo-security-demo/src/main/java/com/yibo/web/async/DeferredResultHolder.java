package com.yibo.web.async;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: huangyibo
 * @Date: 2019/7/27 19:36
 * @Description:
 */

@Component
public class DeferredResultHolder {

    private Map<String,DeferredResult<String>> map = new HashMap<>();

    public Map<String, DeferredResult<String>> getMap() {
        return map;
    }

    public void setMap(Map<String, DeferredResult<String>> map) {
        this.map = map;
    }
}
