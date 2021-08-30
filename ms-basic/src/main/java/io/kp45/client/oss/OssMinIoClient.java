package io.kp45.client.oss;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import io.kp45.exception.BasicRuntimeException;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OssMinIoClient implements InitializingBean {
    private MinioClient minioClient;

    @Autowired
    private MinIoProperties props;

    public MinioClient getClient() {
        if (minioClient == null) {
            throw new BasicRuntimeException("OSS client has not initial.");
        }

        return minioClient;
    }

    public void afterPropertiesSet() throws Exception {
        authenticate();
    }

    public void authenticate() {
        synchronized (minioClient) {
            this.minioClient = MinioClient.builder().endpoint(props.getEndpoint())
                    .credentials(props.getAccessKey(), props.getSecretKey()).build();
        }
    }

    public String upload(String bucketName, String objectName, MultipartFile multipartFile) {
        InputStream stream = null;
        try {
            stream = multipartFile.getInputStream();
        } catch (IOException e) {
            log.error("Prcess upload file {} error", multipartFile.getOriginalFilename(), e);
            throw new BasicRuntimeException("Process upload file error.");
        }
        PutObjectArgs args = PutObjectArgs.builder().bucket(bucketName).object(objectName)
                .stream(stream, multipartFile.getSize(), -1L).build();
        try {
            return minioClient.putObject(args).object();
        } catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
                | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
                | IOException e) {
            log.error("Upload file error", e);
            throw new BasicRuntimeException("Store file error.");
        }
    }

    /**
     * 
     * @param bucketName
     * @param objectName
     * @return download link expired in value of properties config minio.expires
     */
    public String generateDownloadLink(String bucketName, String objectName) {
        return generateDownloadLink(bucketName, objectName, null);
    }

    /**
     * 
     * @param bucketName
     * @param objectName
     * @param expiry     time unit is second, limited to 15 minutes
     * @return download link expired in expiry
     */
    public String generateDownloadLink(String bucketName, String objectName, Integer expiry) {
        if (expiry == null) {
            // set default when not specifying link expiry
            expiry = props.getExpires();
        }

        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(objectName)
                .expiry(props.getExpires(), TimeUnit.SECONDS).build();
        try {
            return minioClient.getPresignedObjectUrl(args);
        } catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
                | InvalidResponseException | NoSuchAlgorithmException | XmlParserException | ServerException
                | IOException e) {
            log.error("Presign object url error", e);
            throw new BasicRuntimeException("Generate file download link");
        }
    }
}
