package com.xinggou.admin.bc.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xinggou.admin.common.core.BaseController;
import com.xinggou.common.constant.EnvConst;
import com.xinggou.common.exception.BizException;
import com.xinggou.common.utils.StrKit;
import io.swagger.annotations.Api;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.util.Date;


/**
 * 系统日志
 */
@Api(tags = "系统日志")
@ApiSupport(order = 800)
@Controller
public class LogController extends BaseController {

    @GetMapping("/mlog")
    public ResponseEntity<FileSystemResource> down(String date, Integer n) {
        StringBuilder fileName = new StringBuilder(EnvConst.LOG_FILE);
        if (StrKit.notBlank(date)) {
            fileName.append(".").append(date);
            fileName.append(".").append(n == null ? 0 : n);
        }
        fileName.append(".log");

        File file = new File(fileName.toString());
        if (!file.exists()) {
            throw new BizException("找不到该日志文件");
        }

        return renderFile(file, file.getName());
    }

    private ResponseEntity<FileSystemResource> renderFile(File file, String downloadName) {
        if (file == null) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + downloadName);
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.ok() .headers(headers) .contentLength(file.length()) .contentType(MediaType.parseMediaType("application/octet-stream")) .body(new FileSystemResource(file));
    }
}
