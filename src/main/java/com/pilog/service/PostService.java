package com.pilog.service;

import com.pilog.domain.Post;
import com.pilog.repository.PostRepository;
import com.pilog.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate){
        Post post = Post.builder()
                        .title(postCreate.getTitle())
                        .content(postCreate.getContent())
                        .build();

        postRepository.save(post);
    }
}
