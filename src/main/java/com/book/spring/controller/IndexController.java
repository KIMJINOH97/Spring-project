package com.book.spring.controller;

import com.book.spring.controller.dto.PostsListResponseDto;
import com.book.spring.controller.dto.PostsResponseDto;
import com.book.spring.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model){
        List<PostsListResponseDto> allDesc = postsService.findAllDesc();
        model.addAttribute("posts", allDesc);
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto responseDto = postsService.findById(id);
        model.addAttribute("post", responseDto);
        return "posts-update";
    }
}
