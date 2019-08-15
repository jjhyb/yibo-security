package com.yibo.validator;

import com.yibo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author: huangyibo
 * @Date: 2019/7/27 1:31
 * @Description:
 */
public class MyConstraintValidator implements ConstraintValidator<MyConstraint,Object> {

    @Autowired
    private HelloService helloService;

    @Override
    public void initialize(MyConstraint myConstraint) {
        System.out.println("my validator init");
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        helloService.greeting("tom");
        System.out.println(obj);
        //校验通过返回true,不通过返回false
        return false;
    }
}
