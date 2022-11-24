package com.example.giay_story.service.user;

import com.example.giay_story.model.User;
import com.example.giay_story.model.dto.UserDTO;
import com.example.giay_story.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends IGeneralService<User>, UserDetailsService {
    User getByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<UserDTO> findUserDTOByEmail(String email);

    Optional<UserDTO> findUserDTOByEmailPassword(String email);

    Optional<UserDTO> findUserDTOByPhone(String phone);

    Optional<UserDTO> findUserDTOById(long id);
}
