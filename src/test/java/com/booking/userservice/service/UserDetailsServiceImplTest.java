package com.booking.userservice.service;

import com.booking.userservice.config.SpringTestConfig;
import com.booking.userservice.dto.UserDetailsDTO;
import com.booking.userservice.entity.MongoUserDetails;
import com.booking.userservice.exception.CustomException;
import com.booking.userservice.model.UserPlan;
import com.booking.userservice.repository.MongoUserDetailsRepository;
import com.booking.userservice.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringTestConfig.class})
class UserDetailsServiceImplTest {

    private UserDetailsServiceImpl userDetailsService;
    private MongoUserDetailsRepository userDetailsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        userDetailsRepository = mock(MongoUserDetailsRepository.class);
        userDetailsService = new UserDetailsServiceImpl(userDetailsRepository, modelMapper);
    }

    @Test
    void createUser_success() {
        MongoUserDetails mongoUserDetails = createMongoUserDetails();
        when(userDetailsRepository.save(any(MongoUserDetails.class))).thenReturn(mongoUserDetails);

        UserDetailsDTO userDetailsDTO = userDetailsService.createUser(createUserDetails());
        assertNotNull(userDetailsDTO);
        assertEquals("TestUser", userDetailsDTO.getUserName());
        assertEquals("test@testemail.com", userDetailsDTO.getEmailId());
    }

    @Test
    void updateUser_success() {
        String userName = "TestUser";
        MongoUserDetails mongoUserDetails = createMongoUserDetails();
        when(userDetailsRepository.findByUserName(anyString())).thenReturn(Optional.of(mongoUserDetails));
        when(userDetailsRepository.save(any(MongoUserDetails.class))).thenReturn(mongoUserDetails);

        UserDetailsDTO userDetailsDTO = userDetailsService.updateUser(userName, createUserDetails());
        assertNotNull(userDetailsDTO);
        assertEquals("TestUser", userDetailsDTO.getUserName());
        assertEquals("test@testemail.com", userDetailsDTO.getEmailId());
    }

    @Test
    void updateUser_noUserFound() {
        String userName = "TestUser";
        when(userDetailsRepository.findByUserName(anyString())).thenReturn(Optional.empty());

        assertThrows(CustomException.class,
                () -> userDetailsService.updateUser(userName, createUserDetails()),
                STR."No user present with given username: \{userName}");
    }

    @Test
    void getAllUsers() {
        when(userDetailsRepository.findAll()).thenReturn(List.of(createMongoUserDetails()));

        List<UserDetailsDTO> userDetails = userDetailsService.getAllUsers();
        assertNotNull(userDetails);
        assertEquals(1, userDetails.size());
        assertEquals("TestUser", userDetails.getFirst().getUserName());
    }

    @Test
    void getByUserName_Success() {
        when(userDetailsRepository.findByUserName(anyString())).thenReturn(Optional.of(createMongoUserDetails()));

        UserDetailsDTO userDetailsDTO = userDetailsService.getByUserName("TestUser");
        assertNotNull(userDetailsDTO);
        assertEquals("TestUser", userDetailsDTO.getUserName());
        assertEquals("test@testemail.com", userDetailsDTO.getEmailId());
    }

    @Test
    void deactivateUser_success() {
        String userName = "TestUser";
        when(userDetailsRepository.findByUserName(anyString())).thenReturn(Optional.of(createMongoUserDetails()));
        String response = userDetailsService.deactivateUser(userName);

        assertEquals(STR."User \{userName} deleted successfully", response);
        verify(userDetailsRepository, times(1)).findByUserName(userName);
        verify(userDetailsRepository, times(1)).deactivateUser(userName);
    }

    @Test
    void deactivateUser_noUserFound() {
        String userName = "TestUser";
        when(userDetailsRepository.findByUserName(anyString())).thenReturn(Optional.empty());
        String response = userDetailsService.deactivateUser(userName);

        assertEquals(STR."No user found with given name \{userName}", response);
        verify(userDetailsRepository, times(1)).findByUserName(userName);
        verify(userDetailsRepository, times(0)).deactivateUser(userName);
    }

    private MongoUserDetails createMongoUserDetails() {
        MongoUserDetails mongoUserDetails = new MongoUserDetails();
        mongoUserDetails.setId("100101");
        mongoUserDetails.setUserName("TestUser");
        mongoUserDetails.setFirstName("Test");
        mongoUserDetails.setLastName("User");
        mongoUserDetails.setPassword("Test@123");
        mongoUserDetails.setEmailId("test@testemail.com");
        mongoUserDetails.setPhoneNumber("9876543210");
        mongoUserDetails.setDateOfBirth(LocalDate.of(2000, 1, 1));
        mongoUserDetails.setUserPlan(UserPlan.FREE);
        mongoUserDetails.setCreatedAt(LocalDateTime.now());
        mongoUserDetails.setUpdateAt(LocalDateTime.now());
        mongoUserDetails.setVersion(1L);

        return mongoUserDetails;
    }

    private UserDetailsDTO createUserDetails() {
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setUserName("TestUser");
        userDetailsDTO.setFirstName("Test");
        userDetailsDTO.setLastName("User");
        userDetailsDTO.setPassword("Test@123");
        userDetailsDTO.setEmailId("test@testemail.com");
        userDetailsDTO.setPhoneNumber("9876543210");
        userDetailsDTO.setDateOfBirth(LocalDate.of(2000, 1, 1));
        userDetailsDTO.setUserPlan(UserPlan.FREE.name());

        return userDetailsDTO;
    }
}
