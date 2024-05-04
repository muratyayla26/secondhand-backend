package com.yayla.secondhand.secondhandbackend.model.dto.product;

import com.yayla.secondhand.secondhandbackend.model.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString(callSuper = true)
public class ProductMediaDto extends BaseDto {
    private Long mediaId;

    private UUID mediaKey;
}
