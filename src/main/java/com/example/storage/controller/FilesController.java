package com.example.storage.controller;

import com.example.minio.MinioService;
import com.example.storage.model.TFile;
import com.example.storage.service.FileService;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class FilesController {

    private final FileService fileService;
    private final MinioService minioService;

    @PostMapping("/files")
    public String saveFile(Principal principal, MultipartFile fileUpload) throws Exception {
        //保存文件
        if (fileUpload.isEmpty()) {
            return "redirect:/result?error";
        }
        fileService.addFile(principal, fileUpload);
        return "redirect:/result?success";
    }

    @GetMapping("/files/delete")
    public String deleteNote(@RequestParam("id") long fileId) throws Exception {
        //删除文件
        if (fileService.deleteFile(fileId)) {
            return "redirect:/result?success";
        }
        return "redirect:/result?error";
    }

    @GetMapping(path = "/files/{userName}/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    ResponseEntity<Resource> download(Principal principal, @PathVariable String userName, @PathVariable String fileName) throws IOException,
            InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, ErrorResponseException,
            XmlParserException, InsufficientDataException, InternalException {
        //下载文件
        if (!principal.getName().equals(userName)) {
            throw new RuntimeException("权限不足");
        }
        Optional<TFile> fileOptional = fileService.getFile(principal.getName(), fileName);
        if (fileOptional.isPresent()) {
            //获取文件元数据，构造getObject请求
            TFile file = fileOptional.get();
            String objectName = file.getObjectName();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=" + URLEncoder.encode(file.getFilename(), StandardCharsets.UTF_8.toString()));
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(minioService.getObject(objectName)));
        } else {
            throw new RuntimeException("文件不存在");
        }
    }
}
