package com.raghav.userservice.services;

import com.raghav.userservice.Entity.User;
import com.raghav.userservice.dto.LoginDTO;
import com.raghav.userservice.dto.RegisterDTO;
import com.raghav.userservice.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public User register(RegisterDTO dto){

        User user = new User();
        user.setCreatedAt(LocalDateTime.now());
        user.setName(dto.getName());
        user.setEmailId(dto.getEmailId());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        repo.save(user);
        return user;
    }
    public String verify(LoginDTO dto) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                dto.getEmailId(),
                                dto.getPassword()
                        )
                );
        User user = repo.findByEmailId(dto.getEmailId());

        return jwtService.genrateToken(user.getEmailId(),user.getRole().name());
    }

}
