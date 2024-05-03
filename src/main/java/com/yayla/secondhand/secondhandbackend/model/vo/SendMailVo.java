package com.yayla.secondhand.secondhandbackend.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SendMailVo {
    private String to;
    private String subject;
    private String content;
}
