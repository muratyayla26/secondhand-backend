package com.yayla.secondhand.secondhandbackend.model.response;


import com.yayla.secondhand.secondhandbackend.model.enumtype.ResponseStatusType;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {

    private ResponseStatusType responseStatusType = ResponseStatusType.SUCCESS;
    private String statusMessage;
    private long systemTime = System.currentTimeMillis();
}
