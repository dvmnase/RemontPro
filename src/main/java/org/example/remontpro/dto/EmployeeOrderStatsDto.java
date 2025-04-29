package org.example.remontpro.dto;

public class EmployeeOrderStatsDto {

    public EmployeeOrderStatsDto(Long id, String fullName, String qualification, String phoneNumber,
                                 Long userId, String username, String email,
                                 Long newOrders, Long inProgressOrders,
                                 Long completedOrders, Long cancelledOrders) {
        this.id = id;
        this.fullName = fullName;
        this.qualification = qualification;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.newOrders = newOrders;
        this.inProgressOrders = inProgressOrders;
        this.completedOrders = completedOrders;
        this.cancelledOrders = cancelledOrders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getNewOrders() {
        return newOrders;
    }

    public void setNewOrders(Long newOrders) {
        this.newOrders = newOrders;
    }

    public Long getInProgressOrders() {
        return inProgressOrders;
    }

    public void setInProgressOrders(Long inProgressOrders) {
        this.inProgressOrders = inProgressOrders;
    }

    public Long getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(Long completedOrders) {
        this.completedOrders = completedOrders;
    }

    public Long getCancelledOrders() {
        return cancelledOrders;
    }

    public void setCancelledOrders(Long cancelledOrders) {
        this.cancelledOrders = cancelledOrders;
    }

    private Long id;
    private String fullName;
    private String qualification;
    private String phoneNumber;
    private Long userId;
    private String username;
    private String email;

    private Long newOrders;
    private Long inProgressOrders;
    private Long completedOrders;
    private Long cancelledOrders;

    // геттеры/сеттеры/конструкторы
}
