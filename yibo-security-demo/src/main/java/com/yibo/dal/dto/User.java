package com.yibo.dal.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.yibo.validator.MyConstraint;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Past;
import java.util.Date;

/**
 * @author: huangyibo
 * @Date: 2019/7/26 23:50
 * @Description:
 */
public class User {

    public interface UserSimpleView {};

    public interface UserDetailView extends UserSimpleView {};


    private String id;

    @MyConstraint(message = "这是一个测试")
    private String username;

    @ApiModelProperty(value = "用户年龄起始值")
    private int age;

    @ApiModelProperty(value = "用户年龄终止值")
    private int ageTo;


    @NotBlank(message = "密码不能为空")
    private String password;

    @Past(message = "生日必须是过去的时间")
    private Date birthday;

    @JsonView(UserSimpleView.class)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonView(UserSimpleView.class)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(int ageTo) {
        this.ageTo = ageTo;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", ageTo=" + ageTo +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
