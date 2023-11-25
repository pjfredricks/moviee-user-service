package com.booking.userservice.controller;

import com.booking.userservice.dto.UserDetailsDTO;
import com.booking.userservice.service.UserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/v1/users")
public class UserDetailsController {

    private final UserDetailsService userDetailsService;

    public UserDetailsController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public ResponseEntity<List<UserDetailsDTO>> getAllUsers() {
        return ResponseEntity.ok(userDetailsService.getAllUsers());
    }

    @GetMapping("{userName}")
    public ResponseEntity<UserDetailsDTO> getByUserName(@PathVariable String userName) {
        return ResponseEntity.ok(userDetailsService.getByUserName(userName));
    }

    @PostMapping
    public ResponseEntity<UserDetailsDTO> creatUser(@RequestBody UserDetailsDTO userDetailsDTO) {
        return ResponseEntity.ok(userDetailsService.createUser(userDetailsDTO));
    }

    @PutMapping("{userName}")
    public ResponseEntity<UserDetailsDTO> updateUser(@PathVariable String userName,
                                                     @RequestBody UserDetailsDTO userDetailsDTO) {
        return ResponseEntity.ok(userDetailsService.updateUser(userName, userDetailsDTO));
    }
}
