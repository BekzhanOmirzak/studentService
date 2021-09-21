package com.studentservice.demo.service;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.studentservice.demo.entity.BucketName;
import com.studentservice.demo.entity.ImageFile;
import com.studentservice.demo.exception.ApiRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AmazonS3Service {

    private final AmazonS3 amazonS3;


    public void upload(String path,
                       String fileName,
                       Optional<Map<String, String>> optionalMetaData,
                       InputStream inputStream
    ) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        optionalMetaData.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(objectMetadata::addUserMetadata);
            }
        });
        try {
            amazonS3.putObject(path, fileName, inputStream, objectMetadata);
        } catch (AmazonServiceException ex) {
            throw new IllegalArgumentException("Failed to upload the file" + ex);
        }


    }


    public byte[] downloadPhoto(String path, String key) {

        try {
            S3Object object = amazonS3.getObject(path, key);
            S3ObjectInputStream objectContent = object.getObjectContent();
            return IOUtils.toByteArray(objectContent);
        } catch (AmazonServiceException | IOException ex) {
            throw new ApiRequestException("Failed to download file ", ex);
        }

    }

    public List<ImageFile> getListOfPhotos(String email) {
        String bucketName = BucketName.BUCKET_NAME.getBucketName();
        ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request()
                .withBucketName(bucketName).withPrefix(email + "/imagelink");

        List<ImageFile> imageFileList = new ArrayList<>();

        ListObjectsV2Result result;

        do {
            result = amazonS3.listObjectsV2(listObjectsRequest);

            for (S3ObjectSummary summary : result.getObjectSummaries()) {
                String fullPath = email + "/" + "imagelink/";
                String key = summary.getKey().substring(fullPath.length());
                String path = BucketName.BUCKET_NAME.getBucketName() + "/" + email + "/imagelink";
                System.out.println("Full Path  : " + bucketName + "/" + summary.getKey());
                System.out.println("Path : " + path);
                System.out.println("Key  : " + key);

                ImageFile imageFile = new ImageFile();
                imageFile.setFileName(key);
                imageFile.setPath(path);
                imageFileList.add(imageFile);

            }

        } while (result.isTruncated());

        return imageFileList;

    }


}
