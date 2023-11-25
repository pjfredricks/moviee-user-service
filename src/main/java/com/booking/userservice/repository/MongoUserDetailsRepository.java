package com.booking.userservice.repository;

import com.booking.userservice.entity.MongoUserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoUserDetailsRepository extends MongoRepository<MongoUserDetails, String> {
    Optional<MongoUserDetails> findByUserName(String userName);
}
