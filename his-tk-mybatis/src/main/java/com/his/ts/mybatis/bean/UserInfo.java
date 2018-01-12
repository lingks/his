package com.his.ts.mybatis.bean;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by kx on 17/3/29.
 */

@Data   //注解在类上：提供类所有属性的getting和setting方法
@Setter //注解在属性上：为属性提供setting方法
@Getter //注解在属性上：为属性提供getting方法
@NoArgsConstructor //注解在类上;为类提供一个无参的构造方法
@AllArgsConstructor//注解在类上;为类提供一个全参的构造方法
@EqualsAndHashCode //注解在类上;为类提供equals和hashCode方法
@ToString
@Table(name = "userinfo") //增加注解声明表名
public class UserInfo {
    //增加注解声明字段名
    @Id
    private String id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
}