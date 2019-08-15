package com.yibo.security.core.validate.code.impl;

import com.yibo.security.core.validate.code.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * @author: huangyibo
 * @Date: 2019/7/30 23:29
 * @Description:
 */
public abstract class AbstractValidateCodeProcessor <T extends ValidateCode> implements ValidateCodeProcessor {

    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    /**
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
     *
     * 这里用到了依赖查找
     *
     * 在spring环境开发中,我们对于某个接口又多个实现,我们就可以注入一个Map,key是bean的名称,value是bean的接口形式.
     */
    @Autowired
    private Map<String,ValidateCodeGenerator> validateCodeGeneratorMap;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        T validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    /**
     * 生成校验码
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private T generate(ServletWebRequest request) {
        String type = getValidateCodeType(request).toString().toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGeneratorMap.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }
        return (T) validateCodeGenerator.generate(request);


        /*String type = getProcessorType(request);
        ValidateCodeGenerator validateCodeGenerator = validateCodeGeneratorMap.get(type + "CodeGenerator");
        return (T) validateCodeGenerator.generate(request);*/
    }


    /**
     * 保存校验码
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, T validateCode) {
        //如果是图片验证码，则只把code取出来放入session，不将图片放入session
        ValidateCode code = new ValidateCode(validateCode.getCode(),validateCode.getExpireTime());
        validateCodeRepository.save(request,code,getValidateCodeType(request));
    }

    /**
     * 构建验证码放入session时的key
     *
     * @param request
     * @return
     */
    private String getSessionKey(ServletWebRequest request) {
        return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
    }

    /**
     * 发送校验码，由子类实现
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, T validateCode) throws Exception;


    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    /**
     * 校验逻辑
     * @param request
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(ServletWebRequest request) {
        ValidateCodeType codeType = getValidateCodeType(request);

        T codeInSession = (T)validateCodeRepository.get(request, codeType);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    codeType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(codeType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(codeType + "验证码不存在");
        }

        if (codeInSession.isExpried()) {
            validateCodeRepository.remove(request, codeType);
            throw new ValidateCodeException(codeType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(codeType + "验证码不匹配");
        }

        validateCodeRepository.remove(request, codeType);
    }


/*
    *//**
     * 根据请求的url获取校验码的类型
     * @param request
     * @return
     *//*
    private String getProcessorType(ServletWebRequest request) {
        return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
    }*/
}
