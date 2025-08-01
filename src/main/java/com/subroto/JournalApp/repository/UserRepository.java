package com.subroto.JournalApp.repository;

import com.subroto.JournalApp.entity.JournalEntity;
import com.subroto.JournalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUsername(String username);
}
