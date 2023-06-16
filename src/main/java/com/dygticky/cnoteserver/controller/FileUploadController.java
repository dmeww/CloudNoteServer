package com.dygticky.cnoteserver.controller;

import com.dygticky.cnoteserver.mapper.UserMapper;
import com.dygticky.cnoteserver.model.Resp;
import com.dygticky.cnoteserver.model.RespCode;
import jakarta.annotation.Resource;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@CrossOrigin
public class FileUploadController {


    @Value("${file.upload-dir}")
    private String uploadDir;


   private String address = "100.111.172.93";

    static List<String> availableNames = List.of(".jpg", ".jpeg", ".png", ".gif");

    @Resource
    UserMapper userMapper;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile image) {
        System.out.println("ON UPLOAD");
        if (image.isEmpty()) {
            return new ResponseEntity<>("请选择一个文件上传", HttpStatus.BAD_REQUEST);
        }

        try {
            // 获取文件名
            String fileName = image.getOriginalFilename();

            // 构建文件存储路径
            Path filePath = Paths.get(uploadDir, fileName);

            // 保存文件到指定路径
            FileUtils.copyInputStreamToFile(image.getInputStream(), filePath.toFile());

            // 构建文件访问URL
            String fileUrl = "http://" + address + ":6321" + "/uploads/" + fileName;
            return new ResponseEntity<>(fileUrl, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("文件上传失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping("/avatar/{uid}")
    @ResponseBody
    public Resp uploadAvatar(@RequestParam("avatar") MultipartFile avatar, @PathVariable("uid") Integer uid) {
        System.out.println("ON AVATAR UPLOAD");
        if (avatar.isEmpty()) {
            return Resp.builder()
                    .code(RespCode.FAIL_CLIENT.getValue())
                    .msg("上传失败,请选择一个文件上传")
                    .data(null)
                    .build();
        }

        try {
            // 获取文件名
            String fileName = avatar.getOriginalFilename();
            System.out.println(fileName);

            boolean available = false;
            String end = "";

            for (String name : availableNames) {
                assert fileName != null;
                if (fileName.contains(name)) {
                    available = true;
                    end = name;
                    break;
                }
            }

            if (!available)
                return Resp.builder()
                        .code(RespCode.FAIL_CLIENT.getValue())
                        .msg("上传失败,不支持的文件格式")
                        .data(null)
                        .build();

            fileName = UUID.randomUUID().toString() + end;

            // 构建文件存储路径
            Path filePath = Paths.get(uploadDir + "/avatar", fileName);

            // 保存文件到指定路径
            FileUtils.copyInputStreamToFile(avatar.getInputStream(), filePath.toFile());

            // 构建文件访问URL
            String fileUrl = String.format("http://%s:6321/avatars/%s", address, fileName);
            System.out.println(fileUrl);
            userMapper.setHasAvatar(uid, fileUrl);
            return Resp.builder()
                    .code(RespCode.SUCCESS.getValue())
                    .msg("上传成功")
                    .data(fileUrl)
                    .build();

        } catch (IOException e) {
            e.printStackTrace();
            return Resp.builder()
                    .code(RespCode.FAIL_CLIENT.getValue())
                    .msg("上传失败" + e.getMessage())
                    .data(null)
                    .build();
        }
    }


}
