package com.dygticky.cnoteserver.controller;


import com.dygticky.cnoteserver.model.Resp;
import com.dygticky.cnoteserver.model.RespCode;
import com.dygticky.cnoteserver.model.User;
import com.dygticky.cnoteserver.model.UserReq;
import com.dygticky.cnoteserver.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/note/auth")
public class AuthController {

    @Resource
    UserService userService;


    @PostMapping("/login")
    public Resp login(@RequestBody User userLogin) {

        if (userLogin.getUsername() != null &&
                userLogin.getUsername().length() > 0 &&
                userLogin.getPassword() != null &&
                userLogin.getPassword().length() > 0) {

            User user = userService.login(userLogin);

            if (user == null) {
                return Resp
                        .builder()
                        .code(RespCode.FAIL_CLIENT.getValue())
                        .msg("登录失败,请检查你的账号和密码")
                        .build();
            } else {
                String token = "TOKEN::" + user.getUid() + "_" + user.getUsername();
                user.setToken(token);
                user.setPassword(null);
                return Resp
                        .builder()
                        .code(RespCode.SUCCESS.getValue())
                        .msg("登录成功")
                        .data(user)
                        .build();
            }
        } else {
            return Resp
                    .builder()
                    .code(RespCode.SUCCESS.getValue())
                    .msg("登录失败,用户名和密码不能为空")
                    .data(null)
                    .build();
        }
    }

    @PostMapping("/register")
    public Resp register(@RequestBody User userRegister) {

        if (userRegister.getUsername() != null &&
                userRegister.getUsername().length() > 0 &&
                userRegister.getPassword() != null &&
                userRegister.getPassword().length() > 0) {

            User result = userService.register(userRegister);

            if (result != null) {
                String token = result.getUid() + "::" + result.getUsername();
                result.setToken(token);
                result.setPassword(null);
                return Resp.builder()
                        .code(RespCode.SUCCESS.getValue())
                        .data(result)
                        .msg("注册成功")
                        .build();
            } else {
                return Resp.builder()
                        .code(RespCode.FAIL_SERVER.getValue())
                        .data(null)
                        .msg("注册失败,该账号已经有人用了")
                        .build();
            }

        }
        return Resp.builder()
                .code(RespCode.FAIL_CLIENT.getValue())
                .data(null)
                .msg("注册失败,账号和密码不能为空")
                .build();
    }

    @PostMapping("/update")
    public Resp updateUser(@RequestBody UserReq req) {
        System.out.println(req);
        if (req.getUid() == null || req.getField() == null || req.getField().equals("unknown")) {
            log.info("UserUpdate:: 修改失败，请求错误");
            return Resp.builder()
                    .code(RespCode.FAIL_CLIENT.getValue())
                    .data(null)
                    .msg("修改失败，请求错误")
                    .build();
        } else {
            try {
                userService.updateUserInfo(req.getUid(), req.getField(), req.getValue());
                User latest = userService.getLatest(req.getUid());
                latest.setToken("USER:" + latest.getUid());
                log.info("UserUpdate:: 修改成功" + latest);
                return Resp.builder()
                        .code(RespCode.SUCCESS.getValue())
                        .data(latest)
                        .msg("修改成功")
                        .build();
            } catch (Exception e) {
                log.info("UserUpdate:: 修改失败，请稍后重试");
                e.printStackTrace();
                return Resp.builder()
                        .code(RespCode.FAIL_SERVER.getValue())
                        .data(null)
                        .msg("修改失败，请稍后重试")
                        .build();
            }
        }
    }


}
