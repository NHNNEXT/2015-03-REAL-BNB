package com.babydear.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.babydear.model.Baby;

@Repository
public interface BabyRepository extends JpaRepository<Baby, Long> {
	List<Baby> findByBabyName(String string);
	List<Baby> findByFId(Long fId);
}
