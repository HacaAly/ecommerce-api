package com.hikadobushido.ecommerce_java.repository;

import com.hikadobushido.ecommerce_java.entity.UserRole;
import com.hikadobushido.ecommerce_java.entity.UserRole.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

    void deleteByUserId(Long userId);

}
