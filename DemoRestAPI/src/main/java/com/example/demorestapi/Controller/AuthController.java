package com.example.demorestapi.Controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.example.demorestapi.Model.Role;
import com.example.demorestapi.Model.User;
import com.example.demorestapi.Repository.RoleRepository;
import com.example.demorestapi.Repository.UserRepository;
import com.example.demorestapi.Security.JWT.JwtUtils;
import com.example.demorestapi.Service.UserDetailsImpl;
import com.example.demorestapi.dto.LoginRequest;
import com.example.demorestapi.dto.LoginResponse;
import com.example.demorestapi.dto.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        //được tạo ra đại diện cho người dùng đã xác thực.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateJwtToken(authentication);
        // lấy danh sách role của user đã được xác thực
        List<String> roles = userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());
        String userRole = roles.get(0);

        return ResponseEntity.ok()
                .body(new LoginResponse(
                        jwtToken,
                        userDetails.getUsername(),
                        userRole));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Tên người dùng đã được sử dụng!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email đã được sử dụng!");
        }

        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

        String strRoles = signUpRequest.getRole() != null && signUpRequest.getRole().equals("ROLE_ADMIN") ? "ROLE_ADMIN" : "ROLE_USER";

        Role userRole = roleRepository.findByName(strRoles);

        user.setRole(userRole);
        userRepository.save(user);

        return ResponseEntity.ok("Đăng ký người dùng thành công!");
    }
}