package io.kp45.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kp45.client.oss.OssMinIoClient;

@RestController
public class CommonApi extends WebCommonOps {
    @Autowired
    private OssMinIoClient ossClient;

    @GetMapping("/commons/files")
    public String getFileDownloadLink(String bucketName, String objectName) {
        return ossClient.generateDownloadLink(bucketName, objectName);
    }
}
