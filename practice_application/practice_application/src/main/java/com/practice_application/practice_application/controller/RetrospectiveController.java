package com.practice_application.practice_application.controller;


import com.practice_application.practice_application.dto.RetrospectiveMasterDto;
import com.practice_application.practice_application.entity.RetrospectiveMaster;
import com.practice_application.practice_application.request.RetrospectiveMasterRequest;
import com.practice_application.practice_application.service.RetrospectiveService;
import com.practice_application.practice_application.utility.BaseResponse;
import com.practice_application.practice_application.utility.BaseResponseBuilder;
import com.practice_application.practice_application.utility.StatusCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/retrospective")

public class RetrospectiveController {

    @Autowired
    private RetrospectiveService retrospectiveService;


    @PostMapping("/createRetrospectiveV1")
    public ResponseEntity<BaseResponse> createRetrospectiveV1(
            HttpServletRequest request,
            @RequestParam String retrospectiveTitle,
            @RequestParam String description,
            @RequestParam LocalDateTime startDate,
            @RequestParam String retrospectiveType,
            @RequestParam List<String> retrospectiveQuestions,
            @RequestParam List<String> invitedUsers,
            @RequestParam String orgId,
            @RequestParam String endTime) {

    /* JSONObject authResponse = utility.authenticationV1(request.getHeader("Authorization"));
    if (!authResponse.optBoolean("success", false)) {
        return new ResponseEntity<>(new BaseResponseBuilder().setBaseResponseWithStatusAndCodeAndCustomMessage(
                HttpStatus.UNAUTHORIZED.name(), StatusCodeEnum.UNAUTHORIZED_CODE.getCode(), authResponse.optString("message", "Unauthorized access")), HttpStatus.UNAUTHORIZED);
    } */

        // log.info("Getting /createRetrospectiveV1");

        RetrospectiveMasterDto retrospective = retrospectiveService.createRetrospectiveV1(
                retrospectiveTitle,
                description,
                startDate,
                retrospectiveType,
                retrospectiveQuestions,
                invitedUsers,
                orgId,
                endTime
        );

        return new ResponseEntity<>(new BaseResponseBuilder().setBaseResponseWithStatusAndCodeAndData(
                HttpStatus.OK.name(), StatusCodeEnum.SUCCESS_CODE.getCode(), retrospective), HttpStatus.OK);
    }
}

