package com.expense.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/getMsg")
    public ResponseEntity<String> getmsg(){
        return new ResponseEntity<>("Application is running", HttpStatus.OK);
    }
}
