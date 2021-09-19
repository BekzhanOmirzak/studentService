package com.studentservice.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
    BUCKET_NAME("studentservice");
    private final String bucketName;
}
