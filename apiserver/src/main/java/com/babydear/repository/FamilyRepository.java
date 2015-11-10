package com.babydear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.babydear.model.Family;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {
}
