package com.booking.userservice.entity;

import com.booking.userservice.model.UserDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "user-details")
public class MongoUserDetails extends UserDetails {

    @Id
    @JsonProperty("_id")
    private String id;

    @Version
    private Long version;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updateAt;
}
