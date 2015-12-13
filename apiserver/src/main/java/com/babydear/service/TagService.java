package com.babydear.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babydear.controller.UserController;
import com.babydear.model.Baby;
import com.babydear.repository.BabyRepository;

@Service
public class TagService {
	private static final Logger logger = LoggerFactory.getLogger(TagService.class);
	@Autowired BabyRepository babyRepo;

	public List<Baby> processTags(List<Long> bIds) {
		if(bIds == null || bIds.isEmpty()) {
			logger.info("bIds null");
			return null;
		}
		List<Baby> babyList = new ArrayList<Baby>();
		for (Long bId : bIds) {
			babyList.add(babyRepo.findOne(bId));
		}
		return babyList;
	}

}
