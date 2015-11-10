package com.babydear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.babydear.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
