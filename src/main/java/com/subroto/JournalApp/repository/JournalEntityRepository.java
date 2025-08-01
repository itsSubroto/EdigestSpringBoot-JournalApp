package com.subroto.JournalApp.repository;

import com.subroto.JournalApp.entity.JournalEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface JournalEntityRepository extends MongoRepository<JournalEntity, ObjectId> {
}
