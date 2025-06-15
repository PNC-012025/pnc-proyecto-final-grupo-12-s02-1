package org.carshare.carsharesv_webservice.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.carshare.carsharesv_webservice.domain.entity.User;
import org.carshare.carsharesv_webservice.repository.iUserRepository;
import org.carshare.carsharesv_webservice.security.JwtProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class UsefullMethods {
    private final JwtProvider tokenProvider;
    private final iUserRepository userRepository;
    private final HttpServletRequest request;

    // gets the information of the current logged in user and the requested user in params
    public CurrentUserInfo getUserInfo(UUID userId) {
        String token = tokenProvider.getUserToken(request);
        UUID idFromToken = tokenProvider.getIdFromToken(token);

        List<String> roles = tokenProvider.getRolesFromToken(token);
        User currentUser = userRepository.findOneByUserId(idFromToken);
        User requestedUser = userRepository.findOneByUserId(userId);

        return new CurrentUserInfo(
                roles,
                currentUser,
                requestedUser
        );
    }
}
