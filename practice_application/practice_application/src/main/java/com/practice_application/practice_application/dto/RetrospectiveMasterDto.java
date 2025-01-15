package com.practice_application.practice_application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class RetrospectiveMasterDto {



    private String id;

    private String retrospectiveTitle;

    private String description;

    LocalDateTime createdDate;

    LocalDateTime startDate;

    LocalDateTime endDate;

    private List<String> invitedUsers;

    private String retrospectiveType; // DAILY, WEEKLY, MONTHLY, SPRINT

    private int retrospectiveCount;

    private List<String> retrospectiveQuestions;

    private String link;

    private String orgId;

    private String imageUrl;

    private String retrospectiveUrl;


}
