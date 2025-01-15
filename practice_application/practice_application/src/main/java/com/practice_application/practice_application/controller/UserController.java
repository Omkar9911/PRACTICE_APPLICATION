package com.practice_application.practice_application.controller;


import com.practice_application.practice_application.auth.JwtTokenUtil;
import com.practice_application.practice_application.entity.UserMaster;
import com.practice_application.practice_application.response.JWTResponse;
import com.practice_application.practice_application.service.UserService;
import com.practice_application.practice_application.utility.BaseResponse;
import com.practice_application.practice_application.utility.BaseResponseBuilder;
import com.practice_application.practice_application.utility.StatusCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService usrService;

    @Autowired
    JwtTokenUtil tokenUtil;


    @PostMapping("/signUp")
    public ResponseEntity<BaseResponse> signUp( HttpServletRequest request,
                                                @RequestParam String userName,
                                                @RequestParam String email,
                                                @RequestParam String mobileNumber

                                                ) {
        // Implement sign up logic here

        log.info("Getting signUp");
        log.info("SignUp request received for mobileNumber: {}", mobileNumber);

        return new ResponseEntity<>(new BaseResponseBuilder().setBaseResponseWithStatusAndCodeAndData(
                HttpStatus.OK.name(), StatusCodeEnum.SUCCESS_CODE.getCode(),
                usrService.signup(userName,email,mobileNumber)), HttpStatus.OK);


    }


    @PostMapping("/sendOtp")
    public ResponseEntity<BaseResponse> sendOtp(HttpServletRequest request,
                                             @RequestParam String mobileNumber) {

        log.info("Sending OTP to : {} ", mobileNumber);

        return new ResponseEntity<>(new BaseResponseBuilder().setBaseResponseWithStatusAndCodeAndData(
                HttpStatus.OK.name(), StatusCodeEnum.SUCCESS_CODE.getCode(),
                usrService.sendOtp(mobileNumber).toMap()), HttpStatus.OK);


    }

    @PostMapping("login")
    public ResponseEntity<BaseResponse> login(HttpServletRequest request,
                                             @RequestParam String mobileNumber,
                                             @RequestParam String otp) {
        JSONObject loginResponse = usrService.loginResponse(mobileNumber, otp);

        if (loginResponse.getBoolean("success")) {

            UserMaster user = (UserMaster) loginResponse.get("data");
            JWTResponse response = tokenUtil.generateToken(user);

            return new ResponseEntity<>(new BaseResponseBuilder().setBaseResponseWithStatusAndCodeAndData(
                    HttpStatus.OK.name(), StatusCodeEnum.SUCCESS_CODE.getCode(),
                    response), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(new BaseResponseBuilder().setBaseResponseWithStatusAndCodeAndData(
                    HttpStatus.INTERNAL_SERVER_ERROR.name(), StatusCodeEnum.BAD_REQUEST.getCode(),
                    loginResponse.getString("message")), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
