package com.userservice.Exception;

import java.util.Date;

public class UserExceptionResponse {
    private String errorMessage;
    private String description;
    private Date date;

    public UserExceptionResponse(String errorMessage, String description, Date date) {
        this.errorMessage = errorMessage;
        this.description = description;
        this.date = date;
    }
    
    public String getErrorMessage() {
        return this.errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
}

