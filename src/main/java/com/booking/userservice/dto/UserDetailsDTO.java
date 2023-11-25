package com.booking.userservice.dto;

import com.booking.userservice.model.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDetailsDTO {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String emailId;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private UserType userType;
}
