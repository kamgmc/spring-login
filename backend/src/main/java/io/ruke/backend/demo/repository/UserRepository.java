package io.ruke.backend.demo.repository;

import io.ruke.backend.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//  Creates JPA Repository for CRUD operations with entity
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
