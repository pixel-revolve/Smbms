package com.chy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/api")
public class ApiBaseController {

    @Autowired(required = false)
    public HttpServletRequest request;

    @Autowired(required = false)
    public HttpServletResponse response;
}
