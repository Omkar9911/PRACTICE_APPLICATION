package com.practice_application.practice_application.service.serviceImpl;


import com.practice_application.practice_application.dto.RetrospectiveMasterDto;
import com.practice_application.practice_application.entity.RetrospectiveMaster;
import com.practice_application.practice_application.service.RetrospectiveService;
import com.practice_application.practice_application.utility.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@Slf4j
public class RetrospectiveServiceImpl implements RetrospectiveService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    Utility utility;

    @Override
    public RetrospectiveMasterDto createRetrospectiveV1(String retrospectiveTitle, String description, LocalDateTime startDate, String retrospectiveType, List<String> questions, List<String> invitedUsers, String orgId, String endTime) {
        try {
            RetrospectiveMaster retrospective = new RetrospectiveMaster();
            retrospective.setCreatedDate(LocalDateTime.now());
            retrospective.setModifiedDate(LocalDateTime.now());

            // Validation for required fields
            utility.validateField(retrospective::setRetrospectiveTitle, retrospectiveTitle, "RETROSPECTIVE_TITLE");
            utility.validateField(retrospective::setDescription, description, "DESCRIPTION");
            utility.validateField(retrospective::setRetrospectiveType, retrospectiveType, "RETROSPECTIVE_TYPE");


            // Setting end date based on start date and retrospective type
            LocalDateTime endDate = calculateEndDate(startDate, retrospectiveType);
            retrospective.setEndDate(endDate);


            if (endTime != null) {
                // Parse endTime string to LocalTime
                LocalTime parsedEndTime = LocalTime.parse(endTime); // Assuming endTime is in "HH:mm:ss" format

                // Get the date part from endDate
                LocalDate endDateLocalDate = retrospective.getEndDate().toLocalDate();

                // Combine date and time to create endDate
                LocalDateTime endDateTime = LocalDateTime.of(endDateLocalDate, parsedEndTime);
                retrospective.setEndDate(endDateTime); // Set the combined endDate
            }



            // Set additional optional fields
            if (startDate != null) {
                retrospective.setStartDate(startDate);
            }

            if(questions != null) {
                retrospective.setRetrospectiveQuestions(questions);
            }

            if(invitedUsers != null) {
                retrospective.setInvitedUsers(invitedUsers);
            }

           /* UnsplashService unsplashService = new UnsplashService();
            String imageUrl = unsplashService.getRandomImageUrl();

            if (imageUrl != null) {
                retrospective.setImageUrl(imageUrl);
            }*/

            if (orgId != null) {
                retrospective.setOrgId(orgId);
            }

            retrospective.setIsActive("Y");
            retrospective.setIsDeleted("N");
            retrospective.setIsRemainderSent("N");
            retrospective.setRetrospectiveCount(0);


            mongoTemplate.save(retrospective);


            // Create and populate RetrospectiveMasterDto
            RetrospectiveMasterDto dto = new RetrospectiveMasterDto();
            dto.setId(retrospective.getId()); // Assuming RetrospectiveMaster has a generated ID
            dto.setRetrospectiveTitle(retrospective.getRetrospectiveTitle());
            dto.setDescription(retrospective.getDescription());
            dto.setStartDate(retrospective.getStartDate());
            dto.setEndDate(retrospective.getEndDate());
            dto.setRetrospectiveType(retrospective.getRetrospectiveType());
            dto.setRetrospectiveQuestions(retrospective.getRetrospectiveQuestions());
            dto.setInvitedUsers(retrospective.getInvitedUsers());
            dto.setOrgId(retrospective.getOrgId());

            return dto;




        } catch (Exception e) {
            log.error("Error creating Retrospective", e);
            throw new RuntimeException("An unexpected error occurred while creating the Retrospective.", e);
        }
    }

    private LocalDateTime calculateEndDate(LocalDateTime startDate, String retrospectiveType) {
        switch (retrospectiveType.toLowerCase()) {
            case "daily":
                return startDate;  // Same day
            case "weekly":
                // Get the date of the upcoming Friday of the same week
                return startDate.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
            case "monthly":
                // Get the last day of the month
                return startDate.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);
            case "sprint":
                // Get the upcoming Friday
                return startDate.plusWeeks(1).with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
            default:
                throw new IllegalArgumentException("Invalid retrospective type");
        }
    }

}
