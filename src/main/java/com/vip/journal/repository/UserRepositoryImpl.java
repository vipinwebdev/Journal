package com.vip.journal.repository;

import com.vip.journal.entity.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepositoryImpl {

    @Resource
    private MongoTemplate mongoTemplate;

    public List<User> findAll() {

        Query query = new Query();
        query.addCriteria(Criteria.where("username").is("Raj"));
        return mongoTemplate.find(query, User.class);
    }
}
