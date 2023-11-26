package com.booking.userservice.service.impl;

import com.booking.userservice.dto.UserDetailsDTO;
import com.booking.userservice.entity.MongoUserDetails;
import com.booking.userservice.exception.CustomException;
import com.booking.userservice.repository.MongoUserDetailsRepository;
import com.booking.userservice.service.UserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MongoUserDetailsRepository userDetailsRepository;
    private final ModelMapper modelMapper;

    public UserDetailsServiceImpl(MongoUserDetailsRepository userDetailsRepository, ModelMapper modelMapper) {
        this.userDetailsRepository = userDetailsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetailsDTO createUser(UserDetailsDTO userDetailsDTO) {
        MongoUserDetails mongoUserDetails = userDetailsRepository.save(mapToEntity(userDetailsDTO));
        return mapToDTO(mongoUserDetails);
    }

    @Override
    public UserDetailsDTO updateUser(String userName, UserDetailsDTO userDetailsDTO) {
        Optional<MongoUserDetails> mongoUserDetailsOptional = userDetailsRepository.findByUserName(userName);
        if (mongoUserDetailsOptional.isEmpty()) {
            throw new CustomException(STR."No user present with given username: \{userName}");
        }

        MongoUserDetails mongoUserDetails = mongoUserDetailsOptional.get();
        modelMapper.map(userDetailsDTO, mongoUserDetails);
        mongoUserDetails.setUserName(userName);

        mongoUserDetails = userDetailsRepository.save(mongoUserDetails);
        return mapToDTO(mongoUserDetails);
    }

    @Override
    public List<UserDetailsDTO> getAllUsers() {
        List<MongoUserDetails> mongoUserDetails = userDetailsRepository.findAll();
        return mongoUserDetails.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetailsDTO getByUserName(String userName) {
        Optional<MongoUserDetails> mongoUserDetails = userDetailsRepository.findByUserName(userName);
        return mongoUserDetails.map(this::mapToDTO)
                .orElse(null);
    }

    @Override
    public String deactivateUser(String userName) {
        Optional<MongoUserDetails> mongoUserDetails = userDetailsRepository.findByUserName(userName);
        if (mongoUserDetails.isEmpty()) {
            return STR."No user found with given name \{userName}";
        } else {
            userDetailsRepository.deactivateUser(userName);
            return STR."User \{userName} deleted successfully";
        }
    }

    private UserDetailsDTO mapToDTO(MongoUserDetails mongoUserDetails) {
        return modelMapper.map(mongoUserDetails, UserDetailsDTO.class);
    }

    private MongoUserDetails mapToEntity(UserDetailsDTO userDetailsDTO) {
        return modelMapper.map(userDetailsDTO, MongoUserDetails.class);
    }
}
