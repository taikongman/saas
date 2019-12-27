package com.saas.api.admin.controller.file;

import com.saas.api.admin.service.sys.ConfigService;
import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.entity.sys.Config;
import com.saas.api.common.util.ApiResultI18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 上传相关
 */
@RestController
@RequestMapping("/admin/file/upload")
public class UploadController {
	
	@Autowired
    private ConfigService configService;

    /**
     * 上传的token
     * @return
     */
    @GetMapping("/qiuNiuUpToken")
    public ApiResultI18n qiuNiuUpToken(HttpServletRequest request) {
    	String lanType = request.getHeader("X-LanType");

        // 这里接入 七牛云 的SDK 就可以了
        Map<String, Object> result = new HashMap<>();
        result.put("upload_url", "/admin/file/upload/createFile"); // 这里可以直接设置成七牛云的上传 url，不用服务端这边去post请求七牛云的上传接口
        result.put("up_token", "xxxxxxx");

        return ApiResultI18n.success(result, lanType);
    }

    /**
     * 上传文件（如果是接入的第三方的建议这个接口废弃）
     * @return
     */
    @PostMapping("/createFile")
    public ApiResultI18n createFile(@RequestParam("file") MultipartFile file,
    		HttpServletRequest request) {
    	
    	String lanType = request.getHeader("X-LanType");
    	if (file.isEmpty()) {
            return ApiResultI18n.failure(ResponseCodeI18n.UPLOAD_FILE_IS_EMPTY.getCode(), 
    				ResponseCodeI18n.UPLOAD_FILE_IS_EMPTY.getMessage(), lanType);
        }
    	
    	String timestamp = String.valueOf(new Date().getTime()/1000);
        String fileName = timestamp + "_" +file.getOriginalFilename();
        Config config = configService.findByKeyName("ROOT_PATH");
        Config resDomain = configService.findByKeyName("RESOURCE_DOMAIN");
        
        // /home/www/yupin/armapi/uploads/resources/ 
        String filePath = config.getKeyValue() + "uploads/resources/";
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT_YYYYMM);
        String curMonth = sdf.format(new Date());
        filePath = filePath + curMonth + "/";
        
        File filedir = new File(filePath);
        if(!filedir.exists() && !filedir.isDirectory()) {
        	filedir.mkdirs();
        }
        
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
        	return ApiResultI18n.failure(ResponseCodeI18n.UPLOAD_FILE_IS_FAILDED.getCode(), 
    				ResponseCodeI18n.UPLOAD_FILE_IS_FAILDED.getMessage(), lanType);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("filePath", "uploads/resources/" + curMonth + "/" + fileName);
        result.put("fileUrl", resDomain.getKeyValue() + "uploads/resources/" + curMonth + "/" + fileName);
        return ApiResultI18n.success(result, lanType);
    }

}
