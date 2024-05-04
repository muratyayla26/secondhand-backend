package com.yayla.secondhand.secondhandbackend.exception;

import com.yayla.secondhand.secondhandbackend.model.enumtype.ResponseStatusType;
import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHelper {

    public ResponseEntity<BaseResponse> getUnknownExceptionResponse(Exception exception) {
        String genericErrorCode = "An unexpected error occurred.";
        log.error("An unexpected error occurred.", exception);
        BaseResponse baseResponse = createErrorResponse(genericErrorCode);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponse);
    }

    public BaseResponse createErrorResponse(String statusMessage) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setResponseStatusType(ResponseStatusType.FAILURE);
        baseResponse.setStatusMessage(statusMessage);
        return baseResponse;
    }
}
