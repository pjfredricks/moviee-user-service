package com.booking.userservice.repository;

import com.booking.userservice.entity.MongoUserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.Optional;

public interface MongoUserDetailsRepository extends MongoRepository<MongoUserDetails, String> {
    Optional<MongoUserDetails> findByUserName(String userName);

    @Query("{'userName' :  ?0 }")
    @Update("{'$set':  {'isActive': false}}")
    Integer deactivateUser(String userName);
}
