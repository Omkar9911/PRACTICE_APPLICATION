package com.practice_application.practice_application.dao.Impl;


import com.practice_application.practice_application.dao.UserMasterDao;
import com.practice_application.practice_application.entity.UserMaster;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserMasterDaoImpl implements UserMasterDao {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public UserMaster getUserByMobile(String mobileNumber) {

    Query query = new Query();
        query.addCriteria(Criteria.where("mobileNumber").is(mobileNumber));
        Criteria isActiveCriteria = new Criteria().orOperator(
                Criteria.where("isActive").is("Y"),
                Criteria.where("isActive").is(null)
        );

        Criteria isDeletedCriteria = new Criteria().orOperator(
                Criteria.where("isDeleted").ne("Y"),
                Criteria.where("isDeleted").is(null)
        );
        Criteria finalCriteria = new Criteria().andOperator(isActiveCriteria,isDeletedCriteria);
        query.addCriteria(finalCriteria);

        query.with(Sort.by("_id").descending());


        return mongoTemplate.findOne(query, UserMaster.class);
    }

    @Override
    public UserMaster getUserByMobileAndOtp(String mobileNumber, String otp) {

        Query query = new Query();
        query.addCriteria(Criteria.where("mobileNumber").is(mobileNumber));
        query.addCriteria(Criteria.where("otp").is(otp));
        Criteria isActiveCriteria = new Criteria().orOperator(
                Criteria.where("isActive").is("Y"),
                Criteria.where("isActive").is(null)
        );

        Criteria isDeletedCriteria = new Criteria().orOperator(
                Criteria.where("isDeleted").ne("Y"),
                Criteria.where("isDeleted").is(null)
        );
        Criteria finalCriteria = new Criteria().andOperator(isActiveCriteria,isDeletedCriteria);
        query.addCriteria(finalCriteria);

        query.with(Sort.by("_id").descending());


        return mongoTemplate.findOne(query, UserMaster.class);


    }
}
