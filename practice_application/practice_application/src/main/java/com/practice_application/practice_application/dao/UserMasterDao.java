package com.practice_application.practice_application.dao;

import com.practice_application.practice_application.entity.UserMaster;

public interface UserMasterDao {


    UserMaster getUserByMobile(String mobileNumber);


    UserMaster getUserByMobileAndOtp(String mobileNumber, String otp);
}
