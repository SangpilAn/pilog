package com.pilog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pilog.domain.Post;
import com.pilog.repository.PostRepository;
import com.pilog.request.PostCreate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void clean(){
        postRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("/posts 요청 시 빈값을 출력한다.")
    void test() throws Exception {
        //given
        PostCreate postCreate = PostCreate.builder()
                        .title("제목입니다.")
                        .content("내용입니다.")
                        .build();

        String json = mapper.writeValueAsString(postCreate);

        //expect
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("/posts 요청 시 타이틀이 빈값일 경우를 확인한다.")
    void errorEmptyTest() throws Exception {
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("  ")
                .content("내용입니다.")
                .build();

        String json = mapper.writeValueAsString(postCreate);

        //expect
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("/posts 요청 시 타이틀이 null 값일 경우를 확인한다.")
    void errorNullTest() throws Exception {
        //given
        PostCreate postCreate = PostCreate.builder()
                .content("내용입니다.")
                .build();

        String json = mapper.writeValueAsString(postCreate);

        //expect
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("/posts 요청 시 DB 에 값이 저장된다.")
    void writeTest() throws Exception {
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = mapper.writeValueAsString(postCreate);

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        
        //then
        Assertions.assertThat(1L).isEqualTo(postRepository.count());

        Post post = postRepository.findAll().get(0);
        Assertions.assertThat("제목입니다.").isEqualTo(post.getTitle());
        Assertions.assertThat("내용입니다.").isEqualTo(post.getContent());

        postRepository.deleteAll();
    }

}