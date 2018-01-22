package com.ylfin.wallet.portal.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.validation.Valid;

import com.ylfin.core.exception.ServiceException;
import com.ylfin.core.exception.SystemException;
import com.ylfin.wallet.portal.controller.req.Base64UploadReq;
import com.ylfin.wallet.portal.controller.vo.UploadResult;
import com.ylfin.wallet.portal.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api("图片上传接口")
@RestController
@RequestMapping("/api")
public class UploadController {

	private static final Pattern IMG_PATTERN = Pattern.compile(".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$");

	@Autowired
	private ImageService imageService;

	//	@ApiOperation("文件上传")
//	@PostMapping("/upload-img")
	public Map<String, String> uploadFile(List<MultipartFile> files) {

		checkFileName(files);

		Map<String, String> resultMap = new LinkedHashMap<>();
		for (MultipartFile file : files) {
			try {
				// 先上传到 ftp 临时目录, 后续业务使用时需要将文件通过 rename 方式转移到正确的目录
				String extension = FilenameUtils.getExtension(file.getOriginalFilename());
				String targetPath = imageService.uploadTmpImg(file.getInputStream(), extension);
				resultMap.put(file.getOriginalFilename(), targetPath);
			} catch (IOException e) {
				throw new SystemException("文件上传失败", e);
			}
		}
		return resultMap;
	}

	@ApiOperation("文件上传")
	@PostMapping(value = "/upload-img", params = "type=base64")
	public UploadResult base64Upload(@Valid @RequestBody Base64UploadReq uploadReq) {
		String extension = FilenameUtils.getExtension(uploadReq.getFilename());
		byte[] bytes = Base64.getDecoder().decode(uploadReq.getBase64Content());
		String tmpImg = imageService.uploadTmpImg(bytes, extension);
		UploadResult uploadResult = new UploadResult();
		uploadResult.setTargetPath(tmpImg);
		return uploadResult;
	}

	private void checkFileName(List<MultipartFile> files) {
		for (MultipartFile file : files) {
			if (!IMG_PATTERN.matcher(file.getOriginalFilename()).matches()) {
				throw new ServiceException(String.format("文件 %s 不是图片格式", file.getOriginalFilename()), "ILLEGAL_IMAGE_TYPE");
			}
		}
	}
}
