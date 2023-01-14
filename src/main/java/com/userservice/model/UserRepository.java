package com.userservice.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDto, Long>{
    UserDto findByUserName(String userName);
    UserDto findByUserEmail(String userEmail);
}
