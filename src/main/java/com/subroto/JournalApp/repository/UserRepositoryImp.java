package com.subroto.JournalApp.repository;

import com.subroto.JournalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImp {

    @Autowired
    private MongoTemplate mongoTemplate ;

    public List<User> getUserForSA(){
        Criteria criteria=new Criteria() ;
        Query query=new Query();

        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        query.addCriteria(Criteria.where("roles").in("USER","ADMIN"));

//        using or queries
        query.addCriteria(criteria.orOperator(
                Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"),
                Criteria.where("sentimentAnalysis").is(true)));


        List<User> users = mongoTemplate.find(query, User.class);
        return users;

    }
 }
