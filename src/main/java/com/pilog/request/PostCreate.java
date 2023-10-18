package com.pilog.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
public class PostCreate {
    @NotBlank(message = "타이틀을 입력해주세요.")
    private final String title;

    @NotBlank(message = "콘텐츠를 입력해주세요.")
    private final String content;

    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
