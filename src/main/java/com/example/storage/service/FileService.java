package com.example.storage.service;

import com.example.minio.MinioService;
import com.example.storage.model.TFile;
import com.example.storage.model.TUser;
import com.example.storage.repository.FileRepository;
import com.example.storage.repository.UserRepository;
import io.minio.ObjectWriteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final MinioService minioService;

    public void addFile(Principal principal, MultipartFile multipartFile) throws Exception {
        TUser user = userRepository.findByUsername(principal.getName());
        String objectName = UUID.randomUUID().toString();
        //上传文件至MinIO
        ObjectWriteResponse objectWriteResponse = minioService.uploadObject(objectName, multipartFile.getInputStream());
        TFile file = new TFile()
                .setFileSize(multipartFile.getSize())
                .setObjectName(objectWriteResponse.object())
                .setUrl(String.format("%s/%s", principal.getName(), multipartFile.getOriginalFilename()))
                .setFilename(multipartFile.getOriginalFilename())
                .setUser(user);
        fileRepository.save(file);
    }

    public boolean deleteFile(long fileId) throws Exception {
        Optional<TFile> fileOptional = fileRepository.findById(fileId);
        if (fileOptional.isPresent()) {
            TFile file = fileOptional.get();
            String objectName = file.getObjectName();
            //删除MinIO上保存的文件
            minioService.removeObject(objectName);
            fileRepository.delete(file);
            return true;
        }
        return false;
    }

    public Optional<TFile> getFile(String userName, String fileName) {
        TUser user = userRepository.findByUsername(userName);
        return fileRepository.findOneByFilenameAndUser(fileName, user);
    }
}
