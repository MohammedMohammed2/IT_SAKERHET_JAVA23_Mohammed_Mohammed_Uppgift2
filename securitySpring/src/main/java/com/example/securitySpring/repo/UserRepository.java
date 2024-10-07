package com.example.securitySpring.repo;

import com.example.securitySpring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

}
