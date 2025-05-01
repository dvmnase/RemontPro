package org.example.remontpro.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderFileDto {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    private Long id;
    private LocalDateTime uploadedAt;
    private byte[] fileData;
}
