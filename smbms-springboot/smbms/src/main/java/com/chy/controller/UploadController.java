package com.chy.controller;

import com.chy.model.Result;
import com.chy.utils.UpYunUtils;
import com.upyun.UpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * @author Lin
 */
@RestController
@Slf4j
@RequestMapping("/api/upload")
public class UploadController {

    Map<String, String> map;

    @Resource
    private UpYunUtils upYunUtils;

    public UploadController() {
        map = new HashMap<>();
        map.put("jpg", null);
        map.put("png", null);
        map.put("jpeg", null);
        map.put("icon", null);
        map.put("gif", null);
    }

    @PostMapping("/image")
    public Result upload(@RequestParam(value = "imgFile", required = false) MultipartFile image) {

        try {
            if (image.getSize() > 4194304) {
                return Result.failure("上传图片大小必须在4MB以内！");
            }
        } catch (Exception e) {
            log.error(e.toString());
            return Result.failure("请先上传图片！");
        }

        String[] temp = image.getOriginalFilename().split("\\.");
        String fileType = temp[temp.length - 1].toLowerCase();

        if (!map.containsKey(fileType)) {
            return Result.failure("图片类型不支持");
        }

        String fileName = UUID.randomUUID().toString().replace("-", "").substring(0, 8) + "." + fileType;
        boolean success = false;

        try {
            success = upYunUtils.uploadImg(fileName, image.getBytes());
        } catch (IOException | UpException e) {
            log.error(e.toString());
            return Result.failure("图片上传异常");
        }

        return success ? Result.success("http://pixel-revolve.test.upcdn.net/images/" + fileName) : Result.failure("图片上传失败!");

    }

}
