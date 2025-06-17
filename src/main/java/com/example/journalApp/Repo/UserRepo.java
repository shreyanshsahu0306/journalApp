package com.example.journalApp.Repo;

import com.example.journalApp.Model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<User, ObjectId> {

    User findUserByUserName(String userName);

    void deleteUserByUserName(String userName);
}
