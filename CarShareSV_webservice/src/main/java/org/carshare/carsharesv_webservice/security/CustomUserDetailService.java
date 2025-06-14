package org.carshare.carsharesv_webservice.security;

import lombok.AllArgsConstructor;
import org.carshare.carsharesv_webservice.domain.entity.User;
import org.carshare.carsharesv_webservice.exception.ResourceNotFoundException;
import org.carshare.carsharesv_webservice.repository.iUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private iUserRepository userRepository; // Repository to access user data

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) {
        // Fetches an employee by username or email, throws an exception if not found
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username or email: " + usernameOrEmail));

        // Maps the employee's roles to granted authorities
        Set<GrantedAuthority> grantedAuthorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName())) // Converts each role to a granted authority
                .collect(Collectors.toSet());

        // Returns a Spring Security User object with the employee's username, password, and authorities
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
