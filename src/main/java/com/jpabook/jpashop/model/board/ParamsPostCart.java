package com.jpabook.jpashop.model.board;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class ParamsPostCart {
    @NotEmpty
    @Size(min = 1, max = 100)
    @ApiModelProperty(value = "카트그룹", required = true)
    private String cart_group_id;
    @Size(min = 2, max = 500)
    @ApiModelProperty(value = "카트그룹명", required = true)
    private String cart_group_name;
}
