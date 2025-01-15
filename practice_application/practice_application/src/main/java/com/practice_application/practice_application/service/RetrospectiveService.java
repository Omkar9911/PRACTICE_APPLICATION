package com.practice_application.practice_application.service;

import com.practice_application.practice_application.dto.RetrospectiveMasterDto;
import com.practice_application.practice_application.entity.RetrospectiveMaster;

import java.time.LocalDateTime;
import java.util.List;



public interface RetrospectiveService {



    RetrospectiveMasterDto createRetrospectiveV1(String retrospectiveTitle, String description,
                                                 LocalDateTime startDate, String retrospectiveType,
                                                 List<String> questions, List<String> invitedUsers, String orgId, String endTime
    );

}
