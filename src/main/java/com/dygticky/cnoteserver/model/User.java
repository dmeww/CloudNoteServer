package com.dygticky.cnoteserver.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    Integer uid;
    String username;
    String password;
    String alias;
    String token;

    Integer sex;

    String intro;

    String tel;

    String avatar;


}
