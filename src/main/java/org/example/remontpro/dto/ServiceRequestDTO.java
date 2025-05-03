package org.example.remontpro.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ServiceRequestDTO {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    private String name;
    private String description;
    private double price;
    private MultipartFile photo;

    public byte[] getPhotoBytes() throws IOException {
        return photo != null ? photo.getBytes() : null;
    }
}
