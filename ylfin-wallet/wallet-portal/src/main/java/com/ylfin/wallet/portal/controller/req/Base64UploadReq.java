package com.ylfin.wallet.portal.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class Base64UploadReq {

	@ApiModelProperty("原始文件名,必须指定文件类型(\".jpt\"...)")
	@NotBlank
	private String filename;

	@ApiModelProperty("图片base64名称")
	@NotBlank
	private String base64Content;
}
