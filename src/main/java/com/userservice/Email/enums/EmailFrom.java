package com.userservice.Email.enums;

public enum EmailFrom {
    NO_REPLY("no-reply@grinta.com");

    public final String emailFrom;

    EmailFrom(String email){
        this.emailFrom = email;
    }
}
