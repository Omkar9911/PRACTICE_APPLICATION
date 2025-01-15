package com.practice_application.practice_application.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RetrospectiveMasterRequest {


    private String retrospectiveTitle;

    private String description;

    LocalDateTime startDate;

    private String retrospectiveType; // DAILY, WEEKLY, MONTHLY, SPRINT


    private List<String> retrospectiveQuestions;

    private List<String> invitedUsers;

    private String orgId;
//
//    private String imageUrl;

    // private LocalTime endTime;

    private String endTime;

}

