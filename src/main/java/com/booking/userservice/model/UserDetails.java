package com.booking.userservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDetails {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String emailId;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private UserPlan userPlan;
    private boolean isActive = true;
}
