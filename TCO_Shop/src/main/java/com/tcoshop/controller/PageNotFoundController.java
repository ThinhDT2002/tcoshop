package com.tcoshop.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageNotFoundController implements ErrorController{
    @RequestMapping("/notFound")
    public String pageNotFound() {
        return "tco-client/other/error-page";
    }
}
