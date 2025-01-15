package com.practice_application.practice_application.entity;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "RETROSPECTIVE_MASTER")
@Getter
@Setter
public class RetrospectiveMaster implements Serializable{

    private static final long serialVersionUID = 1L;

    private String id;

    @Field(name = "RETROSPECTIVE_TITLE")
    private String retrospectiveTitle;

    @Field(name = "DESCRIPTION")
    private String description;

    @Field(name = "CREATED_DATE")
    LocalDateTime createdDate;

    @Field(name = "MODIFIED_DATE")
    LocalDateTime modifiedDate;

    @Field(name = "START_DATE")
    LocalDateTime startDate;

    @Field(name = "END_DATE")
    LocalDateTime endDate;

    @Field(name = "IS_ACTIVE")
    private String isActive = "Y";

    @Field(name = "IS_DELETED")
    private String isDeleted = "N";

    @Field(name = "RETROSPECTIVE_lINK")
    private String retrospectiveLink;

    @Field(name = "INVITED_USERS")
    private List<String> invitedUsers;

    @Field(name = "RETROSPECTIVE_TYPE")
    private String retrospectiveType; // DAILY, WEEKLY, MONTHLY, SPRINT

    @Field(name = "RETROSPECTIVE_COUNT")
    private int retrospectiveCount;

    @Field(name = "RETROSPECTIVE_QUESTIONS")
    private List<String> retrospectiveQuestions;

    @Field(name="LINK")
    private String link;

    @Field(name = "ORG_ID")
    private String orgId;

    @Field(name = "IMAGE_URL")
    private String imageUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Field(name = "END_TIME")
    private LocalTime endTime;
    //private String endTime;

    @Field(name = "IS_REMAINDER_SENT")
    private String isRemainderSent;


}


