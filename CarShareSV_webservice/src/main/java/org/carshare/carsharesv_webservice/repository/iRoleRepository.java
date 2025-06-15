package org.carshare.carsharesv_webservice.repository;

import jakarta.transaction.Transactional;
import org.carshare.carsharesv_webservice.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface iRoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(String roleName);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO userxrole (user_id, role_id) VALUES (:userId, :roleId)",
        nativeQuery = true)
    void addRoleToUser(@Param("userId") UUID userId, @Param("roleId") Integer roleId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM userxrole WHERE user_id = :userId AND role_id = :roleId",
            nativeQuery = true)
    void revokeRoleFromUser(@Param("userId") UUID userId, @Param("roleId") Integer roleId);
}
