package com.controller;

import com.commen.Result;
import org.springframework.web.bind.annotation.*;

/**
 * @author 杨世博
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/login")
    public Result login(){
        System.out.println("ysb");
        return null;
    }
}
