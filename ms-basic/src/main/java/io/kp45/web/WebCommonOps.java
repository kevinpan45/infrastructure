package io.kp45.web;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import io.kp45.client.oss.OssMinIoClient;
import io.kp45.constant.DateFormat;
import io.kp45.exception.BasicRuntimeException;
import io.kp45.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebCommonOps {
    @Autowired
    private OssMinIoClient ossClient;

    /**
     * temporary file in local path /tmp, maybe auto delete by system temporary file
     * deletion policy, don't think of it as a reusable resource
     * 
     * @param multipartFile
     * @return upload input stream write to local file in directory /tmp, file name
     *         format like /tmp/2021_10_01_13_59_59_filename
     */
    File uploadFileToLocal(MultipartFile multipartFile) {
        SecurityUtils.uploadCheck(multipartFile);

        String uploadFileName = multipartFile.getOriginalFilename();
        File dest = new File("/tmp/" + DateUtil.format(new Date(), DateFormat.FILE_FORMAT) + "_" + uploadFileName);
        File localFile = null;
        try {
            localFile = FileUtil.writeFromStream(multipartFile.getInputStream(), dest);
        } catch (IORuntimeException | IOException e) {
            log.error("Process upload file stream error", e);
            throw new BasicRuntimeException("Process upload file error");
        }

        if (localFile == null || localFile.length() == 0) {
            throw new BasicRuntimeException("Cannot upload empty file");
        }

        return localFile;
    }

    void uploadFileToOss(MultipartFile multipartFile, String bucketName, String objectName) {
        SecurityUtils.uploadCheck(multipartFile);
        if (StringUtils.hasText(bucketName)) {
            throw new BasicRuntimeException("Store path parameters must be set.");
        }

        if (!StringUtils.hasText(objectName)) {
            objectName = multipartFile.getOriginalFilename();
        }
        ossClient.upload(bucketName, objectName, multipartFile);
    }

}
