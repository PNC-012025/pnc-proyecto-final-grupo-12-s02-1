package org.carshare.carsharesv_webservice.repository;

import org.carshare.carsharesv_webservice.domain.entity.Role;
import org.carshare.carsharesv_webservice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface iUserRepository extends JpaRepository<User, UUID> {
    User findOneByUserId(UUID userId);
    Optional<User> findByUsernameOrEmail(String username, String email);
    List<User> findByActive(boolean active);
}
