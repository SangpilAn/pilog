package com.pilog.request;

import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class PostCreate {
    private String title;
    private String content;
}