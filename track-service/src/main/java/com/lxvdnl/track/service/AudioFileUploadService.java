package com.lxvdnl.track.service;

import com.lxvdnl.track.exception.UploadException;
import com.lxvdnl.track.service.props.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AudioFileUploadService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    public String uploadFile(MultipartFile audioFile) {
        try {
            createBucket();
        } catch (Exception e) {
            throw new UploadException("Failed to create bucket for audio upload");
        }

        if (audioFile.isEmpty() || audioFile.getOriginalFilename() == null) {
            throw new UploadException("Empty or invalid audio file");
        }
        String fileName = generateFileName(audioFile);
        InputStream inputStream;
        try {
            inputStream = audioFile.getInputStream();
        } catch (Exception e) {
            throw new UploadException("Failed to read audio file stream");
        }
        saveAudio(inputStream, fileName);
        return fileName;
    }

    @SneakyThrows
    private void createBucket() {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(minioProperties.getBucket())
                .build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .build());
        }
    }

    private String generateFileName(MultipartFile audioFile) {
        String extension = getExtension(audioFile);
        return UUID.randomUUID() + "." + extension;
    }

    private String getExtension(MultipartFile audioFile) {
        return Objects.requireNonNull(audioFile.getOriginalFilename())
                .substring(audioFile.getOriginalFilename().lastIndexOf(".") + 1);
    }

    @SneakyThrows
    private void saveAudio(InputStream inputStream, String fileName) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(minioProperties.getBucket())
                .object(fileName)
                .build());
    }

}
