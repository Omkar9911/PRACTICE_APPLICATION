package com.practice_application.practice_application.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "USER_MASTER")
public class UserMaster {


    private static final long serialVersionUID = 1L;

    private String id;

    @Field(name = "USERNAME")
    private String userName;

    @Field(name = "EMAIL")
    private String email;

    @Field(name = "MOBILENUMBER")
    private String mobileNumber;

    @Field(name = "IS_ACTIVE")
    private String isActive = "Y";

    @Field(name = "IS_DELETED")
    private String isDeleted = "N";

    @Field(name = "OTP_VALIDITY_DATE")
    private LocalDateTime otpValidityDate;

    @Field(name = "OTP")
    private String otp;


}
