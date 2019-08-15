package com.yibo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author: huangyibo
 * @Date: 2019/7/28 1:01
 * @Description:
 */

@Component
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {

    private Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    //这里可以注入dao层进行数据库查询，查询出用户信息进行校验


    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 表单登录，用于校验用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("表单登录用户名："+username);
        //根据用户名查找用户信息

        //根据查找到的用户信息判断用户是否被冻结


        //passwordEncoder.encode("123456")这一步应该是用户注册的时候对用户密码进行加密
       /* String password = passwordEncoder.encode("123456");
        logger.info("数据库密码是：" + password);
        return new User(username,password,
                //账户是否可用，       账户是否过期，         密码是否过期，             账户是否被冻结
                true,true,true,true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));*/
        //注意：SocialUser是User的一个子类，所以可以这样做

        return buildUser(username);
    }

    /**
     * 用于社交登录
     * @param userId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        logger.info("社交登录用户ID："+userId);
        //根据用户名查找用户信息

        //根据查找到的用户信息判断用户是否被冻结
        return buildUser(userId);
    }

    private SocialUserDetails buildUser(String userId) {
        //passwordEncoder.encode("123456")这一步应该是用户注册的时候对用户密码进行加密
        String password = passwordEncoder.encode("123456");
        logger.info("数据库密码是：" + password);
        return new SocialUser(userId,password,
                //账户是否可用，       账户是否过期，         密码是否过期，             账户是否被冻结
                true,true,true,true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER,ROLE_VIP"));
    }
}
