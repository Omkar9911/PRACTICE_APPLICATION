package com.practice_application.practice_application.service.serviceImpl;


import com.practice_application.practice_application.dao.UserMasterDao;
import com.practice_application.practice_application.entity.UserMaster;
import com.practice_application.practice_application.service.UserService;
import com.practice_application.practice_application.utility.UserUtility;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class UserServiceImpl implements UserService {


    @Autowired
    UserMasterDao userMasterDao;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserUtility userUtility;

    @Override
    public String signup(String userName, String email, String mobileNumber) {

        UserMaster userMaster = userMasterDao.getUserByMobile(mobileNumber);

        if (userMaster!= null) {
            return "Mobile Number is already exists! You can login.";
        }

        userMaster = new UserMaster();
        userMaster.setUserName(userName);
        userMaster.setEmail(email);
        userMaster.setMobileNumber(mobileNumber);

        mongoTemplate.save(userMaster);

        return "Congratulations! You have Successfully Signup! Now you can login";
    }


    @Override
    public JSONObject sendOtp(String mobileNumber) {

        JSONObject response = new JSONObject();
        UserMaster entity = userMasterDao.getUserByMobile(mobileNumber);
        if (entity == null) {
            response.put("success", false);
            response.put("message", "User not found with given mobile number");
            return response;
        }

      /*  if (entity.getOtpValidityDate() != null && entity.getOtpValidityDate().isAfter(LocalDateTime.now())) {


        }*/

        if (entity.getOtpValidityDate() == null || entity.getOtpValidityDate().isBefore(LocalDateTime.now())) {

            String otp = userUtility.generateOTP();
            entity.setOtp(otp);
            entity.setOtpValidityDate(LocalDateTime.now().plusMinutes(5));
            entity.setCreatedDate(LocalDateTime.now());
            mongoTemplate.save(entity);

        }

        // SMS message send to number

        response.put("success", true);
        response.put("message", "OTP is " + entity.getOtp());
        return response;

    }


    @Override
    public JSONObject loginResponse(String mobileNumber, String otp) {

        JSONObject response = new JSONObject();
        UserMaster entity = userMasterDao.getUserByMobileAndOtp(mobileNumber, otp);

        if(entity == null) {
            response.put("success" ,false);
            response.put("message", "Invalid OTP or User not found");
        } else {
            if (entity.getOtpValidityDate() == null || entity.getOtpValidityDate().isBefore(LocalDateTime.now())){
                response.put("success", false);
                response.put("message", "OTP expired. Please generate new OTP");
                return response;
            }
            response.put("success", true);
            response.put("message", "Login Successful");
            response.put("data", entity);
        }

        return response;
    }
}
