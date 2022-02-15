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
public class ParamsPost {
	@NotEmpty
    @Size(min = 2, max = 50)
    @ApiModelProperty(value = "�Խ��Ǹ�", required = true)
    private String boardname;
	@NotEmpty
    @Size(min = 2, max = 50)
    @ApiModelProperty(value = "�ۼ��ڸ�", required = true)
    private String author;
    @NotEmpty
    @Size(min = 2, max = 100)
    @ApiModelProperty(value = "����", required = true)
    private String title;
    @Size(min = 2, max = 500)
    @ApiModelProperty(value = "����", required = true)
    private String content;
}
