package com.yayla.secondhand.secondhandbackend.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BaseDto {

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
