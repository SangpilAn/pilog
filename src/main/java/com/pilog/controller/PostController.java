package com.pilog.controller;

import com.pilog.domain.Post;
import com.pilog.request.PostCreate;
import com.pilog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request){
        postService.write(request);
    }

    @GetMapping("/posts/{postId}")
    public Post get(@PathVariable(name = "postId") Long id){
        return postService.get(id);
    }
}
