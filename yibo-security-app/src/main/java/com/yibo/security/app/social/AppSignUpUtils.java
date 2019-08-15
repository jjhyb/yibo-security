package com.yibo.security.app.social;

import com.yibo.security.app.exception.AppSecretException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.concurrent.TimeUnit;

/**
 * @author: huangyibo
 * @Date: 2019/8/13 16:21
 * @Description:
 */
@Component
public class AppSignUpUtils {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    /**
     * 将第三方用户信息保存到redis中
     * @param request
     * @param connectionData
     */
    public void saveConnectionData(WebRequest request, ConnectionData connectionData){
        redisTemplate.opsForValue().set(getKey(request),connectionData,10,TimeUnit.MINUTES);
    }

    private String getKey(WebRequest request) {
        String deviceId = request.getHeader("deviceId");
        if(StringUtils.isBlank(deviceId)){
            throw new AppSecretException("设备id参数不能为空");
        }
        return "yibo:security:social.connect."+deviceId;
    }

    public void doPostSignUp(WebRequest request,String userId){
        String key = getKey(request);
        if(!redisTemplate.hasKey(key)){
            throw new AppSecretException("无法找到缓存的第三方用户社交账号信息");
        }
        ConnectionData connectionData = (ConnectionData) redisTemplate.opsForValue().get(key);
        Connection<?> connection = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId()).createConnection(connectionData);
        //将connection和用户信息一起存入数据库
        usersConnectionRepository.createConnectionRepository(userId).addConnection(connection);
        redisTemplate.delete(key);
    }

}
