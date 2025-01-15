package com.practice_application.practice_application.service;

import com.practice_application.practice_application.entity.UserMaster;
import org.json.JSONObject;

public interface UserService  {
    public String signup(String userName, String email, String mobileNumber);

    public JSONObject sendOtp(String mobileNumber);

    public   JSONObject loginResponse(String mobileNumber, String otp);
}
