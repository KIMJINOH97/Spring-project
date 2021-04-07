package com.book.spring.controller;

import com.book.spring.controller.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/dto")
    public HelloResponseDto hello(@RequestParam(name = "name") String name, @RequestParam(name = "amount") int amount){
        return new HelloResponseDto(name, amount);
    }

    @GetMapping("/hellodto")
    public HelloResponseDto hello(@RequestParam HelloResponseDto dto){
        return dto;
    }
}
