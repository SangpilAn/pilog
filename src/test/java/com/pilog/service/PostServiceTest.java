package com.pilog.service;

import com.pilog.domain.Post;
import com.pilog.repository.PostRepository;
import com.pilog.request.PostCreate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @BeforeEach
    void clear(){
        postRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("게시글 작성")
    void writeTest(){
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("foo")
                .content("bar")
                .build();

        //when
        postService.write(postCreate);

        //then
        Assertions.assertThat(postRepository.count()).isEqualTo(1L);
        Post post = postRepository.findAll().get(0);
        Assertions.assertThat(post.getTitle()).isEqualTo("foo");
        Assertions.assertThat(post.getContent()).isEqualTo("bar");
    }

    @Test
    @DisplayName("게시글 1개 조회")
    void getTest(){
        //given
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();

        postRepository.save(requestPost);

        //when
        Post post = postService.get(requestPost.getId());

        //then
        Assertions.assertThat(post).isNotNull();
        Assertions.assertThat(postRepository.count()).isEqualTo(1L);
        Assertions.assertThat(post.getTitle()).isEqualTo("foo");
        Assertions.assertThat(post.getContent()).isEqualTo("bar");
    }

}