package com.babydear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.babydear.model.Card;

@Repository
public interface BabyRepository extends JpaRepository<Card, Long> {
}
