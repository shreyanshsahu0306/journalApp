package com.example.journalApp.Repo;

import com.example.journalApp.Model.ConfigJournalAppEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepo extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
